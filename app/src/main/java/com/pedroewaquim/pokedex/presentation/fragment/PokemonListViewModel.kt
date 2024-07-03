package com.pedroewaquim.pokedex.presentation.fragment

import androidx.lifecycle.ViewModel
import com.pedroewaquim.pokedex.core.action.GetPokemon
import com.pedroewaquim.pokedex.core.callback.PokemonListCallback
import com.pedroewaquim.pokedex.core.domain.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PokemonListViewModel(
    val getPokemonAction: GetPokemon
) : ViewModel() {

    private val _pokedexState = MutableStateFlow(emptyList<Pokemon>())
    val pokedexState: StateFlow<List<Pokemon>> = _pokedexState.asStateFlow()

    fun getPokemonList()  {
        getPokemonAction(object : PokemonListCallback {
            override fun invoke(values: List<Pokemon>) {
                _pokedexState.update { values }
            }
        })
    }
}