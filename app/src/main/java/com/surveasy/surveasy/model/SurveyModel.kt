package com.surveasy.surveasy.model

data class SurveyModel(
    val id : Int,
    val idChecked : Int,
    val title : String,
    val target : String,
    val uploadDate : String?,
    val link : String?,
    val spendTime : String?,
    val dueDate : String?,
    val dueTimeTime : String?,
    val reward : Int,
    val noticeToPanel : String?,
    val progress : Int,
    val targetingAge : Int?,
    val targetingGender : Int?
)
