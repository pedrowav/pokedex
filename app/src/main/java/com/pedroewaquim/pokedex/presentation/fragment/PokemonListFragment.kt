package com.pedroewaquim.pokedex.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pedroewaquim.pokedex.R
import com.pedroewaquim.pokedex.infrastructure.factory.ViewModelsFactory
import com.pedroewaquim.pokedex.presentation.fragment.adapter.PokemonAdapter
import kotlinx.coroutines.launch

class PokemonListFragment : Fragment() {
    private var columnCount = 1
    private lateinit var pokemonAdapter: PokemonAdapter

    private val viewModel: PokemonListViewModel by viewModels {
        ViewModelsFactory.pokemonListViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getFragmentArguments()
        createViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recyclerView =
            inflater.inflate(R.layout.fragment_pokemon_list, container, false) as RecyclerView

        setRecyclerViewConfig(recyclerView)
        viewModel.getPokemonList()
        return recyclerView
    }

    private fun getFragmentArguments() {
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    private fun createViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                onChangePokemon(viewModel)
            }
        }
    }

    private suspend fun onChangePokemon(viewModel: PokemonListViewModel) {
        viewModel.pokedexState.collect { pokemonList ->
            pokemonAdapter.submitList(pokemonList)
        }
    }

    private fun PokemonListFragment.setRecyclerViewConfig(
        recyclerView: RecyclerView
    ) {
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            pokemonAdapter = PokemonAdapter(emptyList())
            adapter = pokemonAdapter
            setHasFixedSize(true)
        }
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            PokemonListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}