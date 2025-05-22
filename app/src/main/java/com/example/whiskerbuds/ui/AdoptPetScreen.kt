package com.example.whiskerbuds.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.whiskerbuds.R
import com.example.whiskerbuds.model.PetsDataClass
import com.example.whiskerbuds.viewmodel.PetViewModel

@Composable
fun AdoptPetScreen(viewModel: PetViewModel, onPetClick: (PetsDataClass) -> Unit) {
    val context = LocalContext.current
    val allPets by viewModel.petList.collectAsState()
    val dogs by viewModel.dogList.collectAsState()
    val cats by viewModel.catList.collectAsState()
    val cows by viewModel.cowList.collectAsState()
    val birds by viewModel.birdList.collectAsState()

    var selectedPetType by remember { mutableStateOf("All") }
    val petTypes = listOf("All", "Dogs", "Cats", "Cows", "Birds")

    val filteredPets = when (selectedPetType) {
        "Dogs" -> dogs
        "Cats" -> cats
        "Cows" -> cows
        "Birds" -> birds
        else -> allPets
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)) // Light cream background
            .padding(horizontal = 16.dp)
    ) {

        // Title + Profile Image Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Make A Friend",
                fontSize = 36.sp,
                color = Color(0xFF2D2D2D),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                letterSpacing = 1.sp,
            )

            Image(
                painter = painterResource(id = R.drawable.pet2), // Use correct drawable
                contentDescription = "Profile",
                modifier = Modifier
                    .size(240.dp)
                    .padding(end = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height((-16).dp))

        // Dropdown
        PetTypeDropdown(selectedPetType, petTypes) { selectedPetType = it }

        Spacer(modifier = Modifier.height(12.dp))

        // Pet List
        if (filteredPets != null) {
            if (filteredPets.isNotEmpty()) {
                LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
                    items(filteredPets) { pet ->
                        PetItem(pet) { onPetClick(pet) }
                    }
                }
            } else {
                Toast.makeText(context, "No pets available", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


@Composable
fun PetTypeDropdown(
    selectedType: String,
    petTypes: List<String>,
    onTypeSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(56.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFFDADFFC), // soft yellow
                contentColor = Color.DarkGray
            )
        ) {
            Text(
                text = selectedType,
                fontSize = 18.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Arrow",
                tint = Color.DarkGray
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color.White) // menu background
        ) {
            petTypes.forEach { type ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = type,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = Color.DarkGray
                        )
                    },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun PetItem(pet: PetsDataClass, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(pet.petImageURL),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = pet.petName, fontSize = 20.sp)
                Text(text = "${pet.petAge} years", fontSize = 16.sp)
                Text(text = pet.petType, fontSize = 16.sp)
            }
        }
    }
}

@Preview
@Composable
fun view() {
    val petTypes = listOf("All", "Dogs", "Cats", "Cows", "Birds")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAECCA)) // Light cream background
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Title + Profile Image Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Make A Friend",
                fontSize = 28.sp,
                color = Color.DarkGray,
                style = MaterialTheme.typography.displayMedium
            )
            Image(
                painter = painterResource(id = R.drawable.pet2), // Use correct drawable
                contentDescription = "Profile",
                modifier = Modifier
                    .size(150.dp)
                    .padding(end = 4.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))

    // Dropdown
    var selectedPetType by remember { mutableStateOf("All") }
    PetTypeDropdown(selectedPetType, petTypes) { selectedPetType = it }
}
