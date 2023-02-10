package com.surveasy.surveasy.my.history

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surveasy.surveasy.R
import com.surveasy.surveasy.list.FinUserSurveyListViewModel
import com.surveasy.surveasy.list.UserSurveyItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.databinding.FragmentMyviewhistoryfinBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyViewHistoryFinFragment : Fragment() {


    val db = Firebase.firestore
    private var _binding : FragmentMyviewhistoryfinBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyViewModel : MyHistoryViewModel

    //activity 내에서만 쓰이는 임시 list
    //val finTempList = arrayListOf<UserSurveyItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyviewhistoryfinBinding.inflate(layoutInflater)
        val finModel by activityViewModels<FinUserSurveyListViewModel>()
        val view = binding.root
        var finTotalReward : Int = 0
        var cnt : Int = 5

        historyViewModel = ViewModelProvider(this, MyHistoryViewModelFactory(MyHistoryRepository()))[MyHistoryViewModel::class.java]


        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                while(finModel.finSurvey.size==0){
                }
                finModel.finSurvey.get(0).id
            }.await()

            for(i in finModel.finSurvey){
                finTotalReward+=i.reward!!
            }
            binding.MyViewHistoryFinAmount.text = finTotalReward.toString() + "원"

            if(finModel.finSurvey.size==0){
                binding.historyFinNoneText.visibility = View.VISIBLE
                binding.historyFinNoneText.text = "해당 설문이 없습니다."
            }
            else{
                val adapter = FinSurveyItemsAdapter(changeHistoryList(finModel.finSurvey,cnt))
                binding.historyFinRecyclerContainer.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                binding.historyFinRecyclerContainer.adapter = FinSurveyItemsAdapter(changeHistoryList(finModel.finSurvey,cnt))
            }

        }

        binding.historyFinMoreBtn.setOnClickListener {
            if(cnt>=finModel.finSurvey.size-1){
                Toast.makeText(context,"불러올 수 있는 내역이 없습니다",Toast.LENGTH_SHORT).show()
            }else{
                cnt+=5
                val adapter = FinSurveyItemsAdapter(changeHistoryList(finModel.finSurvey,cnt))
                binding.historyFinRecyclerContainer.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                binding.historyFinRecyclerContainer.adapter = FinSurveyItemsAdapter(changeHistoryList(finModel.finSurvey,cnt))
            }

        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun changeHistoryList(finSurvey : ArrayList<UserSurveyItem>, cnt : Int) : ArrayList<UserSurveyItem>{
        finSurvey.sortWith(compareByDescending<UserSurveyItem> { it.responseDate })
        val defaultList = arrayListOf<UserSurveyItem>()
        var i : Int = 0
        if(finSurvey.size < cnt){
            while(i <=finSurvey.size-1){
                defaultList.add(finSurvey.get(i))
                i++
            }
        }else{
            while(i <cnt){
                defaultList.add(finSurvey.get(i))
                i++
            }
        }
        return defaultList
    }
}