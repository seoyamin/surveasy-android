package com.surveasy.surveasy.my.notice

data class NoticeModel(
    val id : Int,
    val title : String,
    val date : String,
    val content : String,
    val fixed : Boolean,
    val isOpened: Boolean
)
