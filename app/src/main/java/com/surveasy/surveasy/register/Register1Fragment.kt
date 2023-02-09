package com.surveasy.surveasy.register

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import com.surveasy.surveasy.R
import com.google.firebase.auth.FirebaseAuth
import com.surveasy.surveasy.databinding.FragmentRegister1Binding
import java.util.*


class Register1Fragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    val registerModel by activityViewModels<RegisterInfo1ViewModel>()
    var gender : String? = null
    var birthDate: String? = null
    var inflowPath: String? = null
    var cal = Calendar.getInstance()
    private var _binding : FragmentRegister1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegister1Binding.inflate(layoutInflater)
        val view = binding.root

        setInflowPathSpinner(view)


        // gender
        binding.RegisterFragment1RadioGroup.setOnCheckedChangeListener { genderRadioGroup, checkedId ->
            when (checkedId) {
                R.id.RegisterFragment1_RadioMale -> gender = "남"
                R.id.RegisterFragment1_RadioFemale -> gender = "여"

            }
        }

        // BirthDate
        birthDate = initYearPicker(view) + "-" + initMonthPicker(view) + "-" + initDayPicker(view)

        binding.RegisterFragment1Btn.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            register1(view)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // Register1
    private fun register1(view: View) {
        val name: String = binding.RegisterFragment1NameInput.text.toString()
        val email: String = binding.RegisterFragment1EmailInput.text.toString()
        val password = binding.RegisterFragment1PwInput.text.toString()
        val passwordCheck = binding.RegisterFragment1PwCheckInput.text.toString()
        val phoneNumber: String = binding.RegisterFragment1PhoneNumberInput.text.toString()
        if(inflowPath == "기타" || inflowPath == "") inflowPath = binding.RegisterFragment1EtcInflowInput.text.toString()
        birthDate = initYearPicker(view) + "-" + initMonthPicker(view) + "-" + initDayPicker(view)

        if(name == "") {
            Toast.makeText(context, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(name.length==1){
            Toast.makeText(context, "이름을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(name.contains(" ")){
            Toast.makeText(context, "이름란의 공백을 지워주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (email == "" || !email.contains("@") || !email.contains(".")) {
            Toast.makeText(context, "이메일을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(email.contains(" ")){
            Toast.makeText(context, "아이디란의 공백을 지워주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (password == "") {
            Toast.makeText(context, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(password.length<8){
            Toast.makeText(context, "8자리 이상의 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (password != passwordCheck) {
            Toast.makeText(context, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(phoneNumber.contains("-")||phoneNumber.contains(".") || phoneNumber.contains(" ")){
            Toast.makeText(context, "휴대폰번호란에는 숫자만 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (phoneNumber == "" || phoneNumber.length != 11) {
            Toast.makeText(context, "휴대폰번호를 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (gender == null) {
            Toast.makeText(context, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if (birthDate == null) {
            Toast.makeText(context, "생년월일을 선택해주세요.", Toast.LENGTH_SHORT).show()

        }
        else if (inflowPath == "유입경로를 선택하세요") {
            Toast.makeText(context, "유입경로를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(inflowPath == "") {
            Toast.makeText(context, "기타 유입경로를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            val registerInfo1 = RegisterInfo1(null, null, name, email, password, phoneNumber, gender, birthDate, inflowPath, registerModel.registerInfo1.marketingAgree)

            registerModel.registerInfo1 = registerInfo1
            (activity as RegisterActivity).goToRegister2()

        }

    }

    // Birth Pickers
    private fun initYearPicker(view: View): String {
        val today = Calendar.getInstance()
        val currentYear = today.get(Calendar.YEAR)
//        numberPicker.minValue = currentYear - 35
        binding.RegisterFragment1Year.minValue = 1950
        binding.RegisterFragment1Year.maxValue = currentYear
        binding.RegisterFragment1Year.wrapSelectorWheel = false

        var year : Int = binding.RegisterFragment1Year.minValue
        year = binding.RegisterFragment1Year.value
        return year.toString()
    }

    private fun initMonthPicker(view: View): String {
        binding.RegisterFragment1Month.minValue = 1
        binding.RegisterFragment1Month.maxValue = 12
        binding.RegisterFragment1Month.wrapSelectorWheel = false

        var month : Int = binding.RegisterFragment1Month.minValue
        month = binding.RegisterFragment1Month.value
        var monthStr: String = month.toString()
        if (month < 10) monthStr = "0" + monthStr
        return monthStr
    }

    private fun initDayPicker(view: View): String {
        binding.RegisterFragment1Date.minValue = 1
        binding.RegisterFragment1Date.maxValue = 31
        binding.RegisterFragment1Date.wrapSelectorWheel = false

        var day : Int = binding.RegisterFragment1Date.minValue
        day = binding.RegisterFragment1Date.value
        var dayStr: String = day.toString()
        if (day < 10) dayStr = "0" + dayStr
        return dayStr
    }

    private fun setInflowPathSpinner(view: View) {
        val inflowPathList = resources.getStringArray(R.array.inflowPath)
        val inflowPathAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, inflowPathList)

        binding.RegisterFragment1InflowPathSpinner.adapter = inflowPathAdapter
        binding.RegisterFragment1InflowPathSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                inflowPath = inflowPathList[position]
//                Log.d(TAG, "@@@@@@@ inflow : $inflowPath")
                if(inflowPath == "기타") {
                    binding.EtcInflowPathContainer.visibility = View.VISIBLE
                }
                else binding.EtcInflowPathContainer.visibility = View.GONE
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
}