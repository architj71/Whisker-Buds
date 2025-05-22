package com.example.whiskerbuds.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.whiskerbuds.repository.PetRepository
import com.example.whiskerbuds.ui.ContactVetScreen
import com.example.whiskerbuds.ui.*
import com.example.whiskerbuds.viewmodel.ContactVetViewModel
import com.example.whiskerbuds.viewmodel.PetViewModel
import com.example.whiskerbuds.viewmodel.PetViewModelFactory
import com.example.whiskerbuds.viewmodel.ShelterViewModel

@Composable
fun NavGraph(navController: NavHostController, isUserLoggedIn: Boolean) {
    val startDestination = if (isUserLoggedIn) {
        Screen.Main.route
    } else {
        Screen.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(navController)
        }

        composable(Screen.Main.route) {
            MainScreen()
        }

        composable(Screen.AdoptPet.route) {
            val context = LocalContext.current
            val repository = remember { PetRepository() }
            val factory = remember { PetViewModelFactory(repository, context.applicationContext as Application) }
            val petViewModel: PetViewModel = viewModel(factory = factory)

            AdoptPetScreen(viewModel = petViewModel, onPetClick = { pet ->
                // handle pet click
            })
        }


        composable(Screen.DonateToShelters.route) {
            DonateToShelterScreen(navController)
        }
        composable(Screen.ShelterRegistration.route) {
            ShelterRegistrationScreen(navController = navController)
        }

        composable(Screen.RegisterYourPet.route) {
            RegisterPetScreen(navController = navController)
        }

        composable(Screen.ContactVet.route) {
            ContactVetScreen()
        }
    }
}