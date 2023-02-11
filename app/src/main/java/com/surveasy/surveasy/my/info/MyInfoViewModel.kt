package com.surveasy.surveasy.my.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyInfoViewModel(private val repository: MyInfoRepository) : ViewModel() {
    //live data
    private val _repositoriesFetchInfoData = MutableLiveData<MyInfoModel>()
    val repositories1 : MutableLiveData<MyInfoModel>
        get() = _repositoriesFetchInfoData


    //acc viewModel
    var myInfoModel = ArrayList<MyInfoModel>()

    suspend fun fetchUserInfo(uid : String){
        repository.fetchUserInfo(_repositoriesFetchInfoData, uid)
    }

}