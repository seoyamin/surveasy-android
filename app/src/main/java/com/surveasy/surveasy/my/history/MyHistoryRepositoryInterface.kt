package com.surveasy.surveasy.my.history

import androidx.lifecycle.MutableLiveData
import com.surveasy.surveasy.list.UserSurveyModel

interface MyHistoryRepositoryInterface {
    suspend fun fetchHistoryList(
        finModel : MutableLiveData<ArrayList<UserSurveyModel>>,
        waitModel : MutableLiveData<ArrayList<UserSurveyModel>>,
        uid : String
    )
}