package com.proyecto.cuatro.appostados.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Patterns
import com.proyecto.cuatro.appostados.data.services.LoginService
import com.proyecto.cuatro.appostados.data.services.Result
import com.proyecto.cuatro.appostados.R
import android.content.Context
import com.proyecto.cuatro.appostados.util.Validator
import org.json.JSONObject
import java.io.IOException


class LoginViewModel(private val applicationContext: Context, private val loginService: LoginService) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, password: String) {
        loginService.login(email, password) { result ->
            // Procesar el resultado dentro del hilo principal
            when (result) {
                is Result.Success -> {
                    val loggedInUser = result.data
                    val loggedInUserView = LoggedInUserView(displayName = loggedInUser.name)
                    _loginResult.postValue(LoginResult(success = loggedInUserView))
                }
                is Result.Error -> {
                    val errorType = when (val errorCode = parseErrorCode(result.exception.message)) {
                        "InvalidPasswordException" -> LoginActivity.LoginErrorType.INVALID_PASSWORD
                        "UserNotFound" -> LoginActivity.LoginErrorType.INVALID_USERNAME
                        else -> {
                            // Manejar cualquier otro tipo de error como un error genérico
                            LoginActivity.LoginErrorType.INVALID_CREDENTIALS
                        }
                    }
                    _loginResult.postValue(LoginResult(error = errorType))
                }
            }
        }
    }


    private fun parseErrorCode(errorMessage: String?): String? {
        return try {
            val json = JSONObject(errorMessage)
            json.optString("errorCode")
        } catch (e: Exception) {
            null
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
        return if (Validator.isValidEmail(username)) {
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
