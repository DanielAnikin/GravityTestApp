package com.gravity.testapp

import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.gravity.testapp.viewmodel.LinksApiStatus

@BindingAdapter("linksApiStatus")
fun bindStatus(loadingAnim: LottieAnimationView, status: LinksApiStatus?) {
    when (status) {
        LinksApiStatus.LOADING -> loadingAnim.setAnimation(R.raw.loading)
        LinksApiStatus.ERROR -> loadingAnim.setAnimation(R.raw.error)
        else -> {}
    }
}
