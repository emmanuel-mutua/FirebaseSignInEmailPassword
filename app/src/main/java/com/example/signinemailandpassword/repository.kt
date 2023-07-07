package com.example.signinemailandpassword

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

typealias SignInResponse = Response<Boolean>
typealias SignUpResponse = Response<Boolean>
typealias AuthStateResponse = StateFlow<Boolean>
typealias SendEmailVerificationResponse = Response<Boolean>
typealias IsEmailVerifiedResponse = StateFlow<Boolean>

interface SignInSignUpRepo {
    val currentUser: FirebaseUser?
    suspend fun signInEmailAndPassword(email: String, password: String): SignInResponse
    suspend fun signUpEmailAndPassword(email: String, password: String): SignInResponse
    fun signOut()
    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
    suspend fun sendEmailVerification(): SendEmailVerificationResponse
    suspend fun isEmailVerified(viewModelScope:CoroutineScope) : IsEmailVerifiedResponse

}

class SignInSignUpRepoImpl @Inject constructor(
    private val auth: FirebaseAuth
) : SignInSignUpRepo {
    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun signInEmailAndPassword(
        email: String,
        password: String
    ): SignInResponse {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun signUpEmailAndPassword(
        email: String,
        password: String
    ): SignUpResponse {
        return try {
    auth.createUserWithEmailAndPassword(email, password).await()
            Response.Success(true)
        }catch (e:Exception){
            Response.Failure(e)
        }
    }

    override fun signOut() = auth.signOut()

    override fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse = callbackFlow {
            val authStateListener = AuthStateListener{
                auth -> trySend(auth.currentUser == null)
            }
            auth.addAuthStateListener(authStateListener)
            awaitClose {
                auth.removeAuthStateListener(authStateListener)
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),auth.currentUser == null)

    override suspend fun sendEmailVerification(): SendEmailVerificationResponse {
        return try {
            auth.currentUser?.sendEmailVerification()
            Response.Success(true)
        }catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun isEmailVerified(viewModelScope: CoroutineScope): IsEmailVerifiedResponse = flowOf(
        auth.currentUser?.isEmailVerified?:false
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(),auth.currentUser?.isEmailVerified == false )

}