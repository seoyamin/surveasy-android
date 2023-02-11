package com.surveasy.surveasy.my.notice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyNoticeViewModel(private val repository: MyNoticeRepository) : ViewModel() {
    private val _repositoriesFixArray = MutableLiveData<ArrayList<NoticeModel>>()
    val repositories1 : MutableLiveData<ArrayList<NoticeModel>>
        get() = _repositoriesFixArray
    private val _repositoriesArray = MutableLiveData<ArrayList<NoticeModel>>()
    val repositories2 : MutableLiveData<ArrayList<NoticeModel>>
        get() = _repositoriesArray


    suspend fun fetchNoticeData(cnt : Int){
        viewModelScope.launch {
            repository.fetchNoticeData(_repositoriesFixArray, _repositoriesArray, cnt)
        }
    }
}