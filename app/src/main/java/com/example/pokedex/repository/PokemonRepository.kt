package com.example.pokedex.repository

import retrofit2.Response

class PokemonRepository {
    suspend fun getPokemonResponse(): Response<PokemonResponse> =
        PokemonService.pokemonService.getPokemonResponse()
}