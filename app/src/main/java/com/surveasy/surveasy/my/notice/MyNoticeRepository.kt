package com.surveasy.surveasy.my.notice

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyNoticeRepository : MyNoticeRepositoryInterface {
    companion object{
        val instance = MyNoticeRepository()
    }
    private val db = Firebase.firestore

    override suspend fun fetchNoticeData(
        model1: MutableLiveData<ArrayList<NoticeModel>>,
        model2: MutableLiveData<ArrayList<NoticeModel>>,
        cnt : Int
    ) {
        var fixArray = ArrayList<NoticeModel>()
        var array = ArrayList<NoticeModel>()
        db.collection("AppNotice").orderBy("id", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    // [Case 1] fixed
                    if (document["fixed"] == true) {
                        if (Integer.parseInt(document["id"].toString()) > cnt) {
                            var itemFixed = NoticeModel(
                                Integer.parseInt(document["id"].toString()),
                                document["title"] as String,
                                document["date"] as String,
                                document["content"] as String,
                                document["fixed"] as Boolean,
                                isOpened = false
                            )
                            fixArray.add(itemFixed)
                        } else {
                            var itemFixed = NoticeModel(
                                Integer.parseInt(document["id"].toString()),
                                document["title"] as String,
                                document["date"] as String,
                                document["content"] as String,
                                document["fixed"] as Boolean,
                                isOpened = true
                            )
                            fixArray.add(itemFixed)
                        }

                        model1.postValue(fixArray)
                    }

                    // [Case 2] not fixed
                    else {
                        if (Integer.parseInt(document["id"].toString()) > cnt) {
                            var item = NoticeModel(
                                Integer.parseInt(document["id"].toString()),
                                document["title"] as String,
                                document["date"] as String,
                                document["content"] as String,
                                document["fixed"] as Boolean,
                                isOpened = false
                            )
                            array.add(item)
                        } else {
                            var item= NoticeModel(
                                Integer.parseInt(document["id"].toString()),
                                document["title"] as String,
                                document["date"] as String,
                                document["content"] as String,
                                document["fixed"] as Boolean,
                                isOpened = true
                            )
                            array.add(item)
                        }
                        model2.postValue(array)
                    }
    }
    /*
    private fun fetchNoticeData(noticeDiff: Int, notice_room: Int) {
        noticeRecyclerView_Fixed = binding.recyclerNoticeContainerFixed
        noticeRecyclerView_Fixed.setHasFixedSize(true)
        noticeList_fixed = arrayListOf()


        noticeRecyclerView = binding.recyclerNoticeContainer
        noticeRecyclerView.setHasFixedSize(true)
        noticeList = arrayListOf()


        db = FirebaseFirestore.getInstance()
        db.collection("AppNotice").orderBy("id", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                noticeList_fixed.clear()
                noticeList.clear()

                for (document in result) {

                    // [Case 1] fixed
                    if (document["fixed"] == true) {
                        if (Integer.parseInt(document["id"].toString()) > notice_room) {
                            var item_fixed: NoticeItems = NoticeItems(
                                Integer.parseInt(document["id"].toString()),
                                document["title"] as String,
                                document["date"] as String,
                                document["content"] as String,
                                document["fixed"] as Boolean,
                                isOpened = false
                            )
                            noticeList_fixed.add(item_fixed)
                        } else {
                            var item_fixed: NoticeItems = NoticeItems(
                                Integer.parseInt(document["id"].toString()),
                                document["title"] as String,
                                document["date"] as String,
                                document["content"] as String,
                                document["fixed"] as Boolean,
                                isOpened = true
                            )
                            noticeList_fixed.add(item_fixed)
                        }

                        noticeRecyclerView_Fixed.layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        noticeRecyclerView_Fixed.adapter = NoticeFixedItemsAdapter(noticeList_fixed)
                    }

                    // [Case 2] not fixed
                    else {
                        if (Integer.parseInt(document["id"].toString()) > notice_room) {
                            var item: NoticeItems = NoticeItems(
                                Integer.parseInt(document["id"].toString()),
                                document["title"] as String,
                                document["date"] as String,
                                document["content"] as String,
                                document["fixed"] as Boolean,
                                isOpened = false
                            )
                            noticeList.add(item)
                        } else {
                            var item: NoticeItems = NoticeItems(
                                Integer.parseInt(document["id"].toString()),
                                document["title"] as String,
                                document["date"] as String,
                                document["content"] as String,
                                document["fixed"] as Boolean,
                                isOpened = true
                            )
                            noticeList.add(item)
                        }

                        noticeRecyclerView.layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        noticeRecyclerView.adapter = NoticeItemsAdapter(noticeList)
                    }


                    // Local DB에 new notice 추가
                    if (Integer.parseInt(document["id"].toString()) > notice_room) {
                        val notice: Notice =
                            Notice(Integer.parseInt(document["id"].toString()), true)
                        noticeDB.noticeDao().insert(notice)
                    }

                }

            }


    }

     */
            }
    }
}