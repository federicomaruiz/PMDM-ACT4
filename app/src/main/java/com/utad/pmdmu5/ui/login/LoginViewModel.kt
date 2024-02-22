package com.utad.pmdmu5.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utad.pmdmu5.data.db.firebase.AuthManager
import com.utad.pmdmu5.data.db.firebase.AuthRes
import com.utad.pmdmu5.data.db.firebase.model.User
import io.paperdb.Paper
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

    fun doLogin(email: String, password: String) {
        // Obtener el usuario de la base de datos con el email proporcionado
        val user = Paper.book("users").read<User>(email)
        // Verificar si el usuario existe y si la contraseña coincide
        val isLoginSuccessful = user != null && user.passwd == password
        if (isLoginSuccessful) {
            // Actualizar el estado de isLoggedIn en el objeto User
            user!!.isLoggedIn = true
            // Eliminar el usuario existente (si existe) con el correo electrónico actual
            Paper.book("users").delete(email)
            // Guardar el nuevo usuario con el correo electrónico actualizado
            Paper.book("users").write(email, user)
        }

    }
}