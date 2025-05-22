package com.example.whiskerbuds.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.whiskerbuds.R
import com.example.whiskerbuds.model.ShelterDataClass
import com.example.whiskerbuds.network.RetrofitInstancePostShelter
import com.example.whiskerbuds.repository.ShelterRepository
import com.example.whiskerbuds.viewmodel.ShelterViewModel
import com.example.whiskerbuds.viewmodel.ShelterViewModelFactory

@Composable
fun DonateToShelterScreen(
    navController: NavController,
    shelterViewModel: ShelterViewModel = viewModel(
        factory = ShelterViewModelFactory(ShelterRepository(RetrofitInstancePostShelter.api))
    )
) {
    val shelters by shelterViewModel.shelterList.collectAsState()
    var selectedShelter by remember { mutableStateOf<ShelterDataClass?>(null) }

    LaunchedEffect(Unit) {
        shelterViewModel.getShelters()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Background Image
        Spacer(modifier = Modifier.height(15.dp))
        Image(
            painter = painterResource(id = R.drawable.pets),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(vertical = 20.dp)
                .height(600.dp)
            //    .alpha(0.9f)
        )



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Donate to a Shelter",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(shelters) { shelter ->
                    ShelterItem(shelter) { selectedShelter = it }
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("shelter_registration") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .width(140.dp)
                .padding(16.dp)
        ) {
            Text("Register")
        }
    }

    selectedShelter?.let { shelter ->
        DonateDialog(
            shelterName = shelter.name,
            onDismiss = { selectedShelter = null },
            onConfirm = { amount ->
                shelter.shelterID?.let {
                    shelterViewModel.donateToShelter(it, amount)
                } ?: println("Error: Shelter ID is null!")
            }
        )
    }
}

@Composable
fun ShelterItem(shelter: ShelterDataClass, onClick: (ShelterDataClass) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(shelter) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = shelter.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Location: ${shelter.address}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun DonateDialog(
    shelterName: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var amount by remember { mutableStateOf(TextFieldValue("")) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Donate to $shelterName", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Enter Amount") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) { Text("Cancel") }
                    TextButton(onClick = { onConfirm(amount.text) }) { Text("Donate") }
                }
            }
        }
    }
}
