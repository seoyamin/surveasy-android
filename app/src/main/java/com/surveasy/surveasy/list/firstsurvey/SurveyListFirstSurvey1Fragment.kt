package com.surveasy.surveasy.list.firstsurvey


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.surveasy.surveasy.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.databinding.FragmentSurveylistfirstsurvey1Binding


class SurveyListFirstSurvey1Fragment() : Fragment() {

    val db = Firebase.firestore
    val firstSurveyModel by activityViewModels<FirstSurveyViewModel>()
    private var _binding : FragmentSurveylistfirstsurvey1Binding? = null
    private val binding get() = _binding!!
    private lateinit var job : String
    private lateinit var major : String
    private lateinit var universityList : Array<String>
    private var university: String? = null
    private var military : String? = null
    private var engSurvey : Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurveylistfirstsurvey1Binding.inflate(layoutInflater)
        val view =  binding.root
        universityList = resources.getStringArray(R.array.university)

        // Set spinners
        setJobSpinner(view)
        setMajorSpinner(view)
        setSearchSpinner(view, universityList)

        // EngSurvey
        binding.SurveyListFirstSurvey1EngSurveyRadioGroup.setOnCheckedChangeListener { engSurveyRadioGroup, checked ->
            when(checked) {
                R.id.SurveyListFirstSurvey1_EngSurvey_O -> engSurvey = true
                R.id.SurveyListFirstSurvey1_EngSurvey_X -> engSurvey = false
            }
        }


        // military
        binding.SurveyListFirstSurvey1MilitaryRadioGroup.setOnCheckedChangeListener { militaryRadioGroup, checked ->
            when(checked) {
                R.id.SurveyListFirstSurvey1_MilitaryDone -> military = "군필"
                R.id.SurveyListFirstSurvey1_MilitaryYet -> military = "미필"
                R.id.SurveyListFirstSurvey1_MilitaryNA -> military = "해당없음"
            }
        }

        // Next
        binding.SurveyListFirstSurvey1Btn.setOnClickListener {
            firstSurvey1(view)

        }



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun firstSurvey1(view: View) {
        if((job == "대학생" && university == "기타") || (job == "대학생" && university == "")) university = binding.SurveyListFirstSurvey1EtcUniv.text.toString()
        if((job == "대학원생" && university == "기타") || (job == "대학원생" && university == "")) university = binding.SurveyListFirstSurvey1EtcUniv.text.toString()


        if(job == "직업을 선택해주세요") Toast.makeText(context, "직업을 선택해주세요.", Toast.LENGTH_SHORT).show()

        else if(job == "대학생" && major == "소속 계열을 선택해주세요") Toast.makeText(context, "소속 계열을 선택해주세요.", Toast.LENGTH_SHORT).show()
        else if(job == "대학생" && university == "대학명을 선택해주세요") Toast.makeText(context, "대학명을 선택해주세요.", Toast.LENGTH_SHORT).show()
        else if(job == "대학생" && university == "") Toast.makeText(context, "대학명을 입력해주세요.", Toast.LENGTH_SHORT).show()

        else if(job == "대학원생" && major == "소속 계열을 선택해주세요") Toast.makeText(context, "소속 계열을 선택해주세요.", Toast.LENGTH_SHORT).show()
        else if(job == "대학원생" && university == "대학명을 선택해주세요") Toast.makeText(context, "대학명을 선택해주세요.", Toast.LENGTH_SHORT).show()
        else if(job == "대학원생" && university == "") Toast.makeText(context, "대학명을 입력해주세요.", Toast.LENGTH_SHORT).show()

        else if(engSurvey == null) {
            Toast.makeText(context, "영어 설문 참여 의사를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }

        else if(military == null) {
            Toast.makeText(context, "병역이행 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }

        else {
            val firstSurvey1 = FirstSurvey(job, major, university, engSurvey, military,
                null, null, null, null, null, null)

            firstSurveyModel.firstSurvey = firstSurvey1

            (activity as SurveyListFirstSurveyActivity).next()
        }

    }


    // Set spinners
    private fun setJobSpinner(view: View) {
        val jobList = resources.getStringArray(R.array.job)
        val jobAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, jobList)

        binding.SurveyListFirstSurvey1JobSpinner.adapter = jobAdapter
        binding.SurveyListFirstSurvey1JobSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                job = jobList[position]
                if(position == 1 || position == 2) {
                    binding.SurveyListFirstSurvey1GoneContainer.visibility = View.VISIBLE
                }
                else {
                    binding.SurveyListFirstSurvey1GoneContainer.visibility = View.GONE
                    major = ""
                    university = ""
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setMajorSpinner(view: View) {
        val majorList = resources.getStringArray(R.array.major)
        val majorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, majorList)
        binding.SurveyListFirstSurvey1MajorSpinner.adapter = majorAdapter
        binding.SurveyListFirstSurvey1MajorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                major = majorList[position]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setSearchSpinner(view: View, universityList: Array<String>) {
        val universityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, universityList)
        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerView.adapter = universityAdapter

        binding.spinnerView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                university = universityList[position]

                if(university == "기타") {
                    binding.SurveyListFirstSurvey1EtcUniv.visibility = View.VISIBLE
                    university = binding.SurveyListFirstSurvey1EtcUniv.text.toString()
                }

                else binding.SurveyListFirstSurvey1EtcUniv.visibility = View.GONE
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

    }




}