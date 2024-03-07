package com.utad.pmdmu5.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utad.pmdmu5.data.db.firebase.AuthManager
import com.utad.pmdmu5.data.db.firebase.AuthRes
import com.utad.pmdmu5.data.db.paperdb.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class LoginUIState(
    val isLogin: Boolean = false,
    val isError: Boolean = false
)

class LoginViewModel : ViewModel() {

    private var _loginResponse: MutableLiveData<LoginUIState> = MutableLiveData(LoginUIState())
    val loginResponse: LiveData<LoginUIState> get() = _loginResponse
    val authManager: AuthManager = AuthManager()
    val userRepository: UserRepository = UserRepository()


    fun doLoginFire(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val authRes = authManager.signInWithEmailAndPassword(email, password)) {
                is AuthRes.Success -> {
                    doLogin(email, password)
                    // Inicio sesion
                    _loginResponse.postValue(LoginUIState(isLogin = true, isError = false))
                }

                is AuthRes.Error -> {
                    _loginResponse.postValue(LoginUIState(isLogin = false, isError = true))
                }
            }
        }
    }

    private fun doLogin(email: String, password: String) {
        val user = userRepository.getUserByEmail(email)
        val isLoginSuccessful = user != null && user.passwd == password
        if (isLoginSuccessful) {
            user!!.isLoggedIn = true
            userRepository.deleteUserByEmail(email)
            viewModelScope.launch(Dispatchers.IO) {
                userRepository.saveUser(email,user)
            }
        }
    }
}