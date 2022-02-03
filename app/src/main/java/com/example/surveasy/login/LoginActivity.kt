package com.example.surveasy.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.surveasy.MainActivity
import com.example.surveasy.databinding.ActivityLoginBinding
import com.example.surveasy.list.SurveyItems
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)


        setContentView(binding.root)

        binding.LoginBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setSupportActionBar(binding.ToolbarLogin)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarLogin.setNavigationOnClickListener { onBackPressed() }

        binding.LoginKakao.setOnClickListener{
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                if(error != null){
                    Toast.makeText(this,"로그인 실패 $error",Toast.LENGTH_LONG).show()
                }else if (token != null){
                    Toast.makeText(this,"로그인 성공 ${ token.accessToken }",Toast.LENGTH_LONG).show()
                }

                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.d(TAG,"fail")
                    } else if (user != null) {

                        val userInfo = hashMapOf(
                            "id" to user.id,
                            "name" to user.kakaoAccount?.profile?.nickname
                        )

                        db.collection("AppTestUser").document(user.id.toString())
                            .set(userInfo)

                        Log.d(
                            TAG, "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                        )




                    }
                }


                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


}