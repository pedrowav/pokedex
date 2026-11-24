package com.pedroewaquim.pokedex.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.pedroewaquim.pokedex.core.domain.Pokemon
import com.pedroewaquim.pokedex.presentation.PokedexState
import com.pedroewaquim.pokedex.presentation.component.PokemonItem
import com.pedroewaquim.pokedex.presentation.theme.PokedexTheme
import com.pedroewaquim.pokedex.presentation.viewmodel.PokemonListViewModel

/**
 * Screen composable that replaces the legacy PokemonListFragment and fragment_pokemon_list.xml.
 */
@Composable
fun PokemonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel(),
) {
    val pokedexState by viewModel.pokedexState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getMorePokemonIfNeeded()
    }

    when (val state = pokedexState) {
        is PokedexState.Success -> {
            PokemonListContent(
                modifier = modifier,
                pokemonList = state.pokemonList,
                onLoadMore = { lastVisibleItemIndex ->
                    viewModel.getMorePokemonIfNeeded(lastVisibleItemIndex)
                },
            )
        }
    }
}

@Composable
fun PokemonListContent(
    modifier: Modifier = Modifier,
    pokemonList: List<Pokemon>,
    onLoadMore: (Int) -> Unit,
    listState: LazyListState = rememberLazyListState(),
) {
    val lastVisibleItemIndex by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
        }
    }

    LaunchedEffect(lastVisibleItemIndex, listState.isScrollInProgress) {
        if ((!listState.isScrollInProgress) && (lastVisibleItemIndex >= 0)) {
            onLoadMore(lastVisibleItemIndex)
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState,
    ) {
        items(
            items = pokemonList,
            key = { pokemon -> pokemon.id },
        ) { pokemon ->
            PokemonItem(pokemon = pokemon)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonListContentLightPreview() {
    PokedexTheme {
        PokemonListContent(
            pokemonList = listOf(
                Pokemon(id = "1", name = "Bulbasaur"),
                Pokemon(id = "4", name = "Charmander"),
                Pokemon(id = "7", name = "Squirtle"),
                Pokemon(id = "25", name = "Pikachu"),
            ),
            onLoadMore = {},
        )
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PokemonListContentDarkPreview() {
    PokedexTheme {
        PokemonListContent(
            pokemonList = listOf(
                Pokemon(id = "1", name = "Bulbasaur"),
                Pokemon(id = "4", name = "Charmander"),
                Pokemon(id = "7", name = "Squirtle"),
                Pokemon(id = "25", name = "Pikachu"),
            ),
            onLoadMore = {},
        )
    }
}
