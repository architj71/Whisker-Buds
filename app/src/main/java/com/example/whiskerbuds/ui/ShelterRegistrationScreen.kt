package com.example.whiskerbuds.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.whiskerbuds.model.ShelterDataClass
import com.example.whiskerbuds.network.RetrofitInstancePostShelter
import com.example.whiskerbuds.repository.ShelterRepository
import com.example.whiskerbuds.viewmodel.ShelterViewModel
import com.example.whiskerbuds.viewmodel.ShelterViewModelFactory

@Composable
fun ShelterRegistrationScreen(
    navController: NavController,
    viewModel: ShelterViewModel = viewModel(
        factory = ShelterViewModelFactory(ShelterRepository(RetrofitInstancePostShelter.api))
    )
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val shelterName = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val shelterType = remember { mutableStateOf("") }
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val showDogs = remember { mutableStateOf(false) }
    val showCats = remember { mutableStateOf(false) }
    val showCows = remember { mutableStateOf(false) }
    val showBirds = remember { mutableStateOf(false) }

    val capacityDogs = remember { mutableStateOf("") }
    val capacityCats = remember { mutableStateOf("") }
    val capacityCows = remember { mutableStateOf("") }
    val capacityBirds = remember { mutableStateOf("") }

    val currentDogs = remember { mutableStateOf("") }
    val currentCats = remember { mutableStateOf("") }
    val currentCows = remember { mutableStateOf("") }
    val currentBirds = remember { mutableStateOf("") }

    val showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Shelter Registration",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(vertical = 11.dp)
        )

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = shelterName.value,
                    onValueChange = { shelterName.value = it },
                    label = { Text("Shelter Name") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )

                OutlinedTextField(
                    value = address.value,
                    onValueChange = { address.value = it },
                    label = { Text("Shelter Address") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )

                ShelterTypeDropdown(selectedType = shelterType)

                ImagePicker(imageUri)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Animal Types",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                ToggleAnimalSection("Dogs", showDogs)
                ToggleAnimalSection("Cats", showCats)
                ToggleAnimalSection("Cows", showCows)
                ToggleAnimalSection("Birds", showBirds)

                Spacer(modifier = Modifier.height(12.dp))

                if (showDogs.value) AnimalCapacityInput("Dogs", capacityDogs, currentDogs)
                if (showCats.value) AnimalCapacityInput("Cats", capacityCats, currentCats)
                if (showCows.value) AnimalCapacityInput("Cows", capacityCows, currentCows)
                if (showBirds.value) AnimalCapacityInput("Birds", capacityBirds, currentBirds)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (shelterName.value.isEmpty()) {
                    Toast.makeText(context, "Please Enter Shelter Name", Toast.LENGTH_SHORT).show()
                } else if (address.value.isEmpty()) {
                    Toast.makeText(context, "Please Enter Shelter Address", Toast.LENGTH_SHORT).show()
                } else if (!showDogs.value && !showCats.value && !showCows.value && !showBirds.value) {
                    Toast.makeText(context, "Please select at least one pet category", Toast.LENGTH_SHORT).show()
                } else {
                    showDialog.value = true
                }
            },
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Register Shelter", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Terms and Conditions") },
            text = { Text("Please accept the terms and conditions before proceeding.") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    val data = ShelterDataClass(
                        name = shelterName.value,
                        address = address.value,
                        totalCapacity = listOf(
                            capacityDogs.value.toIntOrNull() ?: 0,
                            capacityCats.value.toIntOrNull() ?: 0,
                            capacityCows.value.toIntOrNull() ?: 0,
                            capacityBirds.value.toIntOrNull() ?: 0
                        ),
                        currentStrength = listOf(
                            currentDogs.value.toIntOrNull() ?: 0,
                            currentCats.value.toIntOrNull() ?: 0,
                            currentCows.value.toIntOrNull() ?: 0,
                            currentBirds.value.toIntOrNull() ?: 0
                        ),
                        donationReceived = 0
                    )
                    viewModel.registerShelter(data)
                }) {
                    Text("Accept")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ShelterTypeDropdown(selectedType: MutableState<String>) {
    val options = listOf("Dogs", "Cats", "Birds", "Cows", "Multiple")
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedType.value,
            onValueChange = {},
            label = { Text("Select Shelter Type") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.clickable { expanded = true }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedType.value = option
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ImagePicker(imageUri: MutableState<Uri?>) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri.value = uri
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        // Image picker box
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier
                .width(360.dp)
                .height(60.dp)
                .clickable { launcher.launch("image/*") }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                imageUri.value?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "Shelter Image",
                        modifier = Modifier.size(110.dp)
                    )
                } ?: Text("Shelter Images", color = Color.DarkGray)
            }
        }
    }
}



@Composable
fun ToggleAnimalSection(label: String, state: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { state.value = !state.value }
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = state.value, onCheckedChange = { state.value = it })
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun AnimalCapacityInput(animalType: String, capacity: MutableState<String>, currentStrength: MutableState<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = capacity.value,
            onValueChange = { capacity.value = it },
            label = { Text("Total capacity for $animalType") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = currentStrength.value,
            onValueChange = { currentStrength.value = it },
            label = { Text("Current strength of $animalType") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
    }
}
