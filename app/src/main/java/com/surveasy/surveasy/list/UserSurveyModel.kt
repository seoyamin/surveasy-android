package com.surveasy.surveasy.list

data class UserSurveyModel(
    val id : Int,
    val idChecked : Int,
    val title : String?,
    val reward : Int?,
    val responseDate : String?,
    val isSent : Boolean?,
    val filePath : String?
)
