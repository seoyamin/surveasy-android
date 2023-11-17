package com.surveasy.surveasy.presentation.splash

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.amplitude.api.Amplitude
import com.surveasy.surveasy.home.NetworkAlertActivity
import com.surveasy.surveasy.presentation.login.LoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.surveasy.surveasy.presentation.MainActivity
import com.surveasy.surveasy.R

class SplashActivity : AppCompatActivity() {
    val db = Firebase.firestore
    var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        // Initialization of Amplitude
        val client = Amplitude.getInstance()
            .initialize(getApplicationContext(), "ae22fbd62558adb236f993284cc62c71")
            .enableForegroundTracking(application)


        if(isConnectInternet() != "null"){
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({

                finish()

            },4000)
        }else{
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                val intent = Intent(baseContext, NetworkAlertActivity::class.java)
                startActivity(intent)
                finish()

            },4000)
        }



        supportActionBar?.hide()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(
                        ContentValues.TAG,
                        "Fetching FCM registration token failed",
                        task.exception
                    )
                    return@OnCompleteListener
                }
                token = task.result

            })


        db.collection("AndroidFirstScreen").get()
            .addOnSuccessListener { result ->
                nextActivity()
//                var i = 0
//                for(document in result){   if(document.id == token) i++   }
//                if(i==0){
//                    startActivity(Intent(this,FirstIntroduceScreenActivity::class.java))
//                    finish()
//                }
//                else {
//                    nextActivity()
//
////                    var bannerIndex = 0
////                    for(item in bannerModel.uriList) {
////                        intent.putExtra(item, "banner" + bannerIndex.toString())
////                        bannerIndex++
////                    }
//                }
            }

    }


    private fun isConnectInternet() : String {
        val cm : ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo : NetworkInfo? = cm.activeNetworkInfo
        return networkInfo.toString()
    }

    private fun nextActivity() {

        // [1] 현재 Firebase auth CurrentUser 존재하지 않는 경우 (로그아웃 상태)
        if (Firebase.auth.currentUser == null) {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        // [2] 현재 Firebase auth CurrentUser 존재하는 경우 (로그인 상태)
        else {

            // CurrentUser의 autoLogin 상태 확인
            db.collection("panelData").document(Firebase.auth.currentUser!!.uid)
                .get().addOnSuccessListener { snapshot ->
                    if (snapshot["autoLogin"] == false) {
                        intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }


            // [Amplitude] app-start
            val client = Amplitude.getInstance()
            client.userId = Firebase.auth.currentUser!!.uid
            client.logEvent("app_start")
        }
    }




}