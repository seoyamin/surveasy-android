package com.surveasy.surveasy.my.notice

import androidx.lifecycle.MutableLiveData

interface MyNoticeRepositoryInterface {
    suspend fun fetchNoticeData(model1 : MutableLiveData<ArrayList<NoticeModel>>, model2 : MutableLiveData<ArrayList<NoticeModel>>, cnt:Int)
}