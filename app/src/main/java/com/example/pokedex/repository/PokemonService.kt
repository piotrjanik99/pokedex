package com.example.pokedex.repository

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("/api/v2/pokemon")
    suspend fun getPokemonResponse(): Response<PokemonResponse>

    @GET("/api/v2/pokemon/{name}")
    suspend fun getPokemonDetails(@Path("name") name: String): Response<PokemonDetails>

    companion object {
        private const val POKEMON_URL = "https://pokeapi.co/"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(POKEMON_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val pokemonService: PokemonService by lazy {
            retrofit.create(PokemonService::class.java)
        }
    }

}