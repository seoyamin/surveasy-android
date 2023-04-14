package com.surveasy.surveasy.my.history

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.surveasy.surveasy.databinding.ActivityMyviewhistoryBinding
import com.surveasy.surveasy.list.FinUserSurveyListViewModel
import com.surveasy.surveasy.list.UserSurveyItem
import com.surveasy.surveasy.list.WaitUserSurveyListViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewHistoryActivity : AppCompatActivity() {
    val db = Firebase.firestore
    val storage = Firebase.storage
    val waitModel by viewModels<WaitUserSurveyListViewModel>()
    val waitList = arrayListOf<UserSurveyItem>()
    val finModel by viewModels<FinUserSurveyListViewModel>()
    val finList = arrayListOf<UserSurveyItem>()

    private lateinit var binding : ActivityMyviewhistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewhistoryBinding.inflate(layoutInflater)


        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarMyViewHistory)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarMyViewHistory.setNavigationOnClickListener {
            onBackPressed()
        }
        fetchFileName()

        binding.MyViewHistoryWait.setOnClickListener{
            binding.MyViewHistoryWaitText.setTextColor(Color.parseColor("#0aab00"))
            binding.MyViewHistoryWaitLine.visibility = View.VISIBLE
            binding.MyViewHistoryFinText.setTextColor(Color.parseColor("#707070"))
            binding.MyViewHistoryFinLine.visibility = View.INVISIBLE

            binding.fragmentContainerView1.visibility = View.VISIBLE
            binding.fragmentContainerView2.visibility = View.INVISIBLE
        }

        binding.MyViewHistoryFin.setOnClickListener{
            binding.MyViewHistoryWaitText.setTextColor(Color.parseColor("#707070"))
            binding.MyViewHistoryWaitLine.visibility = View.INVISIBLE
            binding.MyViewHistoryFinText.setTextColor(Color.parseColor("#0aab00"))
            binding.MyViewHistoryFinLine.visibility = View.VISIBLE

            binding.fragmentContainerView1.visibility = View.INVISIBLE
            binding.fragmentContainerView2.visibility = View.VISIBLE
        }


    }
    private fun fetchFileName(){
        val storageRef = storage.reference.child("historyTest")


    }
    fun finishActivity(){
        finish()
    }




}