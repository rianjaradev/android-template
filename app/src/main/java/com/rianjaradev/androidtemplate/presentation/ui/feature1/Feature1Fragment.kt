package com.rianjaradev.androidtemplate.presentation.ui.feature1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rianjaradev.androidtemplate.R
import com.rianjaradev.androidtemplate.databinding.FeatureFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Feature1Fragment : Fragment() {

    private var _binding: FeatureFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: Feauture1Viewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FeatureFragmentBinding.inflate(inflater, container, false).let {
        _binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.featureBtnLaunch.setOnClickListener {
            lifecycleScope.launch {
                viewModel.uIAction.send(Feature1Action.Fetch)
            }
        }
        binding.featureBtnCancelFetch.setOnClickListener {
            lifecycleScope.launch {
                viewModel.uIAction.send(Feature1Action.CancelFetch)
            }
        }
        setupListener()
    }

    private fun setupListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    when (state) {
                        is Feature1UIState.ShowResponse -> {
                            binding.featureLblResponse.text = state.feature1Entity.toString()
                        }
                        Feature1UIState.CleanResponse -> {
                            binding.featureLblResponse.text = getString(R.string.coroutine_canceled)
                        }
                    }
                }
            }
        }
    }
}