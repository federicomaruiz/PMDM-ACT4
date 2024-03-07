package com.utad.pmdmu5.ui.registers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utad.pmdmu5.data.db.firebase.AuthManager
import com.utad.pmdmu5.data.db.firebase.AuthRes
import com.utad.pmdmu5.data.db.firebase.model.User
import com.utad.pmdmu5.data.db.paperdb.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


data class CreateUIState(
    val isCreate: Boolean = false,
    val isError: Boolean = false
)

class CreateViewModel : ViewModel() {

    private var _createResponse: MutableLiveData<CreateUIState> = MutableLiveData(CreateUIState())
    val createResponse: LiveData<CreateUIState> get() = _createResponse
    val authManager: AuthManager = AuthManager()
    val userRepository: UserRepository = UserRepository()

    // Creo el usuario para verificar si esta logeado en Paper db
    private fun createUserLoggedPaperDb(email: String, user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val userFormat = user.toString()
            userRepository.saveUser(email,user)
            }
    }

    suspend fun doLoginFireBase(email: String, password: String) {
        val user = User(email, password, false)
        when (authManager.createUserWithEmailAndPassword(email, password)) {
            is AuthRes.Success -> {
                createUserLoggedPaperDb(email, user)
                _createResponse.postValue(CreateUIState(isCreate = true, isError = false))
            }
            is AuthRes.Error -> {
                _createResponse.postValue(CreateUIState(isCreate = false, isError = true))
            }
        }

    }
}
