package com.example.signinemailandpassword

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state = viewModel.isEmailVerified.collectAsState().value
    Scaffold {
        NavHost(navController = navController, startDestination = "Sign_In") {
            composable("Sign_In") {
                SignInScreen(
                    signIn = { email, password ->
                        viewModel.signInEmailAndPassword(email, password)
                    },
                    navController = navController
                )
            }
            composable("Sign_Up") {
                SignUpScreen(
                    signUp = { email, password ->
                        viewModel.signUpEmailAndPassword(email, password)
                    },
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable("Profile") {
                ProfileScreen(
                     signOut = {
                        viewModel.signOut()
                    },
                    navController,
                    viewModel
                )
            }
            composable("Verify_Email"){
                VerifyEmailScreen(viewModel,state,navController )
            }
        }
        SessionManager(viewModel, navController)
    }
}

