package com.example.whiskerbuds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.whiskerbuds.navigation.NavGraph
import com.example.whiskerbuds.ui.MainScreen
import com.example.whiskerbuds.ui.theme.WhiskerBudsTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WhiskerBudsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    var isUserLoggedIn by remember { mutableStateOf(false) }

                    // Firebase authentication check in LaunchedEffect
                    LaunchedEffect(Unit) {
                        isUserLoggedIn = FirebaseAuth.getInstance().currentUser != null
                    }

                    NavGraph(navController, isUserLoggedIn)
                }
            }
        }
    }
}
