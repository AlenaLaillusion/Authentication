package com.example.registration

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

interface LoginPresenter {
    fun login(email: String, password: String)
}

interface LoginView {
    fun showSuccess()
    fun showError(message: String)
    fun showError(message: Int)
}

class LoginPresenterImpl: LoginPresenter {

    private var authRepository = AuthRepositoryImpl()
    private var viewState: WeakReference<LoginView>? = null

    fun attachView(view: LoginView) {
        viewState = WeakReference(view)
    }

    override fun login(email: String, password: String) {

        if(!validateEmail(email = email)) {
            viewState?.get()?.showError(R.string.error_email_invalid)
            return
        }

        if(!validatePassword(password = password)) {
            viewState?.get()?.showError(R.string.error_password_invalid)
            return
        }

        //Internal logic
        CoroutineScope(Dispatchers.IO).async {

            val errorMessage = authRepository.login(email = email, password = password).await()

            if (errorMessage.isEmpty()) {
                launch(Dispatchers.Main) {
                    viewState?.get()?.showSuccess()
                }
            } else {
                launch(Dispatchers.Main) {
                    viewState?.get()?.showError(message = errorMessage)
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