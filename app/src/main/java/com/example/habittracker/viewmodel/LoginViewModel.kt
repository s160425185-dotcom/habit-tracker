package com.example.habittracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _loginSuccess = MutableLiveData(false)
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    fun login(username: String, password: String) {
        if (username == VALID_USERNAME && password == VALID_PASSWORD) {
            _errorMessage.value = null
            _loginSuccess.value = true
        } else {
            _errorMessage.value = "Invalid username or password"
        }
    }

    fun onLoginHandled() {
        _loginSuccess.value = false
    }

    companion object {
        // Hardcode sesuai ketentuan tugas
        private const val VALID_USERNAME = "student"
        private const val VALID_PASSWORD = "123"
    }
}
