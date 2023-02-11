package com.surveasy.surveasy.list.firstsurvey

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.*
import com.surveasy.surveasy.databinding.FragmentFirstsurveylistBinding
import com.surveasy.surveasy.login.CurrentUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirstSurveyListFragment : Fragment() {
    private var _binding : FragmentFirstsurveylistBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory : MainViewModelFactory
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstsurveylistBinding.inflate(layoutInflater)

        mainViewModelFactory = MainViewModelFactory(MainRepository())
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        val view = binding.root
        //여기는 모델 쓰기
        binding.firstSurveyContainer.setOnClickListener {
            (activity as MainActivity).clickItem()
        }
        CoroutineScope(Dispatchers.Main).launch {
            mainViewModel.fetchCurrentUser(Firebase.auth.uid.toString())
            mainViewModel.repositories1.observe(viewLifecycleOwner){
                binding.FirstSurveyListItemName.text = "${it.name}님에 대해 알려주세요!"

            }
        }


        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}