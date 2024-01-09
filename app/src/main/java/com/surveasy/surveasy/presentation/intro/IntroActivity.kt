package com.surveasy.surveasy.presentation.intro

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityRegisterBinding
import com.surveasy.surveasy.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityRegisterBinding>(ActivityRegisterBinding::inflate) {
    private val vm: RegisterViewModel by viewModels()
    private var navController: NavController? = null

    override fun initData() = Unit

    override fun initEventObserver() = Unit

    override fun initView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
    }
}