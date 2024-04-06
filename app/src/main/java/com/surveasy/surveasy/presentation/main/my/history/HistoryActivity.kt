package com.surveasy.surveasy.presentation.main.my.history

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityHistoryBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HistoryActivity : BaseActivity<ActivityHistoryBinding>(ActivityHistoryBinding::inflate) {
    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun initData() = Unit

    override fun initEventObserver() = repeatOnStarted {
        viewModel.events.collect {
            when(it){
                is HistoryEvents.NavigateToBack -> finish()
                else -> Unit
            }
        }
    }

    override fun initView() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
}