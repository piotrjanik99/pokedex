package com.example.pokedex

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.example.pokedex.repository.PokemonDetails
import com.example.pokedex.ui.theme.PokedexTheme

class DetailsActivity : ComponentActivity() {
    private val viewModel: DetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("CUSTOM_NAME")
        if (name != null) {
            viewModel.getDetailsData(name)
        }
        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    //MainView()
                    ShowDetails(viewModel)
                }
            }
        }

        Toast.makeText(this, "szczegóły $name", Toast.LENGTH_SHORT).show()
    }
}
@Composable
fun ShowDetails(viewModel: DetailsViewModel) {
    val uiState by viewModel.immutablePokemonDetailsData.observeAsState(UiState())

    when {
        uiState.isLoading -> {
            DetailsLoadingView()
        }
        uiState.error != null -> {
            DetailsErrorView()
        }
        uiState.data != null -> {
            Log.d("DetailsViewModel", "Details")
            uiState.data?.let {DetailsView(pokemonDetails = it)}
        }
    }
}

@Composable
fun DetailsErrorView() {
    Text(text = "ERROR", fontSize=70.sp, fontStyle = FontStyle.Italic, color = Color.Red)
}

@Composable
fun DetailsLoadingView() {
    CircularProgressIndicator()
}

@Composable
fun DetailsView(pokemonDetails: PokemonDetails){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Name: "+pokemonDetails.name.uppercase(), fontSize= 30.sp, color= Color.Black)
        Text(text = "Id: "+pokemonDetails.id.toString(), fontSize=30.sp, fontStyle = FontStyle.Normal, color = Color.Red)
        Text(text = "Weight: "+pokemonDetails.weight.toString(), fontSize=30.sp, fontStyle = FontStyle.Normal, color = Color.Red)
        Text(text = "Order: "+pokemonDetails.order.toString(), fontSize=30.sp, fontStyle = FontStyle.Normal, color = Color.Red)
    }

}