package com.pedroewaquim.pokedex.infrastructure.placeholder

import com.pedroewaquim.pokedex.core.domain.Pokemon

object PlaceholderContent {
    val ITEMS: MutableList<Pokemon> = ArrayList()
    private val ITEM_MAP: MutableMap<Int, Pokemon> = HashMap()

    private const val COUNT = 25

    init {
        for (i in 1..COUNT) {
            addItem(createPokemonItem())
        }
    }

    private fun addItem(item: Pokemon) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    private fun createPokemonItem(): Pokemon {
        return Pokemon(
            25,
            "Pikachu",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"
        )
    }
}