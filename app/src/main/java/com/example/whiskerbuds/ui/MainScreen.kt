package com.example.whiskerbuds.ui

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.whiskerbuds.R
import com.example.whiskerbuds.navigation.NavGraph
import com.example.whiskerbuds.navigation.Screen
import com.example.whiskerbuds.repository.PetRepository
import com.example.whiskerbuds.viewmodel.PetViewModel
import com.example.whiskerbuds.viewmodel.PetViewModelFactory

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(
                navController = navController,
                startDestination = Screen.AdoptPet.route
            ) {
                composable(Screen.AdoptPet.route) {
                    val context = LocalContext.current
                    val repository = remember { PetRepository() }
                    val factory = remember { PetViewModelFactory(repository, context.applicationContext as Application) }
                    val petViewModel: PetViewModel = viewModel(factory = factory)

                    AdoptPetScreen(viewModel = petViewModel, onPetClick = { /* handle click */ })
                }

                composable(Screen.DonateToShelters.route) {
                    DonateToShelterScreen(navController)
                }

                composable(Screen.RegisterYourPet.route) {
                    RegisterPetScreen(navController)
                }

                composable(Screen.ContactVet.route) {
                    ContactVetScreen()
                }

                composable(Screen.ShelterRegistration.route) {
                    ShelterRegistrationScreen(navController)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Adopt a Pet", R.drawable.dognav, Screen.AdoptPet.route),
        BottomNavItem("Shelters", R.drawable.heart, Screen.DonateToShelters.route),
        BottomNavItem("Register a Pet", R.drawable.register, Screen.RegisterYourPet.route),
        BottomNavItem("Contact Vet", R.drawable.stethoscope, Screen.ContactVet.route)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(50))
            .background(color = Color(0xFFCDC8E8), shape = RoundedCornerShape(50))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = currentRoute == item.route

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            if (!selected) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                ) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    if (selected) {
                        Text(
                            text = item.label,
                            color = Color.Black,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}


data class BottomNavItem(val label: String, val icon: Int, val route: String)
