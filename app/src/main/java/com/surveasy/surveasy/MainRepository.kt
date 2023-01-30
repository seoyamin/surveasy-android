package com.surveasy.surveasy

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.amplitude.api.Amplitude
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.list.UserSurveyItem
import com.surveasy.surveasy.login.CurrentUser
import com.surveasy.surveasy.userRoom.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.json.JSONException
import org.json.JSONObject

class MainRepository : MainRepositoryInterface {
    companion object {
        val instance = MainRepository()
    }

    private val db = Firebase.firestore
    val tt = MutableLiveData<String>()

    override suspend fun test1() {
        db.collection("panelData").addSnapshotListener { snap, e ->
            for (doc in snap!!.documentChanges) {
                if (doc.document.id == "lmhi8mpzo0YZGpx0NTRYkIdxAlE2") {
                    val name = doc.document["name"].toString()
                    tt.value = name
                    Log.d(TAG, "test1: ${name}")

                }
            }
        }
    }

    }
