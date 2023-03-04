package com.surveasy.surveasy.list

import androidx.lifecycle.ViewModel
import com.surveasy.surveasy.model.SurveyModel

//기본 Survey list
class SurveyInfoViewModel:ViewModel() {

    val surveyInfo = ArrayList<SurveyModel>()

    fun setData(surveyItems: SurveyModel){
        surveyInfo.add(surveyItems)
    }
    //마감 임박순으로 정렬
    fun sortSurvey() : ArrayList<SurveyModel>{
        val surveylist = arrayListOf<SurveyModel>()
        surveyInfo.sortWith(compareBy<SurveyModel> { it.dueDate }.thenBy { it.dueTimeTime })
        surveylist.addAll(surveyInfo)

        return surveylist
    }

    //최신 등록순 정렬
//    fun sortSurveyRecent() : ArrayList<SurveyItems>{
//        val surveylist = arrayListOf<SurveyItems>()
//        surveyInfo.sortWith(compareByDescending<SurveyItems> { it.uploadDate })
//        surveylist.addAll(surveyInfo)
//
//        return surveylist
//    }



}