package com.example.signinemailandpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun SessionManager(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val isUserSignedOut = viewModel.authState.collectAsState().value
    val isEmailVerified = viewModel.isEmailVerified.collectAsState().value
    if (isUserSignedOut) {
        NavigateToSignInScreen(navController)
    } else {
        if (isEmailVerified){
            NavigateToProfileScreen(navController)
        }else{
            NavigateToVerifyEmailScreen(navController)
        }
    }
}
@Composable
fun NavigateToVerifyEmailScreen(navController: NavController)= navController.navigate("Verify_Email"){
    popUpTo(navController.graph.id){
        inclusive = true
    }
}

@Composable
fun NavigateToProfileScreen(navController: NavController) = navController.navigate("Profile"){
    popUpTo(navController.graph.id){
        inclusive = true
    }
}

@Composable
fun NavigateToSignInScreen(navController: NavController) = navController.navigate("Sign_In"){
    popUpTo(navController.graph.id){
        inclusive = true
    }
}