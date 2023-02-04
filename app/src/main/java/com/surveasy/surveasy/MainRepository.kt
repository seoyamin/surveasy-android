package com.surveasy.surveasy

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.amplitude.api.Amplitude
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.surveasy.surveasy.home.Opinion.AnswerItem
import com.surveasy.surveasy.home.Opinion.OpinionItem
import com.surveasy.surveasy.home.contribution.ContributionItems
import com.surveasy.surveasy.list.UserSurveyItem
import com.surveasy.surveasy.login.CurrentUser
import com.surveasy.surveasy.model.ContributionModel
import com.surveasy.surveasy.model.OpinionAModel
import com.surveasy.surveasy.model.OpinionQModel
import com.surveasy.surveasy.userRoom.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class MainRepository : MainRepositoryInterface {
    companion object {
        val instance = MainRepository()
    }

    private val db = Firebase.firestore
    val tt = MutableLiveData<Dto>()

    // fetchCurrentUser in fragment
    override suspend fun fetchCurrentUser(uid: String, model : MutableLiveData<CurrentUser>) {
        val docRef = db.collection("panelData").document(uid.toString())
        val userSurveyList = ArrayList<UserSurveyItem>()

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

        docRef.get().addOnCompleteListener{ snapshot ->
            val currentUser : CurrentUser = CurrentUser(
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
            model.postValue(currentUser)
            setAmplitude(currentUser)

        }
        Log.d(TAG, "fetchCurrentUser: ㄹㅔ포")

    }


    // Get banner img uri from Firebase Storage
    override suspend fun fetchBannerImg(model : MutableLiveData<ArrayList<String>>) {
        val storage : FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef : StorageReference = storage.reference.child("banner")
        val listAllTask: Task<ListResult> = storageRef.listAll()

        listAllTask.addOnSuccessListener { result ->
            val items : List<StorageReference> = result.items
            val itemNum : Int = result.items.size
            val uriList = ArrayList<String>()
            //bannerModel.num = itemNum
            items.forEachIndexed { index, item ->
                item.downloadUrl.addOnSuccessListener {
                    //bannerModel.uriList.add(it.toString())
                    uriList.add(it.toString())
                    model.postValue(uriList)
                }
            }
            //model.value = uriList
            //model.postValue(uriList)
            //Log.d(TAG, "fetchBannerImg: repository $uriList")
        }
    }

    //fetch contribution
    override suspend fun fetchContribution(model: MutableLiveData<ArrayList<ContributionModel>>) {
        db.collection("AppContribution").get()
            .addOnSuccessListener { documents ->
                if(documents != null) {
                    var contributionList = ArrayList<ContributionModel>()
                    for (document in documents) {
                        val contribution = ContributionModel(
                            document["date"].toString(),
                            document["title"].toString(),
                            document["journal"].toString(),
                            document["source"].toString(),
                            document["institute"].toString(),
                            document["img"].toString(),
                            document["content"].toString()

                        )
                        contributionList.add(contribution)
                    }
                    model.postValue(contributionList)
                }

            }
    }
    private fun fetchContribution() {


    }

    override suspend fun fetchOpinion(model1 : MutableLiveData<OpinionQModel>, model2 : MutableLiveData<List<OpinionAModel>>) {
        db.collection("AppOpinion").whereEqualTo("isValid", true)
            .get().addOnCompleteListener{ documents ->
                val data = documents.result.documents[0]
                val question = OpinionQModel(
                    Integer.parseInt(data["id"].toString()),
                    data["question"].toString(),
                    data["content1"].toString(),
                    data["content2"].toString()
                )
                model1.postValue(question)
            }
        db.collection("AppAnswer").get()
            .addOnSuccessListener { documents ->
                var answerList = ArrayList<OpinionAModel>()
                if(documents != null){
                    for (document in documents){
                        val answerItem  = OpinionAModel(
                            Integer.parseInt(document["id"].toString()) as Int,
                            document["question"] as String,
                            document["content1"] as String?,
                            document["content2"] as String?,
                            document["content3"] as String?
                        )
                        answerList.add(answerItem)
                    }
                }
                model2.postValue(answerList.sortedByDescending { it.id })
            }.addOnFailureListener{ exception ->
                Log.d(TAG, "fail $exception")
            }
    }



    // [Amplitude] user properties (name, reward_total)
    override fun setAmplitude(model : CurrentUser) {
        val client = Amplitude.getInstance()
        val userProperties = JSONObject()
        try {
            userProperties.put("name", model.name)
                .put("reward_total", model.rewardTotal)
                .put("birthYear", model.birthDate!!.substring(0, 4))
                .put("gender", model.gender)
        } catch (e: JSONException) {
            e.printStackTrace()
            System.err.println("Invalid JSON")
        }
        client.setUserProperties(userProperties)
    }

    }
