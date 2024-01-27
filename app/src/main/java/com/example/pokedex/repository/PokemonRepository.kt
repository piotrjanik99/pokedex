package com.example.pokedex.repository

import retrofit2.Response

class PokemonRepository {
    suspend fun getPokemonResponse(): Response<PokemonResponse> =
        PokemonService.pokemonService.getPokemonResponse()

    suspend fun getPokemonDetails(name:String): Response<PokemonDetails> =
        PokemonService.pokemonService.getPokemonDetails(name)
}