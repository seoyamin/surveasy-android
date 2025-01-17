package com.surveasy.surveasy.presentation.main.home

import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentHomeBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.MainViewModel
import com.surveasy.surveasy.presentation.main.home.list.HomeListAdapter
import com.surveasy.surveasy.presentation.main.home.notice.HomeHowContentActivity
import com.surveasy.surveasy.presentation.main.home.notice.HomeNoticeActivity
import com.surveasy.surveasy.presentation.main.survey.SurveyActivity
import com.surveasy.surveasy.presentation.main.survey.fs.FsActivity
import com.surveasy.surveasy.presentation.util.IntentId
import com.surveasy.surveasy.presentation.util.showTwoButtonDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()
    private val adapter = HomeListAdapter {
        viewModel.navigateToSurveyDetail(it)
    }

    override fun initData() {
        viewModel.queryPanelInfo()
        viewModel.listHomeSurvey()
    }

    override fun initView() = with(binding) {
        vm = viewModel
        rvHomeList.adapter = adapter
        rvHomeList.animation = null
        drawUnderLine()
        tvAlertTitle.setOnClickListener { showAlertDialog() }
        finishApp()
        // repeatOnStarted { viewModel.getFcmToken() }
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is HomeEvents.ClickSurveyItem -> toSurveyDetail(it.id)
                    is HomeEvents.ShowSnackBar -> showSnackBar(it.msg, it.btn)
                    is HomeEvents.ClickHowContent -> toHowContent()
                    is HomeEvents.ClickNotice -> toNotice()
                    is HomeEvents.NavigateToFs -> toFs()
                    else -> Unit
                }
            }
        }
    }

    private fun toSurveyDetail(id: Int) {
        val intent = Intent(context, SurveyActivity::class.java)
        intent.putExtra(IntentId.SURVEY_ID, id)
        startActivity(intent)
    }

    private fun toHowContent() {
        startActivity(Intent(context, HomeHowContentActivity::class.java))
    }

    private fun toNotice() {
        startActivity(Intent(context, HomeNoticeActivity::class.java))
    }

    private fun toFs() {
        val intent = Intent(context, FsActivity::class.java)
        startActivity(intent)
    }

    private fun drawUnderLine() {
        val ss = SpannableString(binding.tvAlertTitle.text.toString())
        ss.setSpan(UnderlineSpan(), 0, ss.length, 0)
        binding.tvAlertTitle.text = ss
    }

    private fun showAlertDialog() = with(resources) {
        showTwoButtonDialog(
            requireContext(),
            getString(R.string.alert_title),
            getString(R.string.alert_content),
            getString(R.string.alert_yes),
            getString(R.string.alert_no),
            { goChatLink() },
            {}
        )
    }

    private fun goChatLink() {
        val uri = "https://open.kakao.com/o/g9K6Y3Qg"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

    private fun finishApp() {
        var backPressTime = 0L
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backPressTime <= 2000) {
                    parentViewModel.finishApp()
                } else {
                    backPressTime = System.currentTimeMillis()
                    showToastMessage(resources.getString(R.string.back_alert))
                }
            }
        })
    }
}
