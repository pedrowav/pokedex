package com.pedroewaquim.pokedex.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pedroewaquim.pokedex.R
import com.pedroewaquim.pokedex.core.domain.Pokemon
import com.pedroewaquim.pokedex.presentation.theme.PokedexTheme

/**
 * Composable item representing a single Pokemon entry.
 *
 * Replaces the legacy pokemon_item_layout.xml:
 * - 150dp x 150dp Pokemon image with a Pokeball placeholder
 * - Top-left aligned Pokemon number (30sp)
 * - Horizontally centered Pokemon name (30sp) filling the remaining width
 */
@Composable
fun PokemonItem(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.text_margin)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(150.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.imageUrl)
                    .crossfade(enable = true)
                    .build(),
                placeholder = painterResource(R.drawable.pokeball),
                error = painterResource(R.drawable.pokeball),
                contentDescription = stringResource(R.string.pokemon_image),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
            )
            Text(
                text = pokemon.id,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 30.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.TopStart),
            )
        }

        Text(
            text = pokemon.name,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .padding(start = dimensionResource(R.dimen.text_margin)),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonItemLightPreview() {
    PokedexTheme {
        PokemonItem(
            pokemon = Pokemon(
                id = "25",
                name = "Pikachu",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
            ),
        )
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PokemonItemDarkPreview() {
    PokedexTheme {
        PokemonItem(
            pokemon = Pokemon(
                id = "25",
                name = "Pikachu",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
            ),
        )
    }
}
