package com.example.registration

interface LoginPresenter {
    fun login(email: String, password: String)
}

interface LoginView {
    fun showSuccess()
    fun showError(message: String)
}