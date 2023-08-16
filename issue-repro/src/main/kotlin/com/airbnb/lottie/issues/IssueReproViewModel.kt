package com.airbnb.lottie.issues

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.LottieCompositionFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IssueReproViewModel: ViewModel() {

    private val cacheKey = "cache_key"

    private val _resultFromSync = MutableLiveData("Click Sync Load")
    val resultFromSync: LiveData<String> = _resultFromSync
    private val _resultFromAsync = MutableLiveData("Click Async Load")
    val resultFromAsync: LiveData<String> = _resultFromAsync

    fun getAndCompareLottieSync(context: Context) {
        _resultFromSync.postValue("Loading...")
        viewModelScope.launch(Dispatchers.IO) {
            clearLottieCache(context)
            val firstLottie = LottieCompositionFactory.fromRawResSync(context, R.raw.heart, cacheKey)
            val shouldBeFromCache = LottieCompositionFactory.fromRawResSync(context, R.raw.heart, cacheKey)
            _resultFromSync.postValue("Lottie fromRawResSync is same ref: ${firstLottie === shouldBeFromCache}")
        }
    }

    fun getAndCompareLottieAsync(context: Context) {
        _resultFromAsync.postValue("Loading...")
        clearLottieCache(context)
        LottieCompositionFactory.fromRawRes(context, R.raw.heart, cacheKey).addListener {firstLottie ->
            LottieCompositionFactory.fromRawRes(context, R.raw.heart, cacheKey).addListener { shouldBeFromCache ->
                _resultFromAsync.postValue("Lottie fromRawRes is same ref: ${firstLottie === shouldBeFromCache}")
            }
        }
    }

    private fun clearLottieCache(context: Context) {
        LottieCompositionFactory.clearCache(context)
    }
}
