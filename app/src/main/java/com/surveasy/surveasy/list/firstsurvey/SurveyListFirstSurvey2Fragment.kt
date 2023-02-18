package com.surveasy.surveasy.list.firstsurvey


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.amplitude.api.Amplitude
import com.google.firebase.auth.ktx.auth
import com.surveasy.surveasy.R
import com.surveasy.surveasy.login.CurrentUserViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.MainRepository
import com.surveasy.surveasy.MainViewModel
import com.surveasy.surveasy.MainViewModelFactory
import com.surveasy.surveasy.databinding.FragmentSurveylistfirstsurvey2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class SurveyListFirstSurvey2Fragment() : Fragment() {

    val db = Firebase.firestore
    val userModel by activityViewModels<CurrentUserViewModel>()
    val firstSurveyModel by activityViewModels<FirstSurveyViewModel>()
    
    private lateinit var fsViewModel : FSViewModel
    private lateinit var fsViewModelFactory: FSViewModelFactory

    private var _binding : FragmentSurveylistfirstsurvey2Binding? = null
    private val binding get() = _binding!!
    private lateinit var districtSpinner : Spinner
    private var city : String? = null
    private var district : String? = null
    private var married: String? = null
    private var pet : String? = null
    private var family : String? = null
    private var housingType: String? = null
    private lateinit var uid : String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurveylistfirstsurvey2Binding.inflate(layoutInflater)
        val view = binding.root
        
        uid = Firebase.auth.uid.toString()
        districtSpinner = view.findViewById(R.id.SurveyListFirstSurvey2_DistrictSpinner)
        
        fsViewModelFactory = FSViewModelFactory(FirstSurveyRepository())
        fsViewModel = ViewModelProvider(this, fsViewModelFactory)[FSViewModel::class.java]
        
        setCitySpinner(view)
        setDistrictSpinner(districtSpinner, 0)
        //setPetSpinner(view)
        setHousingTypeSpinner(view)
        setFamilyTypeSpinner(view)

        // pet
        binding.SurveyListFirstSurvey2PetRadioGroup.setOnCheckedChangeListener { petRadioGroup, checked ->
            when(checked) {
                R.id.SurveyListFirstSurvey2_PetDog -> pet = "반려견"
                R.id.SurveyListFirstSurvey2_PetCat -> pet = "반려묘"
                R.id.SurveyListFirstSurvey2_PetEtc -> pet = "기타"
                R.id.SurveyListFirstSurvey2_PetNone -> pet = "없음"
            }
        }

        // married
        binding.SurveyListFirstSurvey2MarriedRadioGroup.setOnCheckedChangeListener { marriedRadioGroup, checked ->
            when(checked) {
                R.id.SurveyListFirstSurvey2_MarriedYet -> married = "미혼"
                R.id.SurveyListFirstSurvey2_Married -> married = "기혼"
                R.id.SurveyListFirstSurvey2_MarriedDivorce -> married = "이혼"
            }
        }

        binding.SurveyListFirstSurvey2Btn.setOnClickListener{
            firstSurveyFin()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun firstSurveyFin() {
        if(city == "시/도") {
            Toast.makeText(context, "시/도를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(district == "시/군/구") {
            Toast.makeText(context, "시/군/구를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(pet == null) {
            Toast.makeText(context, "반려동물 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(married == null) {
            Toast.makeText(context, "혼인 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(family == null || family == "가구 형태를 선택해주세요") {
            Toast.makeText(context, "가구 형태를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(housingType == null || housingType == "주거 형태를 선택해주세요") {
            Toast.makeText(context, "주거 형태를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            firstSurveyModel.firstSurvey.city = city
            firstSurveyModel.firstSurvey.district = district
            firstSurveyModel.firstSurvey.married = married
            firstSurveyModel.firstSurvey.pet = pet
            firstSurveyModel.firstSurvey.family = family
            firstSurveyModel.firstSurvey.housingType = housingType

            uploadFB()

            // [Amplitude] First Survey Fin
            val client = Amplitude.getInstance()
            client.logEvent("First Survey Fin")

            val intent : Intent = Intent(context, SurveyListFirstSurveyLast::class.java)
            startActivity(intent)
            (activity as SurveyListFirstSurveyActivity).fin()
        }

    }

    private fun uploadFB() {
        CoroutineScope(Dispatchers.Main).launch {
            fsViewModel.updateDidFS(uid)
            fsViewModel.updateReward(uid)
            fsViewModel.setUserSurveyList(uid)
            val firstSurvey = FSCollectionModel(
                firstSurveyModel.firstSurvey.job,
                firstSurveyModel.firstSurvey.major,
                firstSurveyModel.firstSurvey.university,
                firstSurveyModel.firstSurvey.EngSurvey,
                firstSurveyModel.firstSurvey.military,
                firstSurveyModel.firstSurvey.city,
                firstSurveyModel.firstSurvey.district,
                firstSurveyModel.firstSurvey.married,
                firstSurveyModel.firstSurvey.pet,
                firstSurveyModel.firstSurvey.family,
                firstSurveyModel.firstSurvey.housingType
            )
            fsViewModel.addFSCollection(uid, firstSurvey)
            Log.d(TAG, "uploadFB: done, ${firstSurveyModel.firstSurvey}")
        }
    }


    private fun setCitySpinner(view: View) {
        val cityList = resources.getStringArray(R.array.city)
        val cityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, cityList)

        binding.SurveyListFirstSurvey2CitySpinner.adapter = cityAdapter
        binding.SurveyListFirstSurvey2CitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                city = cityList[position]
                if(position == 8) {
                    binding.SurveyListFirstSurvey2DistrictSpinner.visibility = View.GONE
                    district = ""
                }
                else {
                    binding.SurveyListFirstSurvey2DistrictSpinner.visibility = View.VISIBLE
                    setDistrictSpinner(binding.SurveyListFirstSurvey2DistrictSpinner, position)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }


    private fun setDistrictSpinner(districtSpinner: Spinner, cityPosition: Int) {
        var districtList = resources.getStringArray(R.array.서울)

        when(cityPosition) {
            1 -> { districtList = resources.getStringArray(R.array.서울) }
            2 -> { districtList = resources.getStringArray(R.array.부산) }
            3 -> { districtList = resources.getStringArray(R.array.대구) }
            4 -> { districtList = resources.getStringArray(R.array.인천) }
            5 -> { districtList = resources.getStringArray(R.array.광주) }
            6 -> { districtList = resources.getStringArray(R.array.대전) }
            7 -> { districtList = resources.getStringArray(R.array.울산) }
            9 -> { districtList = resources.getStringArray(R.array.경기) }
            10 -> { districtList = resources.getStringArray(R.array.강원) }
            11 -> { districtList = resources.getStringArray(R.array.충북) }
            12 -> { districtList = resources.getStringArray(R.array.충남) }
            13 -> { districtList = resources.getStringArray(R.array.전북) }
            14 -> { districtList = resources.getStringArray(R.array.전남) }
            15 -> { districtList = resources.getStringArray(R.array.경북) }
            16 -> { districtList = resources.getStringArray(R.array.경남) }
            17 -> { districtList = resources.getStringArray(R.array.제주) }
        }

        val districtAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, districtList)
        districtSpinner.adapter = districtAdapter
        districtSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                district = districtList[position]

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }


    private fun setHousingTypeSpinner(view: View) {
        val housingTypeList = resources.getStringArray(R.array.housingType)
        val housingTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, housingTypeList)
        binding.SurveyListFirstSurvey2HousingTypeSpinner.adapter = housingTypeAdapter
        binding.SurveyListFirstSurvey2HousingTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                housingType = housingTypeList[position]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setFamilyTypeSpinner(view: View) {
        val familyTypeList = resources.getStringArray(R.array.familyType)
        val familyTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, familyTypeList)
        binding.SurveyListFirstSurvey2FamilyTypeSpinner.adapter = familyTypeAdapter
        binding.SurveyListFirstSurvey2FamilyTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                family = familyTypeList[position]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }




}