package com.airbnb.lottie.issues

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.issues.databinding.IssueReproActivityBinding

class IssueReproActivity : AppCompatActivity() {
    val viewModel: IssueReproViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = IssueReproActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.syncButton.setOnClickListener {
            viewModel.getAndCompareLottieSync(this)
        }
        binding.asyncButton.setOnClickListener {
            viewModel.getAndCompareLottieAsync(this)
        }

        viewModel.resultFromSync.observe(this) { text ->
            binding.syncButton.isEnabled = !text.contains("Loading")
            binding.syncTextView.text = text

        }

        viewModel.resultFromAsync.observe(this) { text ->
            binding.asyncButton.isEnabled = !text.contains("Loading")
            binding.asyncTextView.text = text
        }

        // Reproduce any issues here.
    }
}
