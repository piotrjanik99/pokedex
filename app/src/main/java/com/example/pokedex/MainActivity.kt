package com.example.pokedex

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokedex.repository.Pokemon
import com.example.pokedex.ui.theme.PokedexTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData()
        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    //MainView()
                    ShowPokemonList(viewModel = viewModel, onClick = {name -> navigateToDetailsActivity(name)})
                }
            }
        }
    }

    fun navigateToDetailsActivity(name: String) {
        val detailsIntent = Intent(this, DetailsActivity::class.java)
        detailsIntent.putExtra("CUSTOM_NAME", name)
        startActivity(detailsIntent);
    }
}

@Composable
fun ShowPokemonList(viewModel: MainViewModel, onClick: (String) -> Unit) {
    val uiState by viewModel.immutablePokemonData.observeAsState(UiState())

    when {
        uiState.isLoading -> {
            LoadingView()
        }
        uiState.error != null -> {
            ErrorView()
        }
        uiState.data != null -> {
            uiState.data?.let { MyListView(pokemon = it, onClick = { name -> onClick.invoke(name) }) }
        }
    }
}

@Composable
fun ErrorView() {
    Text(text = "ERROR", fontSize=70.sp, fontStyle = FontStyle.Italic, color = Color.Red)
}

@Composable
fun LoadingView() {
    CircularProgressIndicator()
}

@Composable
fun MyListView(pokemon: List<Pokemon>, onClick: (String) -> Unit){
    LazyColumn{
        items(pokemon) {pokemon ->
            MainView(name = pokemon.name, onClick = { name -> onClick.invoke(name) })
        }
    }

}
@Composable
fun MainView(name: String, onClick: (String) -> Unit) {

    Column(
        modifier = Modifier.clickable { onClick.invoke(name) }
    ) {
        Text(text = name, color= Color.Black)
        Text(text = "Id", fontSize=10.sp, fontStyle = FontStyle.Normal, color = Color.Red)
        AsyncImage(
            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png",
            contentDescription = "pokemon",
            placeholder = painterResource(R.drawable.notfound)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokedexTheme {
        MainView("a", {})
    }
}