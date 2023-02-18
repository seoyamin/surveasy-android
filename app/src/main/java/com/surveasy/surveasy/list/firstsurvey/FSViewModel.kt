package com.surveasy.surveasy.list.firstsurvey

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FSViewModel(private val repository: FirstSurveyRepository) : ViewModel() {
    suspend fun updateDidFS(uid : String){
        repository.updateDidFS(uid)
    }

    suspend fun setUserSurveyList(uid: String){
        repository.setUserSurveyList(uid)
    }

    suspend fun updateReward(uid: String){
        repository.updateReward(uid)
    }

    suspend fun addFSCollection(uid : String, data : FSCollectionModel){
        repository.addFSCollection(uid, data)
    }
}