package com.example.surveasy.my

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.surveasy.databinding.ActivityMyviewcontactBinding

class MyViewContactActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMyviewcontactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewcontactBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarMyViewContact)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarMyViewContact.setNavigationOnClickListener { onBackPressed()  }

        binding.MyViewContactEmail.setOnClickListener {
//            val textToCopy = "surveasy"
//            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//            val clipData = ClipData.newPlainText("text",textToCopy)
//            clipboardManager.setPrimaryClip(clipData)
//
//            Toast.makeText(this,"복사되었습니다", Toast.LENGTH_LONG).show()
        }

    }
}