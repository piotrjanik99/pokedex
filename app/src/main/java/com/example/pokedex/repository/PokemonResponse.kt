package com.example.pokedex.repository

data class PokemonResponse (
    val results: List<Pokemon>
)

data class Pokemon(
    val name: String,
)