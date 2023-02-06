package com.surveasy.surveasy

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.login.CurrentUser
import com.surveasy.surveasy.model.ContributionModel
import com.surveasy.surveasy.model.OpinionAModel
import com.surveasy.surveasy.model.OpinionQModel
import com.surveasy.surveasy.model.SurveyModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.log

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private val _repositoriesFetchCurrentUser = MutableLiveData<CurrentUser>()
    val repositories1 : MutableLiveData<CurrentUser>
        get() = _repositoriesFetchCurrentUser
    private val _repositoriesFetchBannerImg = MutableLiveData<ArrayList<String>>()
    val repositories2 : MutableLiveData<ArrayList<String>>
        get() = _repositoriesFetchBannerImg
    private val _repositoriesFetchContribution = MutableLiveData<ArrayList<ContributionModel>>()
    val repositories3 : MutableLiveData<ArrayList<ContributionModel>>
        get() = _repositoriesFetchContribution
    private val _repositoriesFetchOpinionQ = MutableLiveData<OpinionQModel>()
    val repositories4 : MutableLiveData<OpinionQModel>
        get() = _repositoriesFetchOpinionQ
    private val _repositoriesFetchOpinionA = MutableLiveData<List<OpinionAModel>>()
    val repositories5 : MutableLiveData<List<OpinionAModel>>
        get() = _repositoriesFetchOpinionA
    private val _repositoriesFetchSurvey = MutableLiveData<ArrayList<SurveyModel>>()
    val repositories6 : MutableLiveData<ArrayList<SurveyModel>>
        get() = _repositoriesFetchSurvey

    private val db = Firebase.firestore

    init {
        Log.d(TAG, ": MainViewMode init")
    }

    suspend fun fetchCurrentUser(uid : String){
        viewModelScope.launch {
            repository.fetchCurrentUser(uid, _repositoriesFetchCurrentUser)

        }
    }

    suspend fun fetchSurvey(userAge : Int, userGender : String){
        viewModelScope.launch {
            repository.fetchSurvey(_repositoriesFetchSurvey, userAge, userGender)
        }
    }

    suspend fun fetchBannerImg(){
        viewModelScope.launch {
            repository.fetchBannerImg(_repositoriesFetchBannerImg)
        }
    }

    suspend fun fetchContribution(){
        viewModelScope.launch {
            repository.fetchContribution(_repositoriesFetchContribution)

        }
    }

    suspend fun fetchOpinion(){
        viewModelScope.launch {
            repository.fetchOpinion(_repositoriesFetchOpinionQ, _repositoriesFetchOpinionA)
            Log.d(TAG, "fetchCurrentUser: 뷰모델")
        }
    }


}