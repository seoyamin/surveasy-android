package com.surveasy.surveasy

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.amplitude.api.Amplitude
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.list.UserSurveyItem
import com.surveasy.surveasy.login.CurrentUser
import com.surveasy.surveasy.userRoom.User
import org.json.JSONException
import org.json.JSONObject

class MainRepository {
    companion object{
        val instance = MainRepository()
    }

    private val db = Firebase.firestore

    fun test() : String{
        var temp : String? = null
        db.collection("panelData").addSnapshotListener{ snap, e->
            for(doc in snap!!.documentChanges){
                if(doc.document.id=="lmhi8mpzo0YZGpx0NTRYkIdxAlE2"){
                    temp = doc.document["name"].toString()
                    Log.d(TAG, "test: $temp")
                }


            }
        }

        return "aa"
    }


    suspend fun fetchCurrentUser(uid: String) :ArrayList<CurrentUser> {
        val docRef = db.collection("panelData").document(uid.toString())
        val userSurveyList = ArrayList<UserSurveyItem>()
        var currentUserList = ArrayList<CurrentUser>()
        //var currentUser = CurrentUser(null, null, null, null, null, null, null, null, null, null, null, null, null, null)


        docRef.collection("UserSurveyList").get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    var item : UserSurveyItem = UserSurveyItem(
                        Integer.parseInt(document["id"].toString()),
                        Integer.parseInt(document["lastIDChecked"].toString()),
                        document["title"] as String?,
                        Integer.parseInt(document["panelReward"].toString()) as Int?,
                        document["responseDate"] as String?,
                        document["isSent"] as Boolean?,
                        null
                    )
                    userSurveyList.add(item)

                }
            }


        docRef.get().addOnCompleteListener { snapshot ->
            if(snapshot != null) {

//                // Local Room DB에 current user의 User 객체 저장하기
//                val uidNum = userDB.userDao().getNumUid(snapshot.result["uid"].toString())
//
//                // [case 1] 해당 uid 가진 튜플 없는 경우 (INSERT user info)
//                if(uidNum == 0) {
//                    userDB.userDao().deleteAll()
//                    val user : User = User(
//                        snapshot.result["uid"].toString(),
//                        Integer.parseInt(snapshot.result["birthDate"].toString().substring(0, 4)),
//                        snapshot.result["gender"].toString(),
//                        snapshot.result["fcmToken"].toString(),
//                        snapshot.result["autoLogin"] as Boolean,
//
//                        )
//                    userDB.userDao().insert(user)
//                }
//
//                // [case 2] 이미 동일한 uid의 튜플이 저장된 경우 (UPDATE fcm token)
//                else if(uidNum == 1) {
//                    userDB.userDao().updateFcm(snapshot.result["uid"].toString(),snapshot.result["fcmToken"].toString())
//                }


                // userModel에 유저 정보 저장
                var currentUser = CurrentUser(
                    snapshot.result["uid"].toString(),
                    snapshot.result["fcmToken"].toString(),
                    snapshot.result["name"].toString(),
                    snapshot.result["email"].toString(),
                    snapshot.result["phoneNumber"].toString(),
                    snapshot.result["gender"].toString(),
                    snapshot.result["birthDate"].toString(),
                    snapshot.result["accountType"].toString(),
                    snapshot.result["accountNumber"].toString(),
                    snapshot.result["accountOwner"].toString(),
                    snapshot.result["inflowPath"].toString(),
                    snapshot.result["didFirstSurvey"] as Boolean?,
                    snapshot.result["autoLogin"] as Boolean?,
                    Integer.parseInt(snapshot.result["reward_current"].toString()),
                    Integer.parseInt(snapshot.result["reward_total"].toString()),
                    snapshot.result["marketingAgree"] as Boolean?,
                    userSurveyList
                )

                currentUserList.add(currentUser)



                // [Amplitude] user properties (name, reward_total)
                val client = Amplitude.getInstance()
                val userProperties = JSONObject()
                try {
                    userProperties.put("name", currentUser.name)
                        .put("reward_total", currentUser.rewardTotal)
                        .put("birthYear", currentUser.birthDate!!.substring(0, 4))
                        .put("gender", currentUser.gender)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    System.err.println("Invalid JSON")
                }
                client.setUserProperties(userProperties)


            }
        }.addOnFailureListener { exception ->
            Log.d(ContentValues.TAG, "fail $exception")
        }
        return currentUserList
    }

}