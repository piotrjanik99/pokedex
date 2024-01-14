package com.example.pokedex

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity

class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = intent.getStringExtra("CUSTOM_NAME")

        Toast.makeText(this, "szczegóły $name", Toast.LENGTH_SHORT).show()
    }
}