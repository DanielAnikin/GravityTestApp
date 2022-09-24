package com.gravity.testapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gravity.testapp.R
import com.gravity.testapp.databinding.FragmentLoadingBinding
import com.gravity.testapp.viewmodel.LinksApiStatus.DONE
import com.gravity.testapp.viewmodel.LinksViewModel
import kotlinx.coroutines.delay

class LoadingFragment : Fragment(R.layout.fragment_loading) {
    private val viewModel: LinksViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = false
    }

    private lateinit var binding: FragmentLoadingBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoadingBinding.bind(view)
        binding.viewModel = viewModel

        viewModel.status.observe(viewLifecycleOwner) { status ->
            lifecycleScope.launchWhenResumed {
                if (status == DONE) {
                    delay(1000) //Long loading imitation
                    findNavController().navigate(LoadingFragmentDirections.actionLoadingFragmentToWebViewFragment())
                }
            }
        }
    }
}