package com.example.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registration.extensions.set
import kotlinx.coroutines.*

class LoginFragmentViewModel(
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var authRepository = AuthRepositoryImpl()
    val state = MutableLiveData<LoginState>(LoginState.DefaultState())

    fun login(email: String, password: String) {
        if(!validateEmail(email = email)) {
            state.value = LoginState.ErrorState(message = R.string.error_email_invalid)
            return
        }

        if(!validatePassword(password = password)) {
            state.value = LoginState.ErrorState(message = R.string.error_password_invalid)
            return
        }

        state.set(newValue = LoginState.SendingState())
        CoroutineScope(Dispatchers.IO).async {

            val errorMessage = authRepository.login(email = email, password = password).await()

            if (errorMessage.isEmpty()) {
                launch(Dispatchers.Main) {
                    state.set(newValue = LoginState.LoginSucceededState())
                }
            } else {
                launch(Dispatchers.Main) {
                   state.set(newValue = LoginState.ErrorState(message = errorMessage))
                }
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    private fun validatePassword(password: String): Boolean {
        return password.length > 7
    }
}

