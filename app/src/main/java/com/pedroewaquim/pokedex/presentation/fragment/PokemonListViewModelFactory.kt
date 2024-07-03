package com.pedroewaquim.pokedex.presentation.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pedroewaquim.pokedex.core.action.GetPokemon

@Suppress("UNCHECKED_CAST")
class PokemonListViewModelFactory(
    private val getPokemonAction: GetPokemon
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PokemonListViewModel(getPokemonAction) as T
    }
}