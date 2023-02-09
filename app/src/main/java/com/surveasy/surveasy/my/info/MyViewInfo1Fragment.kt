package com.surveasy.surveasy.my.info

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentMyviewinfo1Binding


class MyViewInfo1Fragment : Fragment() {
    val infoDataModel by activityViewModels<InfoDataViewModel>()
    val set: Boolean = false
    private var _binding : FragmentMyviewinfo1Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyviewinfo1Binding.inflate(layoutInflater)
        // (activity as MyViewInfoActivity).fetchInfoData()
        val view = binding.root

        setVariableInfo(view, infoDataModel.infoData)

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setVariableInfo(view: View, infoData: InfoData) {
        binding.MyViewInfoInfoItemPhoneNumber.text = infoData.phoneNumber
        binding.MyViewInfoInfoItemAccountType.text = infoData.accountType
        binding.MyViewInfoInfoItemAccountNumber.text = infoData.accountNumber

        if(infoData.EngSurvey == true) {
            binding.MyViewInfoInfoItemEngSurvey.text = "희망함"
        }
        else if(infoData.EngSurvey == false) {
            binding.MyViewInfoInfoItemEngSurvey.text = "희망하지 않음"
        }
    }

}