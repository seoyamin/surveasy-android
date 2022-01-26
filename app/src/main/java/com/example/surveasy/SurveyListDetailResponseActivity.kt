package com.example.surveasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class SurveyListDetailResponseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_surveylistdetailresponse)

        val surveyListDetailResponseToolbar : Toolbar? = findViewById(R.id.ToolbarSurveyListDetailResponse)
        setSupportActionBar(surveyListDetailResponseToolbar)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        surveyListDetailResponseToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}