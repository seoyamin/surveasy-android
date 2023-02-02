package com.surveasy.surveasy

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.surveasy.surveasy.login.CurrentUser
import kotlinx.coroutines.flow.Flow

interface MainRepositoryInterface {

    //fetchCurrentUser
    suspend fun fetchCurrentUser(uid : String, model : MutableLiveData<CurrentUser>)
    fun setAmplitude(model : CurrentUser)
    suspend fun fetchBannerImg(model : MutableLiveData<ArrayList<String>>)

}