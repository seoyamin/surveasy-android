package com.surveasy.surveasy.retrofit

import com.google.gson.annotations.SerializedName

data class PostTestDto(
    val enTarget : Boolean,
    @SerializedName("account_userName")
    val accountName : String,
    val dueDate : String,
    val institute : String,
    val link : String,
    val notice : String,
    @SerializedName("point_add")
    val pointAdd : Int,
    val price : Int,
    val priceIdentity : String,
    val requiredHeadCount : Int,
    val spendTime : String,
    val target : String,
    val targetingAge : Int,
    val targetingAgeOption : Int,
    val targetingAgeOptionList : String,
    val targetingGender : Int,
    val targetingGenderOptionList : String,
    val title : String
)

data class PostResponse(
    val surveyId : Int
)

