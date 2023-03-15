package com.surveasy.surveasy.my.history

import androidx.lifecycle.MutableLiveData
import com.surveasy.surveasy.list.UserSurveyModel

interface MyHistoryRepositoryInterface {
    suspend fun fetchHistoryList(
        waitModel : MutableLiveData<ArrayList<UserSurveyModel>>,
        finModel : MutableLiveData<ArrayList<UserSurveyModel>>,
        uid : String
    )
}