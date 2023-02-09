package com.surveasy.surveasy.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.surveasy.surveasy.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.databinding.FragmentRegisterAgree1Binding

class RegisterAgree1Fragment : Fragment() {

    val db = Firebase.firestore
    private var _binding : FragmentRegisterAgree1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterAgree1Binding.inflate(layoutInflater)
        val view = binding.root
        val registerModel by activityViewModels<RegisterInfo1ViewModel>()



        binding.RegisterAgree1Btn.setOnClickListener {
            if(binding.registerAgree1Agree2.isChecked && binding.registerAgree1Agree3.isChecked){
                (activity as RegisterActivity).goAgree2()
            }else{
                Toast.makeText(context,"필수 항목에 동의해주세요",Toast.LENGTH_LONG).show()
            }
            if(binding.registerAgree1Agree4.isChecked){
                registerModel.registerInfo1.marketingAgree = true
            }
        }

        binding.registerGoTerm1.setOnClickListener {
            val intent = Intent(context,RegisterTerm1::class.java)
            startActivity(intent)
        }
        binding.registerGoTerm2.setOnClickListener {
            val intent = Intent(context,RegisterTerm2::class.java)
            startActivity(intent)
        }



        binding.registerAgree1Agree1.setOnClickListener { view ->
            if(binding.registerAgree1Agree1.isChecked){
                binding.registerAgree1Agree2.isChecked = true
                binding.registerAgree1Agree3.isChecked = true
                binding.registerAgree1Agree4.isChecked = true
            }else{
                binding.registerAgree1Agree2.isChecked = false
                binding.registerAgree1Agree3.isChecked = false
                binding.registerAgree1Agree4.isChecked = false
            }
            if(binding.registerAgree1Agree4.isChecked){
                binding.SNSAgreeText.text ="할인 쿠폰 및 혜택, 이벤트 등 유익한 정보를 SMS나\n" +
                        "이메일로 받아보실 수 있습니다."
            }else{
                binding.SNSAgreeText.text=""
            }

        }
        binding.registerAgree1Agree2.setOnClickListener {
            if(!binding.registerAgree1Agree2.isChecked||!binding.registerAgree1Agree3.isChecked||!binding.registerAgree1Agree4.isChecked){
                binding.registerAgree1Agree1.isChecked=false
            }
            if(binding.registerAgree1Agree2.isChecked && binding.registerAgree1Agree3.isChecked && binding.registerAgree1Agree4.isChecked){
                binding.registerAgree1Agree1.isChecked=true
            }
        }
        binding.registerAgree1Agree3.setOnClickListener {
            if(!binding.registerAgree1Agree2.isChecked||!binding.registerAgree1Agree3.isChecked||!binding.registerAgree1Agree4.isChecked){
                binding.registerAgree1Agree1.isChecked=false
            }
            if(binding.registerAgree1Agree2.isChecked && binding.registerAgree1Agree3.isChecked && binding.registerAgree1Agree4.isChecked){
                binding.registerAgree1Agree1.isChecked=true
            }
        }
        binding.registerAgree1Agree4.setOnClickListener {
            if(!binding.registerAgree1Agree2.isChecked||!binding.registerAgree1Agree3.isChecked||!binding.registerAgree1Agree4.isChecked){
                binding.registerAgree1Agree1.isChecked=false
            }
            if(binding.registerAgree1Agree2.isChecked && binding.registerAgree1Agree3.isChecked && binding.registerAgree1Agree4.isChecked){
                binding.registerAgree1Agree1.isChecked=true
            }
            if(binding.registerAgree1Agree4.isChecked){
                binding.SNSAgreeText.text ="할인 쿠폰 및 혜택, 이벤트 등 유익한 정보를 SMS나\n" +
                        "이메일로 받아보실 수 있습니다."
            }else{
                binding.SNSAgreeText.text=""
            }
        }




        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}