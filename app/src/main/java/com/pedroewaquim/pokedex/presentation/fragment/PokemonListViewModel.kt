package com.pedroewaquim.pokedex.presentation.fragment

import androidx.lifecycle.ViewModel
import com.pedroewaquim.pokedex.core.action.GetPokemonList
import com.pedroewaquim.pokedex.core.callback.PokemonListCallback
import com.pedroewaquim.pokedex.core.domain.Pokemon
import com.pedroewaquim.pokedex.presentation.PokedexState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PokemonListViewModel(
    val getPokemonListAction: GetPokemonList
) : ViewModel(), PokemonListCallback {

    private val _pokedexState = MutableStateFlow<PokedexState>(PokedexState.Success(emptyList()))
    val pokedexState: StateFlow<PokedexState> = _pokedexState.asStateFlow()

    private val _pokemonList: MutableList<Pokemon> = mutableListOf()

    fun getPokemonList() {
        getPokemonListAction(this)
    }

    fun onLastItemView(lastItemPos: Int) {
        if (shouldRequestMorePokemons(lastItemPos)) {
            getPokemonListAction(this@PokemonListViewModel, offset = _pokemonList.size)
        }
    }

    private fun shouldRequestMorePokemons(lastItemPos: Int): Boolean =
        _pokemonList.size - 3 <= lastItemPos + 1

    override fun invoke(values: List<Pokemon>) {
        _pokemonList.addAll(values)
        _pokedexState.value = PokedexState.Success(_pokemonList.toList())
    }
}