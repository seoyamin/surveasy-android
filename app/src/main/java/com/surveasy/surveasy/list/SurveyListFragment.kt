package com.surveasy.surveasy.list


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surveasy.surveasy.login.CurrentUserViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.*
import com.surveasy.surveasy.databinding.FragmentSurveylistBinding
import com.surveasy.surveasy.model.SurveyModel
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
    private val mainDataViewModel by activityViewModels<MainDataViewModel>()


    private var _binding : FragmentSurveylistBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory : MainViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurveylistBinding.inflate(layoutInflater)
        val view = binding.root

        var showCanParticipateList = arrayListOf<Boolean>()
        val filterList = listOf("최신순","마감순")
        val filterAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, filterList)
        var n : Int = 0

        while (n < model.surveyInfo.size) {
            showCanParticipateList.add(false)
            n++
        }

//        binding.SurveylistFilterSpinner.adapter = filterAdapter
//        binding.SurveylistFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                listFilter = filterList[position]
//                if(listFilter.equals("최신순")){
//                    if(binding.SurveylistFilterParticipate.isChecked){
//                        val adapter = SurveyItemsAdapter(model.sortSurveyRecent(), changeDoneSurvey(),changeDoneSurvey())
//                        binding.recyclerContainer.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                        binding.recyclerContainer.adapter = SurveyItemsAdapter(model.sortSurveyRecent(),changeDoneSurvey(),changeDoneSurvey())
//                    }else{
//                        val adapter = SurveyItemsAdapter(model.sortSurveyRecent(), changeDoneSurvey(),showCanParticipateList)
//                        binding.recyclerContainer.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                        binding.recyclerContainer.adapter = SurveyItemsAdapter(model.sortSurveyRecent(),changeDoneSurvey(),showCanParticipateList)
//                    }
//
//                }else{
//                    if(binding.SurveylistFilterParticipate.isChecked){
//                        val adapter = SurveyItemsAdapter(model.sortSurvey(), changeDoneSurvey(),changeDoneSurvey())
//                        binding.recyclerContainer.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                        binding.recyclerContainer.adapter = SurveyItemsAdapter(model.sortSurvey(),changeDoneSurvey(),changeDoneSurvey())
//                    }else{
//                        val adapter = SurveyItemsAdapter(model.sortSurvey(), changeDoneSurvey(),showCanParticipateList)
//                        binding.recyclerContainer.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                        binding.recyclerContainer.adapter = SurveyItemsAdapter(model.sortSurvey(),changeDoneSurvey(),showCanParticipateList)
//                    }
//                }
//
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//        }

        mainViewModelFactory = MainViewModelFactory(MainRepository())
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        //이부분 수정
        CoroutineScope(Dispatchers.Main).launch {
            mainViewModel.fetchSurvey(20, "여")
            mainViewModel.repositories6.observe(viewLifecycleOwner){
                Log.d(TAG, "onCreate: 000000${it.get(0)}")
                val adapter = SurveyItemsAdapter(sortSurveyRecent(it), changeDoneSurvey(it),showCanParticipateList)
                binding.recyclerContainer.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                binding.recyclerContainer.adapter = SurveyItemsAdapter(sortSurveyRecent(it),changeDoneSurvey(it),showCanParticipateList)

            }

        }
        //Toast.makeText(context,"Loading",Toast.LENGTH_LONG).show()

        binding.recyclerContainer.setItemViewCacheSize(20)

//        binding.SurveylistFilterParticipate.setOnCheckedChangeListener{ button, ischecked ->
//            if(ischecked){
//                if(listFilter.equals("최신순")){
//                    val adapter = SurveyItemsAdapter(model.sortSurveyRecent(), changeDoneSurvey(),changeDoneSurvey())
//                    binding.recyclerContainer.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                    binding.recyclerContainer.adapter = SurveyItemsAdapter(model.sortSurveyRecent(),changeDoneSurvey(),changeDoneSurvey())
//                }
//                else{
//                    val adapter = SurveyItemsAdapter(model.sortSurvey(), changeDoneSurvey(),changeDoneSurvey())
//                    binding.recyclerContainer.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                    binding.recyclerContainer.adapter = SurveyItemsAdapter(model.sortSurvey(),changeDoneSurvey(),changeDoneSurvey())
//                }
//
//            }else{
//                if(listFilter.equals("최신순")){
//                    val adapter = SurveyItemsAdapter(model.sortSurveyRecent(), changeDoneSurvey(),showCanParticipateList)
//                    binding.recyclerContainer.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                    binding.recyclerContainer.adapter = SurveyItemsAdapter(model.sortSurveyRecent(),changeDoneSurvey(),showCanParticipateList)
//                }else{
//                    val adapter = SurveyItemsAdapter(model.sortSurvey(), changeDoneSurvey(),showCanParticipateList)
//                    binding.recyclerContainer.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                    binding.recyclerContainer.adapter = SurveyItemsAdapter(model.sortSurvey(),changeDoneSurvey(),showCanParticipateList)
//                }
//
//            }
//        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //최신 등록순 정렬
    fun sortSurveyRecent(surveyList : kotlin.collections.ArrayList<SurveyModel>) : ArrayList<SurveyModel>{
        val recentList = kotlin.collections.ArrayList<SurveyModel>()
        surveyList.sortWith(compareByDescending<SurveyModel> { it.uploadDate })
        recentList.addAll(surveyList)

        return recentList
    }

    private suspend fun getSurveyList(listModel : SurveyInfoViewModel){
        withContext(Dispatchers.IO){
            while (listModel.surveyInfo.size == 0) {
                //Log.d(TAG, "########loading")
            }
        }
    }


    //참여 완료한 survey 를 list 안에서 색 변경
    private fun changeDoneSurvey(surveyList : kotlin.collections.ArrayList<SurveyModel>) : ArrayList<Boolean> {


        val doneSurvey = mainDataViewModel.currentUserModel[0].UserSurveyList
        var boolList = ArrayList<Boolean>(surveyList.size)
        var num: Int = 0



        //survey list item 크기와 같은 boolean type list 만들기. 모두 false 로
        while (num < surveyList.size) {
            boolList.add(false)
            num++
        }

        var index: Int = -1

        // userSurveyList 와 겹치는 요소가 있으면 boolean 배열의 해당 인덱스 값을 true로 바꿈
        if (doneSurvey?.size != 0) {
            if (doneSurvey != null) {
                for (done in doneSurvey) {
                    index = -1
                    for (survey in surveyList) {
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
            for(survey in surveyList){
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