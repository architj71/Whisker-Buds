package com.example.whiskerbuds.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whiskerbuds.viewmodel.PaymentViewModel

@Composable
fun PaymentScreen(shelterID: String, shelterName: String, amount: String, viewModel: PaymentViewModel = viewModel()) {
    val context = LocalContext.current
    val paymentStatus by viewModel.paymentStatus.collectAsState()

    val googlePayPackage = "com.google.android.apps.nbu.paisa.user"
    val upiUri = getUpiPaymentUri(shelterName, "7014648589@paytm", "Adopt Your Pet", amount)

    val launcher = rememberLauncherForActivityResult(StartActivityForResult()) { result ->
        val status = result.data?.getStringExtra("Status")?.lowercase()
        if (result.resultCode == android.app.Activity.RESULT_OK && status == "success") {
            Toast.makeText(context, "Transaction Successful", Toast.LENGTH_SHORT).show()
            viewModel.processDonation(shelterID, amount)
        } else {
            Toast.makeText(context, "Transaction Failed", Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Button (
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = upiUri
                    `package` = googlePayPackage
                }
                if (isAppInstalled(context, googlePayPackage)) {
                    launcher.launch(intent)
                } else {
                    Toast.makeText(context, "Please Install GPay", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Pay ₹$amount with Google Pay")
        }

        paymentStatus?.let {
            Text(it, modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.bodyLarge)
        }
    }
}

fun getUpiPaymentUri(name: String, upiId: String, transactionNote: String, amount: String): Uri {
    return Uri.Builder()
        .scheme("upi")
        .authority("pay")
        .appendQueryParameter("pa", upiId)
        .appendQueryParameter("pn", name)
        .appendQueryParameter("tn", transactionNote)
        .appendQueryParameter("am", amount)
        .appendQueryParameter("cu", "INR")
        .build()
}

fun isAppInstalled(context: android.content.Context, packageName: String): Boolean {
    return try {
        context.packageManager.getApplicationInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}
