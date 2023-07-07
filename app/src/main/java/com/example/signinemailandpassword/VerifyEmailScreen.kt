package com.example.signinemailandpassword

import androidx.navigation.NavController

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun VerifyEmailScreen(
    viewModel: MainViewModel = hiltViewModel(),
    state: VerifiedState,
    navController: NavController
) {
    var isVerified by remember {
        mutableStateOf(false)
    }
    isVerified = state.isVerified
    if(isVerified){
        NavigateToProfileScreen(navController = navController)
    }else{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Verify Email, Open Gmail")
            TextButton(onClick = {
                viewModel.sendEmailVerification()
            }) {
                Text(text = "Resend link")
            }
            TextButton(onClick = {
                navController.navigate("Sign_Up")
            }) {
                Text(text = "Cancel")
            }
            TextButton(onClick = {
                navController.navigate("Profile")
            }) {
                Text(text = "Already Verified?")
            }

        }
    }
    }