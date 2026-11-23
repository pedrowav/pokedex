package com.pedroewaquim.pokedex.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pedroewaquim.pokedex.core.action.GetPokemonList

@Suppress("UNCHECKED_CAST")
class PokemonListViewModelFactory(
    private val getPokemonListAction: GetPokemonList
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PokemonListViewModel(getPokemonListAction) as T
    }
}
