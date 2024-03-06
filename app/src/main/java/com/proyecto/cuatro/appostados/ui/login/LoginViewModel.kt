package com.proyecto.cuatro.appostados.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Patterns
import com.proyecto.cuatro.appostados.data.services.LoginService
import com.proyecto.cuatro.appostados.data.services.Result
import com.proyecto.cuatro.appostados.R
import android.content.Context


class LoginViewModel(private val applicationContext: Context, private val loginService: LoginService) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginService.login(username, password) // AQUI EMPIEZA LA PETICION

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = LoginActivity.LoginErrorType.INVALID_CREDENTIALS)
        }
    }

    fun logOut(){
        loginService.logout()
    }

    fun loginDataChanged(username: String, password: String) {
        var usernameError: Int? = null
        var passwordError: Int? = null

        if (!isUserNameValid(username)) {
            usernameError = R.string.error_invalid_username
        }
        if (!isPasswordValid(password)) {
            passwordError = R.string.error_invalid_password
        }

        _loginForm.value = LoginFormState(
            isDataValid = usernameError == null && passwordError == null,
            usernameError = usernameError,
            passwordError = passwordError
        )
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return true;
    }
}
