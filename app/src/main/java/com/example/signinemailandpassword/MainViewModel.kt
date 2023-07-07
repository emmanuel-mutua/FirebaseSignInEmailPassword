package com.example.signinemailandpassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.signinemailandpassword.IsEmailVerifiedResponse
import com.example.signinemailandpassword.Response
import com.example.signinemailandpassword.SignInResponse
import com.example.signinemailandpassword.SignInSignUpRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val repository : SignInSignUpRepo
): ViewModel() {
    var signInResponse by mutableStateOf<SignInResponse>(Response.Success(false))

    private var _isEmailVerified = MutableStateFlow(VerifiedState(
    repository.currentUser?.isEmailVerified?:false
    ))
    val isEmailVerified = _isEmailVerified.asStateFlow()

    var authState = repository.getAuthState(viewModelScope)


    fun signInEmailAndPassword(email: String, password: String){
        viewModelScope.launch {
            signInResponse = Response.Loading
           signInResponse = repository.signInEmailAndPassword(email, password)
        }
    }
    fun signUpEmailAndPassword(email: String, password: String){
        viewModelScope.launch {
            repository.signUpEmailAndPassword(email, password)
        }
    }

    fun signOut() = repository.signOut()
     fun sendEmailVerification() {
         viewModelScope.launch {
             repository.sendEmailVerification()
         }
    }
}