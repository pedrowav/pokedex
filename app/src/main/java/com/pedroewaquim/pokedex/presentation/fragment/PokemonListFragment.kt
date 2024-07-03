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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.pedroewaquim.pokedex.R
import com.pedroewaquim.pokedex.infrastructure.factory.ViewModelsFactory
import com.pedroewaquim.pokedex.presentation.PokedexState
import com.pedroewaquim.pokedex.presentation.fragment.adapter.PokemonAdapter
import kotlinx.coroutines.launch

class PokemonListFragment : Fragment() {
    private lateinit var pokemonAdapter: PokemonAdapter

    private val viewModel: PokemonListViewModel by viewModels {
        ViewModelsFactory.pokemonListViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun createViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                onChangePokemon(viewModel)
            }
        }
    }

    private suspend fun onChangePokemon(viewModel: PokemonListViewModel) {
        viewModel.pokedexState.collect { pokedexState ->
            when (pokedexState) {
                is PokedexState.Success -> pokemonAdapter.submitList(pokedexState.pokemonList)
            }
        }
    }

    private fun PokemonListFragment.setRecyclerViewConfig(
        recyclerView: RecyclerView
    ) {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            pokemonAdapter = PokemonAdapter(emptyList())
            adapter = pokemonAdapter
            setHasFixedSize(true)
            addOnScrollListener(getScrollListener(layoutManager as LinearLayoutManager))
        }
    }

    private fun getScrollListener(layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE) {
                    val lastViewPosition = layoutManager.findLastVisibleItemPosition()
                    viewModel.onLastItemView(lastViewPosition)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PokemonListFragment()
    }
}