package com.surveasy.surveasy.my


import android.os.Bundle
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.amplitude.api.Amplitude
import com.surveasy.surveasy.login.CurrentUserViewModel
import com.surveasy.surveasy.my.history.MyViewHistoryActivity
import com.surveasy.surveasy.my.info.InfoData
import com.surveasy.surveasy.my.info.InfoDataViewModel
import com.google.firebase.auth.ktx.auth
import com.surveasy.surveasy.my.info.MyViewInfoActivity
import com.surveasy.surveasy.my.setting.MyViewSettingActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.*
import com.surveasy.surveasy.databinding.FragmentMyviewBinding
import com.surveasy.surveasy.my.notice.*
import com.surveasy.surveasy.my.notice.noticeRoom.NoticeDatabase
import kotlinx.coroutines.*


class MyViewFragment : Fragment() {
    val db = Firebase.firestore
    val infoDataModel by viewModels<InfoDataViewModel>()
    var info = InfoData(null, null, null, null, null, null, null, null)
    var noticeNum_fb = 0
    var noticeNum_room = 0
    val userModel by activityViewModels<CurrentUserViewModel>()
    private val mainDataViewModel by activityViewModels<MainDataViewModel>()
    private lateinit var noticeDB : NoticeDatabase

    private var _binding : FragmentMyviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory : MainViewModelFactory

    // 여기 다시 확인
    override fun onStart() {
        super.onStart()
        val infoIcon = requireView().findViewById<LinearLayout>(R.id.MyView_InfoIcon)
        val noticeDot = requireView().findViewById<ImageView>(R.id.MyView_NoticeIcon_dot)
        val noticeBtn = requireView().findViewById<ImageView>(R.id.MyView_NoticeIcon)
        val userName = requireView().findViewById<TextView>(R.id.MyView_UserName)
        val userRewardFinAmount = requireView().findViewById<TextView>(R.id.MyView_UserRewardFinAmount)
        val userRewardYetAmount = requireView().findViewById<TextView>(R.id.MyView_UserRewardYetAmount)
        val userSurveyCountAmount = requireView().findViewById<TextView>(R.id.MyView_UserSurveyCountAmount)


        mainViewModelFactory = MainViewModelFactory(MainRepository())
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        CoroutineScope(Dispatchers.Main).launch {
            fetchUserData()
            val userData = mainDataViewModel.currentUserModel[0]
            userName.text = userData.name
            val rewardFin = (userData.rewardTotal!!.toInt()) - (userData.rewardCurrent!!.toInt())
            userRewardFinAmount.text = rewardFin.toString()
            userRewardYetAmount.text = userData.rewardCurrent.toString()
            userSurveyCountAmount.text = userData.UserSurveyList!!.size.toString()
            //eng fetch
            info = InfoData(userData.name, userData.birthDate, userData.gender, userData.email,
                userData.phoneNumber, userData.accountType, userData.accountNumber, false)
            infoIcon.setOnClickListener {
                val intent = Intent(context, MyViewInfoActivity::class.java)
                intent.putExtra("info", info)
                startActivity(intent)
            }
        }


        // Initiate Room DB
        noticeDB = Room.databaseBuilder(
            context!!,
            NoticeDatabase::class.java, "NoticeDatabase"
        ).allowMainThreadQueries().build()

        noticeNum_room = noticeDB.noticeDao().getNum()

        CoroutineScope(Dispatchers.Main).launch {
            val notice = CoroutineScope(Dispatchers.IO).async {
                fetchNoticeNum(noticeDot)
            }.await()

            noticeBtn.setOnClickListener {
                val intent = Intent(context, MyViewNoticeListActivity::class.java)
                intent.putExtra("noticeDiff", noticeNum_fb - noticeNum_room)
                intent.putExtra("notice_room", noticeNum_room)
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyviewBinding.inflate(layoutInflater)
        val view = binding.root
        val userModel by activityViewModels<CurrentUserViewModel>()

        binding.MyViewUserName.setOnClickListener{
            noticeDB.noticeDao().deleteAll()
        }


        // Initiate Room DB
        noticeDB = Room.databaseBuilder(
            context!!,
            NoticeDatabase::class.java, "NoticeDatabase"
        ).allowMainThreadQueries().build()

        noticeNum_room = noticeDB.noticeDao().getLastID()

        CoroutineScope(Dispatchers.Main).launch {
            val notice = CoroutineScope(Dispatchers.IO).async {
                fetchNoticeNum(binding.MyViewNoticeIconDot)
            }.await()

            binding.MyViewNoticeIcon.setOnClickListener {
                val intent = Intent(context, MyViewNoticeListActivity::class.java)
                intent.putExtra("noticeDiff", noticeNum_fb - noticeNum_room)
                intent.putExtra("notice_room", noticeNum_room)
                startActivity(intent)
            }
        }

        binding.MyViewHistoryIcon.setOnClickListener {
            val intent = Intent(context, MyViewHistoryActivity::class.java)
            startActivity(intent)
        }

        binding.MyViewSettingIcon.setOnClickListener {
            val intent = Intent(context, MyViewSettingActivity::class.java)
            intent.putExtra("reward_current", userModel.currentUser.rewardCurrent)
            startActivity(intent)

            // [Amplitude] Settings View Showed
            val client = Amplitude.getInstance()
            client.logEvent("Settings View Showed")

        }

        binding.MyViewContactIcon.setOnClickListener {
            val intent = Intent(context, MyViewContactActivity::class.java)
            startActivity(intent)
        }

            return view
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // Fetch info of current User for MyViewInfo
    private fun fetchInfoData() {
        val docRef = db.collection("panelData").document(Firebase.auth.currentUser!!.uid)
        var eng: Boolean? = true

        docRef.collection("FirstSurvey").document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener { document ->
                if (document != null) {

                    eng = document["EngSurvey"] as Boolean?

                    docRef.get().addOnSuccessListener { document ->
                        if (document != null) {
                            val infoData: InfoData = InfoData(
                                document["name"] as String,
                                document["birthDate"] as String,
                                document["gender"] as String,
                                document["email"] as String,
                                document["phoneNumber"] as String,
                                document["accountType"] as String,
                                document["accountNumber"] as String,
                                eng
                            )
                            info = infoData
                        }
                    }

                }
            }
    }

    private suspend fun fetchUserData(){
        CoroutineScope(Dispatchers.Main).async {
            val t = withContext(Dispatchers.IO){
                while(mainDataViewModel.currentUserModel.size==0){}
                1
            }
        }.await()
    }


    // Fetch Notice Num from Firestore
    private fun fetchNoticeNum(dot: ImageView) {
        val docRef = db.collection("lastID").document("lastNoticeID")
        docRef.get().addOnSuccessListener { document ->
            noticeNum_fb = Integer.parseInt(document["lastNoticeID"].toString()) - 1

            if(noticeNum_fb > noticeNum_room) dot.visibility = View.VISIBLE
            else dot.visibility = View.INVISIBLE
        }

    }

    private fun fetchNoticeNum2(dot: ImageView) {
        noticeNum_fb = 0
        val docRef = db.collection("AppNotice")
        docRef.get().addOnSuccessListener { documents ->
            for(document in documents) {
                noticeNum_fb++
            }

            if(noticeNum_fb > noticeNum_room) dot.visibility = View.VISIBLE
            else dot.visibility = View.INVISIBLE
        }

    }


}
