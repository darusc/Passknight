package com.example.passknight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.biometric.BiometricManager.Authenticators
import com.example.passknight.services.BiometricsProvider
import com.example.passknight.services.Firestore
import com.google.android.material.button.MaterialButton
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.firestore
import com.google.firebase.initialize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var biometricsProvider: BiometricsProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<MaterialButton>(R.id.unlock_btn).setOnClickListener {
            biometricsProvider.prompt()
            startActivity(Intent(this, AppActivity::class.java))
        }

        biometricsProvider = BiometricsProvider(
            applicationContext,
            this,
            "Enter your lock screen lock to access",
            "Please unlock to proceed",
            Authenticators.DEVICE_CREDENTIAL or Authenticators.BIOMETRIC_WEAK
        ) {
            startActivity(Intent(this, AppActivity::class.java))
        }

        biometricsProvider.prompt()
    }
}