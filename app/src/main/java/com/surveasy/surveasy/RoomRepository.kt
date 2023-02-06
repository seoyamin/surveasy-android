package com.surveasy.surveasy

//import androidx.lifecycle.LiveData
//import androidx.room.Room
//import com.surveasy.surveasy.userRoom.User
//import com.surveasy.surveasy.userRoom.UserDao
//import com.surveasy.surveasy.userRoom.UserDatabase
//
//class RoomRepository {
//
//    companion object{
//        val UserDao : UserDao
//    }
//
//    private val dao = UserDao
//
//    val getNumAll : Int = dao.getNumAll()
//    val getUid = dao.getUid()
//    val getBirth = dao.getBirth()
//    val getGender = dao.getGender()
//    val getFcmToken = dao.getFcmToken()
//    val getAutoLogin = dao.getAutoLogin()
//    val getAll = dao.getAll()
//    suspend fun getNumUid(Uid : String) : Int{
//        return dao.getNumUid(Uid)
//    }
//    suspend fun insert(user : User){
//        dao.insert(user)
//    }
//    suspend fun update(vararg users : User){
//        dao.update(*users)
//    }
//    suspend fun updateFcm(Uid : String, FcmToken : String){
//        dao.updateFcm(Uid, FcmToken)
//    }
//    suspend fun delete(vararg users : User){
//        dao.delete(*users)
//    }
//    suspend fun deleteAll(){
//        dao.deleteAll()
//    }
//
//}