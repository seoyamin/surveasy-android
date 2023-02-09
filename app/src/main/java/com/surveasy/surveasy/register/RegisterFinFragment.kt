package com.surveasy.surveasy.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterfinBinding
import com.surveasy.surveasy.login.LoginActivity


class RegisterFinFragment : Fragment() {
    private var _binding : FragmentRegisterfinBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterfinBinding.inflate(layoutInflater)
        val view = binding.root
        (activity as RegisterActivity).toolbarHide()

        binding.RegisterFinFragmentBtn.setOnClickListener {
            val intent : Intent = Intent(context, LoginActivity::class.java)
            (activity as RegisterActivity).fin()
            startActivity(intent)

        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}