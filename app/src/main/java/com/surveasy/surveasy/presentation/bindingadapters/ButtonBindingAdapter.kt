package com.surveasy.surveasy.presentation.bindingadapters

import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter

@BindingAdapter("rewardBtn")
fun AppCompatButton.rewardBtn(reward: Int) {
    text = "${reward}원 받으러 가기"
}