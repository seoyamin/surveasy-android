package com.surveasy.surveasy

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

interface MainRepositoryInterface {

    //fetchCurrentUser
    suspend fun fetchCurrentUser(uid : String)
    suspend fun test1() {}

}