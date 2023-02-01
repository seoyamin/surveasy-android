package com.surveasy.surveasy.list.firstsurvey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.surveasy.surveasy.MainActivity
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentFirstsurveylistBinding
import com.surveasy.surveasy.login.CurrentUserViewModel

class FirstSurveyListFragment : Fragment() {
    private var _binding : FragmentFirstsurveylistBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstsurveylistBinding.inflate(layoutInflater)
        val userModel by activityViewModels<CurrentUserViewModel>()
        val view = binding.root

        binding.firstSurveyContainer.setOnClickListener {
            (activity as MainActivity).clickItem()
        }

        binding.FirstSurveyListItemName.text = "${userModel.currentUser.name}님에 대해 알려주세요!"


        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}