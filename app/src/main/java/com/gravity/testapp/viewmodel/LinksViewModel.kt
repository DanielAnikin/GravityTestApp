package com.gravity.testapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gravity.testapp.network.Links
import com.gravity.testapp.network.LinksApi.retrofitService
import kotlinx.coroutines.launch

enum class LinksApiStatus { LOADING, ERROR, DONE }

class LinksViewModel : ViewModel() {
    val linkData = MutableLiveData<Links>()
    val status = MutableLiveData<LinksApiStatus>()

    init { getLinksData() }

    private fun getLinksData() {
        viewModelScope.launch {
            status.value = LinksApiStatus.LOADING
            try {
                linkData.value = retrofitService.getLinks()
                status.value = LinksApiStatus.DONE
            } catch (e: Exception) {
                status.value = LinksApiStatus.ERROR
                linkData.value = Links("", "")
            }
        }
    }
}