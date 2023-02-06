package com.surveasy.surveasy
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import com.surveasy.surveasy.userRoom.User
//
//class RoomViewModel(private val repository: RoomRepository) : ViewModel() {
//
//    val getNumAll : Int = repository.getNumAll
//    val getUid : String = repository.getUid
//    val getBirth : Int = repository.getBirth
//    val getFcmToken : String = repository.getFcmToken
//    val getGender : String = repository.getGender
//    val getAutoLogin : Boolean = repository.getAutoLogin
//    val getAll : Array<User> = repository.getAll
//
//    suspend fun getNumUid(Uid : String) : Int{
//        return repository.getNumUid(Uid)
//    }
//
//    suspend fun insert(user : User){
//        repository.insert(user)
//    }
//    suspend fun update(vararg users : User){
//        repository.update(*users)
//    }
//    suspend fun updateFcm(Uid : String, FcmToken : String){
//        repository.updateFcm(Uid, FcmToken)
//    }
//    suspend fun delete(vararg users : User){
//        repository.delete(*users)
//    }
//    suspend fun deleteAll(){
//        repository.deleteAll()
//    }
//}