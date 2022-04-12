package com.test.surveasy.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.surveasy.databinding.ActivitySurveylistdetailresponseBinding
import java.util.*

class SurveyListDetailResponseActivity : AppCompatActivity() {



    private lateinit var binding: ActivitySurveylistdetailresponseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurveylistdetailresponseBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarSurveyListDetailResponse)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarSurveyListDetailResponse.setNavigationOnClickListener {
            onBackPressed()
        }

    }


    }