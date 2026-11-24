package com.pedroewaquim.pokedex.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.pedroewaquim.pokedex.core.action.GetPokemonList
import com.pedroewaquim.pokedex.core.callback.PokemonListCallback
import com.pedroewaquim.pokedex.core.domain.Pokemon
import com.pedroewaquim.pokedex.presentation.PokedexState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListAction: GetPokemonList
) : ViewModel(), PokemonListCallback {

    private val _pokedexState = MutableStateFlow<PokedexState>(PokedexState.Success(emptyList()))
    val pokedexState: StateFlow<PokedexState> = _pokedexState.asStateFlow()

    private val pokemonList: MutableList<Pokemon> = mutableListOf()
    private var alreadyRequested = false

    fun getMorePokemonIfNeeded(lastItemPos: Int = -1) {
        if (shouldRequestMorePokemon(lastItemPos)) {
            alreadyRequested = true
            getPokemonListAction(this@PokemonListViewModel, offset = pokemonList.size)
        }
    }

    private fun shouldRequestMorePokemon(lastItemPos: Int): Boolean =
        pokemonList.size - 3 <= lastItemPos + 1 && !alreadyRequested

    override fun success(values: List<Pokemon>) {
        alreadyRequested = false
        pokemonList.addAll(values)
        _pokedexState.value = PokedexState.Success(pokemonList.toList())
    }

    override fun error() {
        alreadyRequested = false
    }
}
