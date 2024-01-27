package com.example.pokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.repository.PokemonDetails
import com.example.pokedex.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val pokemonRepository = PokemonRepository()

    private val livePokemonDetails = MutableLiveData<UiState<PokemonDetails>>()
    val immutablePokemonDetailsData: LiveData<UiState<PokemonDetails>> = livePokemonDetails

    fun getDetailsData(name: String) {
        livePokemonDetails.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val detailsRequest = pokemonRepository.getPokemonDetails(name)

                Log.d("DetailsViewModel", "request return code:  ${detailsRequest.code()}")

                if (detailsRequest.isSuccessful) {
                    val pokemonDetails = detailsRequest.body()
                    livePokemonDetails.postValue((UiState(data = pokemonDetails)))
                } else {
                    livePokemonDetails.postValue(UiState(error = "${detailsRequest.code()}"))
                    Log.e("DetailsViewModel", "Request failed, ${detailsRequest.errorBody()}")
                }

            } catch (e: Exception) {
                livePokemonDetails.postValue(UiState(error = "Exception $e"))
                Log.e("DetailsViewModel", "Request failed, exception: ", e)

            }
        }
    }
}