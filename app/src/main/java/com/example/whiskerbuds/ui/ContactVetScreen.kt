package com.example.whiskerbuds.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.whiskerbuds.R
import com.example.whiskerbuds.model.VetsDataClass
import com.example.whiskerbuds.viewmodel.ContactVetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactVetScreen(vetViewModel: ContactVetViewModel = viewModel()) {
    val vetList by vetViewModel.vetList.collectAsStateWithLifecycle()

    // Load data when the screen is first opened
    LaunchedEffect(true) {
        vetViewModel.loadVets()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Get Help", fontWeight = FontWeight.Bold) })
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(vetList) { vet ->
                VetItem(vet)
            }
        }
    }
}

@Composable
fun VetItem(vet: VetsDataClass) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()

            .padding(horizontal = 18.dp, vertical = 10.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F1D4)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal =  16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Vet Image on the Left
            Image(
                painter = rememberAsyncImagePainter(vet.imgUrl),
                contentDescription = "Vet Image",
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            // Vet Info on the Right
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(text = vet.vetName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = vet.contactEmail, fontSize = 13.sp, modifier = Modifier.padding(top = 2.dp))
                Text(text = vet.address, fontSize = 12.sp, modifier = Modifier.padding(top = 2.dp))
             //   Text(text = "Contact: ${vet.contactNo}", fontSize = 13.sp, modifier = Modifier.padding(top = 2.dp))

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${vet.contactNo}"))
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(36.dp)
                ) {
                    Text(text = "Contact Now", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}
