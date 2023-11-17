package com.surveasy.surveasy.list


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.surveasy.surveasy.R
import com.surveasy.surveasy.presentation.login.CurrentUserViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.presentation.MainRepository
import com.surveasy.surveasy.presentation.MainViewModel
import com.surveasy.surveasy.presentation.MainViewModelFactory
import com.surveasy.surveasy.auth.AuthDialogActivity
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SurveyListFragment() : Fragment() {

    val db = Firebase.firestore
    private var listFilter : String? = null
    val surveyList = arrayListOf<SurveyItems>()
    val model by activityViewModels<SurveyInfoViewModel>()
    val userModel by activityViewModels<CurrentUserViewModel>()
    //auth check
    private lateinit var mainViewModel : MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_surveylist,container,false)

        val container : RecyclerView? = view.findViewById(R.id.recyclerContainer)
        //val refreshBtn : ImageButton = view.findViewById(R.id.Surveylist_refresh)
        val filterParticipate : Switch = view.findViewById(R.id.Surveylist_FilterParticipate)
        var showCanParticipateList = arrayListOf<Boolean>()
//        val filterList = listOf("최신순","마감순")
//        val filterAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, filterList)
//        val filterSpinner : Spinner = view.findViewById(R.id.Surveylist_FilterSpinner)
        var n : Int = 0


        // fetch auth info
        mainViewModelFactory = MainViewModelFactory(MainRepository())
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        CoroutineScope(Dispatchers.Main).launch {
            mainViewModel.fetchDidAuth(Firebase.auth.uid.toString())
            mainViewModel.repositories1.observe(viewLifecycleOwner){
                if(!it.didAuth){
                    val intent = Intent(context, AuthDialogActivity::class.java)
                    startActivity(intent)
                }
            }
        }


        while (n < model.surveyInfo.size) {
            showCanParticipateList.add(false)
            n++
        }

//        filterSpinner.adapter = filterAdapter
//        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                listFilter = filterList[position]
//                if(listFilter.equals("최신순")){
//                    if(filterParticipate.isChecked){
//                        val adapter = SurveyItemsAdapter(model.sortSurveyRecent(), changeDoneSurvey(),changeDoneSurvey())
//                        container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                        container?.adapter = SurveyItemsAdapter(model.sortSurveyRecent(),changeDoneSurvey(),changeDoneSurvey())
//                    }else{
//                        val adapter = SurveyItemsAdapter(model.sortSurveyRecent(), changeDoneSurvey(),showCanParticipateList)
//                        container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                        container?.adapter = SurveyItemsAdapter(model.sortSurveyRecent(),changeDoneSurvey(),showCanParticipateList)
//                    }
//
//                }else{
//                    if(filterParticipate.isChecked){
//                        val adapter = SurveyItemsAdapter(model.sortSurvey(), changeDoneSurvey(),changeDoneSurvey())
//                        container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                        container?.adapter = SurveyItemsAdapter(model.sortSurvey(),changeDoneSurvey(),changeDoneSurvey())
//                    }else{
//                        val adapter = SurveyItemsAdapter(model.sortSurvey(), changeDoneSurvey(),showCanParticipateList)
//                        container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                        container?.adapter = SurveyItemsAdapter(model.sortSurvey(),changeDoneSurvey(),showCanParticipateList)
//                    }
//                }
//
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//        }



        CoroutineScope(Dispatchers.Main).launch {
            //val model by activityViewModels<SurveyInfoViewModel>()
            getSurveyList(model)

            val adapter = SurveyItemsAdapter(model.sortSurveyRecent(), changeDoneSurvey(),showCanParticipateList)
            container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            container?.adapter = SurveyItemsAdapter(model.sortSurveyRecent(),changeDoneSurvey(),showCanParticipateList)
        }
        //Toast.makeText(context,"Loading",Toast.LENGTH_LONG).show()

        container?.setItemViewCacheSize(20)

        filterParticipate.setOnCheckedChangeListener{ button, ischecked ->
            if(ischecked){
                val adapter = SurveyItemsAdapter(model.sortSurveyRecent(), changeDoneSurvey(),changeDoneSurvey())
                container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                container?.adapter = SurveyItemsAdapter(model.sortSurveyRecent(),changeDoneSurvey(),changeDoneSurvey())

            }else{
                val adapter = SurveyItemsAdapter(model.sortSurveyRecent(), changeDoneSurvey(),showCanParticipateList)
                container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                container?.adapter = SurveyItemsAdapter(model.sortSurveyRecent(),changeDoneSurvey(),showCanParticipateList)

            }
        }

//        refreshBtn.setOnClickListener{
//
//
//            (activity as MainActivity).clickList()
//            Log.d(TAG,"%%%%refresh")
//
//            }






        return view
    }

    private suspend fun getSurveyList(listModel : SurveyInfoViewModel){
        withContext(Dispatchers.IO){
            while (listModel.surveyInfo.size == 0) {
                //Log.d(TAG, "########loading")
            }
        }
    }


    //참여 완료한 survey 를 list 안에서 색 변경
    private fun changeDoneSurvey() : ArrayList<Boolean> {


        val doneSurvey = userModel.currentUser.UserSurveyList
        var boolList = ArrayList<Boolean>(model.surveyInfo.size)
        var num: Int = 0



        //survey list item 크기와 같은 boolean type list 만들기. 모두 false 로
        while (num < model.surveyInfo.size) {
            boolList.add(false)
            num++
        }

        var index: Int = -1

        // userSurveyList 와 겹치는 요소가 있으면 boolean 배열의 해당 인덱스 값을 true로 바꿈
        if (doneSurvey?.size != 0) {
            if (doneSurvey != null) {
                for (done in doneSurvey) {
                    index = -1
                    for (survey in model.surveyInfo) {
                        index++
                        val dueDate = survey.dueDate + " " + survey.dueTimeTime + ":00"
                        val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        val date = sf.parse(dueDate)
                        val now = Calendar.getInstance()
                        val calDate = (date.time - now.time.time) / (60 * 60 * 1000)

                        if (calDate < 0) {
                            boolList[index] = true
                        }

                        if (survey.idChecked.equals(done.idChecked)) {
                            boolList[index] = true
                        } else if (survey.progress >= 3) {
                            boolList[index] = true
                        }
                    }
                }
            }
        }else{
            index = -1
            for(survey in model.surveyInfo){
                index++
                boolList[index] = survey.progress>=3
            }
        }
        return boolList


    }







// firebase에서 가져와서 비교 (속도 문제)
//    private fun changeFbDone(surveyList : ArrayList<SurveyItems>) : ArrayList<Boolean>{
//        val userModel by activityViewModels<CurrentUserViewModel>()
//
//        val doneSurvey = userModel.currentUser.UserSurveyList
//        var boolList = ArrayList<Boolean>(surveyList.size)
//        var num: Int = 0
//
//        //survey list item 크기와 같은 boolean type list 만들기
//        while (num < surveyList.size) {
//            boolList.add(false)
//            num++
//        }
//
//        var index: Int = -1
//        // usersurveylist 와 겹치는 요소가 있으면 boolean 배열의 해당 인덱스 값을 true로 바꿈
//        if (doneSurvey != null) {
//            for (done in doneSurvey) {
//                index = -1
//                for (survey in surveyList) {
//                    index++
//                    if (survey.id == done.id) {
//                        boolList[index] = true
//                    }
//                }
//            }
//        }
//
//        return boolList
//    }


}