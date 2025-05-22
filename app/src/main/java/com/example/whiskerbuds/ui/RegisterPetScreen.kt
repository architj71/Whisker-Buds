package com.example.whiskerbuds.ui

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.whiskerbuds.model.PetsDataClass
import com.example.whiskerbuds.repository.PetRepository
import com.example.whiskerbuds.viewmodel.PetViewModel
import com.example.whiskerbuds.viewmodel.PetViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPetScreen(
    navController: NavController,
    petViewModel: PetViewModel = viewModel(factory = PetViewModelFactory(PetRepository(), LocalContext.current.applicationContext as Application))
) {
    val context = LocalContext.current

    // Form State
    var petName by remember { mutableStateOf("") }
    var petAge by remember { mutableStateOf("") }
    var petBreed by remember { mutableStateOf("") }
    var petOwnersName by remember { mutableStateOf("") }
    var petOwnerEmail by remember { mutableStateOf("") }
    var petHealth by remember { mutableStateOf("") }
    var petAddress by remember { mutableStateOf("") }
    var adoptionMsg by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf(false) } // false = Male
    var selectedType by remember { mutableStateOf("Dog") }
    var expanded by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val petTypes = listOf("Dog", "Cat", "Rabbit", "Other")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register Your Pet",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                ModernTextField("Pet Name", petName) { petName = it }
                ModernTextField("Pet Age", petAge, KeyboardType.Number) { petAge = it }
                ModernTextField("Pet Breed", petBreed) { petBreed = it }
                ModernTextField("Owner's Name", petOwnersName) { petOwnersName = it }
                ModernTextField("Owner's Email", petOwnerEmail, KeyboardType.Email) { petOwnerEmail = it }
                ModernTextField("Health Condition (1-100)", petHealth, KeyboardType.Number) { petHealth = it }
                ModernTextField("Address", petAddress) { petAddress = it }
                ModernTextField("Adoption Message", adoptionMsg) { adoptionMsg = it }

                Spacer(modifier = Modifier.height(12.dp))

                Text("Pet Type", fontWeight = FontWeight.SemiBold)
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    androidx.compose.material3.TextField(
                        value = selectedType,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedLabelColor = Color(0xFF4CAF50),
                            unfocusedLabelColor = Color(0xFF4CAF50),
                            cursorColor = Color(0xFF4CAF50)
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        petTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    selectedType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }



                Spacer(modifier = Modifier.height(12.dp))

                Text("Gender", fontWeight = FontWeight.SemiBold)
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    listOf("Male" to false, "Female" to true).forEach { (label, value) ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            RadioButton(
                                selected = (selectedGender == value),
                                onClick = { selectedGender = value }
                            )
                            Text(label)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Pet Image", fontWeight = FontWeight.SemiBold)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable {
                            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            (context as? Activity)?.startActivityForResult(intent, 100)
                        },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFCFCFC)),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        imageUri?.let {
                            Image(
                                painter = rememberAsyncImagePainter(it),
                                contentDescription = null,
                                modifier = Modifier.size(120.dp)
                            )
                        } ?: Text("Tap to Upload", color = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (petName.isNotBlank() && petAge.isNotBlank() && petBreed.isNotBlank() &&
                            petOwnersName.isNotBlank() && petOwnerEmail.isNotBlank() &&
                            petHealth.isNotBlank() && petAddress.isNotBlank() && adoptionMsg.isNotBlank()
                        ) {
                            isLoading = true
                            val petData = PetsDataClass(
                                petType = selectedType,
                                petName = petName,
                                petAge = petAge.toIntOrNull() ?: 0,
                                petBreed = petBreed,
                                petOwnersName = petOwnersName,
                                petOwnerEmail = petOwnerEmail,
                                petHealth = petHealth.toIntOrNull() ?: 100,
                                petAdoptionTime = System.currentTimeMillis(),
                                petAddress = petAddress,
                                petImageURL = imageUri?.toString() ?: "",
                                adoptionMsg = adoptionMsg,
                                gender = selectedGender
                            )
                            petViewModel.uploadPet(petData) { success, message ->
                                isLoading = false
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                if (success) navController.popBackStack()
                            }
                        } else {
                            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                        }
                    },
                    enabled = !isLoading,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3D9B41), // Green color
                        contentColor = Color.White // Text color
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                    } else {
                        Text("Submit", fontSize = 17.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ModernTextField(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color(0xFF061302)) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,

            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,

            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Gray,
            errorTextColor = Color.Red,

            focusedLabelColor = Color(0xFF000000),
            unfocusedLabelColor = Color(0xFF000000),
            disabledLabelColor = Color.Gray,
            errorLabelColor = Color.Red,

        )

    )
}
