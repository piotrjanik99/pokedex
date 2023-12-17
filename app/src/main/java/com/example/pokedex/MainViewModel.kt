package com.example.pokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.repository.Pokemon
import com.example.pokedex.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val pokemonRepository = PokemonRepository()

    private val livePokemonData = MutableLiveData<UiState<List<Pokemon>>>()
    val immutablePokemonData: LiveData<UiState<List<Pokemon>>> = livePokemonData

    fun getData() {
        livePokemonData.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val request = pokemonRepository.getPokemonResponse()
                Log.d("MainViewModel", "request return code:  ${request.code()}")

                if (request.isSuccessful) {
                    val pokemon = request.body()?.results
                    livePokemonData.postValue(UiState(data = pokemon))
                } else {
                    livePokemonData.postValue(UiState(error = "${request.code()}"))
                    Log.e("MainViewModel", "Request failed, ${request.errorBody()}")
                }
            } catch (e: Exception) {
                livePokemonData.postValue(UiState(error = "Exception $e"))
                Log.e("MainViewModel", "Request failed, exception: ", e)

            }
        }
    }
}