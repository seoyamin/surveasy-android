package com.surveasy.surveasy.my.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surveasy.surveasy.list.UserSurveyModel

class MyHistoryViewModel(private val repository: MyHistoryRepository) : ViewModel() {
    private val _repositoriesFinList = MutableLiveData<ArrayList<UserSurveyModel>>()
    val repositories1 : MutableLiveData<ArrayList<UserSurveyModel>>
        get() = _repositoriesFinList
    private val _repositoriesWaitList = MutableLiveData<ArrayList<UserSurveyModel>>()
    val repositories2 : MutableLiveData<ArrayList<UserSurveyModel>>
        get() = _repositoriesWaitList

    suspend fun fetchHistoryList(uid : String){
        repository.fetchHistoryList(_repositoriesFinList, _repositoriesWaitList, uid)
    }
}