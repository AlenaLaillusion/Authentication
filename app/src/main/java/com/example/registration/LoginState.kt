package com.example.registration

sealed class LoginState {
    class DefaultState: LoginState()
    class SendingState: LoginState()
    class LoginSucceededState(): LoginState()
    class ErrorState<T>(val message: T): LoginState()
}