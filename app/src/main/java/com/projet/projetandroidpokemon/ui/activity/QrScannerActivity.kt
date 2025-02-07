package com.projet.projetandroidpokemon.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class QrScannerActivity : AppCompatActivity() {

    private val scanLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            val scannedEmail = result.contents.substringAfter("email=")
            Log.d("scannedEmailFirst",scannedEmail+"")

            if (scannedEmail.isNotEmpty() && scannedEmail.contains("@")) {
                val intent = Intent().apply {
                    putExtra("scanned_email", scannedEmail)
                }
                setResult(Activity.RESULT_OK, intent)
            } else {
                setResult(Activity.RESULT_CANCELED)
            }
            finish()
        } else {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val options = ScanOptions().apply {
            setPrompt("Scannez un QR Code")
            setBeepEnabled(true)
            setBarcodeImageEnabled(true)
        }
        scanLauncher.launch(options)
    }
}
