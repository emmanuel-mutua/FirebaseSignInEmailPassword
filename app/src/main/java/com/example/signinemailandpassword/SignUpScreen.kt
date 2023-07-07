package com.example.signinemailandpassword

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: MainViewModel = hiltViewModel(),
    signUp : (email: String, password: String) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        val context = LocalContext.current
        when(val signInResponse = viewModel.signInResponse){
            Response.Loading -> {
               CircularProgressIndicator()
            }
            is Response.Success ->{
                val isSignedUp = signInResponse.data
                if (isSignedUp){
                    viewModel.sendEmailVerification()
                }
            }
            is Response.Failure -> signInResponse.apply {
                LaunchedEffect(e){
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        TextField(value = email, label = { Text(text = "Email") }, onValueChange = { email = it })
        Spacer()
        TextField(
            value = password,
            label = { Text(text = "Password") },
            onValueChange = { password = it })
        Spacer()
        Button(
            modifier = Modifier.height(40.dp),
            onClick = {
            signUp(email, password)
        }) {
                Text(text = "SignUp")
            }
        }
    }