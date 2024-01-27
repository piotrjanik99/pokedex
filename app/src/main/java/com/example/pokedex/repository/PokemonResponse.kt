package com.example.pokedex.repository

data class PokemonResponse (
    val results: List<Pokemon>
)
data class Pokemon(
    val name: String,
)
data class PokemonDetails(
    val name: String,
    val id: Int,
    val weight: Int,
    val order: Int,
)