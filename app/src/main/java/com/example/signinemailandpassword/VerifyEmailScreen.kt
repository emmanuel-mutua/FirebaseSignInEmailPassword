import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.signinemailandpassword.MainViewModel
import com.example.signinemailandpassword.VerifiedState

@Composable
fun VerifyEmailScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val isEmailVerified by viewModel.isEmailVerified.collectAsState()

    LaunchedEffect(isEmailVerified) {
        if (isEmailVerified) {
            navController.navigate("Profile")
        }
    }

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
            navController.navigate("Sign_In")
        }) {
            Text(text = "Cancel")
        }
        TextButton(onClick = {
            if (isEmailVerified) {
                navController.navigate("Profile")
            } else {
                return@TextButton
            }
        }) {
            Text(text = "Already Verified?")
        }
    }
}
