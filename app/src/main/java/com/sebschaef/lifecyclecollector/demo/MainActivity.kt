package com.sebschaef.lifecyclecollector.demo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sebschaef.lifecyclecollector.collectWhenStarted
import com.sebschaef.lifecyclecollector.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeBasicFlowWhenStarted()
        observeStateFlowWhenStarted()
        observeContinuousBasicFlowWhenStarted()
    }

    /**
     * Turns the [viewModel.secondsSinceObservedFlow] into a hot flow only when the Activity
     * is in [Lifecycle.State.STARTED]
     */
    private fun observeBasicFlowWhenStarted() {
        viewModel.secondsSinceObservedFlow.collectWhenStarted(owner = this) {
            binding.whenStartedBasicFlowValue.text = it.toString()
        }
    }

    /**
     * Collects items of the [viewModel.secondsSinceFirstObservation] only when the Activity
     * is in [Lifecycle.State.STARTED]
     */
    private fun observeStateFlowWhenStarted() {
        viewModel.secondsSinceFirstObservation.collectWhenStarted(owner = this) {
            binding.whenStartedStateFlowValue.text = it.toString()
        }
    }

    /**
     * Turns the [viewModel.secondsOfTotalObservatio] into a hot flow only when the Activity
     * is in [Lifecycle.State.STARTED]
     */
    private fun observeContinuousBasicFlowWhenStarted() {
        viewModel.secondsOfTotalObservation.collectWhenStarted(owner = this) {
            binding.whenStartedBasicContinuousFlowValue.text = it.toString()
        }
    }
}