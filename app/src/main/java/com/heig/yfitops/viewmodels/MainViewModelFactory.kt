package com.heig.yfitops.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.Context

class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java))
                return MainViewModel(context) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }