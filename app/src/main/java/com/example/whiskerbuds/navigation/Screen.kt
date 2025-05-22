package com.example.whiskerbuds.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object AdoptPet : Screen("adopt_pet")
    object DonateToShelters : Screen("donate_to_shelters")
    object ShelterRegistration : Screen("shelter_registration")
    object SignUp : Screen("sign_up")
    object RegisterYourPet : Screen("register_pet")
    object ContactVet : Screen("contact_vet")
    object Login : Screen("login")
}
