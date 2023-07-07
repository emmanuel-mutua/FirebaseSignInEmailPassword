package com.example.signinemailandpassword

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseAuthModule {
    @Provides
    fun provideAuthRepo(): SignInSignUpRepo = SignInSignUpRepoImpl(
        auth = Firebase.auth
    )
}