package com.example.registration

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class LoginFragmentViewModelFactory(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        LoginFragmentViewModel::class.java -> LoginFragmentViewModel(
            context = context,
            dispatcher = dispatcher
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}