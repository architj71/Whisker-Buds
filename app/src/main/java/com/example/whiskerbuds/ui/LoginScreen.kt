package com.example.whiskerbuds.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.whiskerbuds.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hey there!",style = MaterialTheme.typography.headlineLarge)
        Text(text = "Sign In", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
        )

        Button(
            onClick = {
                loading = true
                authViewModel.login(email, password) { success, error ->
                    loading = false
                    if (success) {
                        navController.navigate("main_screen") // Navigate to MainScreen
                    } else {
                        Toast.makeText(navController.context, error, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
        ) {
            Text(text = if (loading) "Signing in..." else "Enter")
        }

        TextButton(onClick = { navController.navigate("sign_up") }) {
            Text(text = "New Here? Sign Up")
        }

        TextButton(onClick = {
            if (email.isNotEmpty()) {
                authViewModel.resetPassword(email) { success, message ->
                    Toast.makeText(navController.context, message, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(navController.context, "Enter email", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Forgot Password?", color = MaterialTheme.colorScheme.primary)
        }
    }
}
@Preview
@Composable
fun LoginScreen() {
}