package com.surveasy.surveasy.presentation.main.survey

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivitySurveyBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import com.surveasy.surveasy.presentation.util.IntentId.SURVEY_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyActivity : BaseActivity<ActivitySurveyBinding>(ActivitySurveyBinding::inflate) {
    private val viewModel: SurveyViewModel by viewModels()
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is SurveyEvents.NavigateToList -> toList()
                    is SurveyEvents.NavigateToBack -> finish()
                    else -> Unit
                }
            }
        }
    }

    override fun initView() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val sId = intent.getIntExtra(SURVEY_ID, -1)
        viewModel.setSurveyId(sId)

    }

    override fun initData() = Unit

    private fun toList() = finish()

}