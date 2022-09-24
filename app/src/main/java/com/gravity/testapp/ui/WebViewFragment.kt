package com.gravity.testapp.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gravity.testapp.R
import com.gravity.testapp.databinding.FragmentWebViewBinding
import com.gravity.testapp.utils.IS_FIRST_RUN
import com.gravity.testapp.utils.PrefManager
import com.gravity.testapp.utils.Utils.addBackPress
import com.gravity.testapp.utils.Utils.isNetworkConnected
import com.gravity.testapp.viewmodel.LinksViewModel

class WebViewFragment : Fragment(R.layout.fragment_web_view) {
    private val viewModel: LinksViewModel by activityViewModels()

    private lateinit var binding: FragmentWebViewBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addBackPress { onGoBack() }
        binding = FragmentWebViewBinding.bind(view)
        binding.apply {
            prepareWebView()

            btnGoBack.setOnClickListener { onGoBack() }
            btnGoForward.setOnClickListener { onGoForward() }
            swipeRefresh.setOnRefreshListener { refreshPage() }

            viewModel.linkData.observe(viewLifecycleOwner) { links ->
                val prefManager = PrefManager(requireContext())
                if (prefManager.loadBoolean(IS_FIRST_RUN)) {
                    loadUrl(links.link)
                    prefManager.saveBoolean(IS_FIRST_RUN, false)
                } else loadUrl(links.home)
            }
        }
    }

    private fun loadUrl(url: String) {
        if (!isNetworkConnected(requireContext())) {
            Toast.makeText(requireContext(), R.string.no_internet, Toast.LENGTH_LONG).show()
            return
        }

        if (URLUtil.isValidUrl(url))
            binding.webView.loadUrl(url)
    }

    private fun onGoBack() {
        if (binding.webView.canGoBack())
            binding.webView.goBack()
        else requireActivity().finish()
    }

    private fun onGoForward() {
        if (binding.webView.canGoForward())
            binding.webView.goForward()
    }

    private fun refreshPage() {
        binding.webView.url?.let { loadUrl(it) }
    }

    private fun prepareWebView() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.progressBar.isVisible = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBar.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }
        }

        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                binding.progressBar.progress = progress
            }
        }
    }
}