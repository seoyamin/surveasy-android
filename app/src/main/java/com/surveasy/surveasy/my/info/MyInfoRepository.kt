package com.surveasy.surveasy.my.info

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.list.UserSurveyItem
import com.surveasy.surveasy.login.CurrentUser

class MyInfoRepository : MyInfoRepositoryInterface {

    companion object{
        val instance = MyInfoRepository()
    }

    private val db = Firebase.firestore

    override suspend fun fetchUserInfo(model: MutableLiveData<MyInfoModel>, uid: String) {
        val docRef = db.collection("panelData").document(uid)
        var engSurvey = false

        docRef.collection("FirstSurvey").document(uid).get()
            .addOnCompleteListener { snapshot ->
                engSurvey  = snapshot.result["EngSurvey"] as Boolean
            }

        docRef.get().addOnCompleteListener{ snapshot ->
            val userInfo = MyInfoModel(
                snapshot.result["name"].toString(),
                snapshot.result["birthDate"].toString(),
                snapshot.result["gender"].toString(),
                snapshot.result["email"].toString(),
                snapshot.result["phoneNumber"].toString(),
                snapshot.result["accountType"].toString(),
                snapshot.result["accountNumber"].toString(),
                engSurvey
            )
            model.postValue(userInfo)

        }

    }
    }
