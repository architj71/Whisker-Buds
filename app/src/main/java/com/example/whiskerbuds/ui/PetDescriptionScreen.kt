package com.example.whiskerbuds.ui

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.whiskerbuds.MainActivity
import com.example.whiskerbuds.model.PetsDataClass
import com.example.whiskerbuds.viewmodel.PetViewModel

@Composable
fun PetDescriptionScreen(pet: PetsDataClass, petViewModel: PetViewModel, onAdoptSuccess: () -> Unit) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Pet Image
        Image(
            painter = rememberAsyncImagePainter(pet.petImageURL),
            contentDescription = "Pet Image",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Pet Details
        Text(text = pet.petName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = "${pet.petAge} years", fontSize = 18.sp)
        Text(text = pet.petType, fontSize = 18.sp)
        Text(text = "Breed: ${pet.petBreed}", fontSize = 16.sp)
        Text(text = "Health: ${pet.petHealth}", fontSize = 16.sp)
        Text(text = "Owner: ${pet.petOwnersName}", fontSize = 16.sp)
        Text(text = pet.adoptionMsg, fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // Gender Icons
        if (pet.gender) {
            Text(text = "Female", fontSize = 16.sp, color = Color.Magenta)
        } else {
            Text(text = "Male", fontSize = 16.sp, color = Color.Blue)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Adopt Button
        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adopt This Buddy")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("ADOPT THIS BUDDY?") },
                confirmButton = {
                    Button(onClick = {
                        pet.petID?.let { petViewModel.deletePet(it) }
                        Toast.makeText(context, "Yayy!! You adopted a buddy!", Toast.LENGTH_LONG).show()
                        context.startActivity(Intent(context, MainActivity::class.java))
                        onAdoptSuccess()
                        showDialog = false
                    }) {
                        Text("CONFIRM")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
