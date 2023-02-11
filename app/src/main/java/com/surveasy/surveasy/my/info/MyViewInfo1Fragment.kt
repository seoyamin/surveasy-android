package com.surveasy.surveasy.my.info

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.MainRepository
import com.surveasy.surveasy.MainViewModel
import com.surveasy.surveasy.MainViewModelFactory
import com.surveasy.surveasy.databinding.FragmentMyviewinfo1Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyViewInfo1Fragment : Fragment() {
    val infoDataModel by activityViewModels<InfoDataViewModel>()
    val set: Boolean = false
    private var _binding : FragmentMyviewinfo1Binding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory : MainViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyviewinfo1Binding.inflate(layoutInflater)
        // (activity as MyViewInfoActivity).fetchInfoData()
        mainViewModelFactory = MainViewModelFactory(MainRepository())
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        val view = binding.root
        setVariableInfo()

        

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setVariableInfo() {
        CoroutineScope(Dispatchers.Main).launch {
            mainViewModel.fetchCurrentUser(Firebase.auth.uid.toString())
            mainViewModel.repositories1.observe(viewLifecycleOwner){
                binding.MyViewInfoInfoItemPhoneNumber.text = it.phoneNumber
                binding.MyViewInfoInfoItemAccountType.text = it.accountType
                binding.MyViewInfoInfoItemAccountNumber.text = it.accountNumber
                //eng survey 방법 찾기
                binding.MyViewInfoInfoItemEngSurvey.text = "희망하지 않음"

            }
            }





    }


}