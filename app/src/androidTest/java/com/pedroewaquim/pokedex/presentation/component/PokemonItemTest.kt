package com.pedroewaquim.pokedex.presentation.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pedroewaquim.pokedex.core.domain.Pokemon
import com.pedroewaquim.pokedex.presentation.theme.PokedexTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokemonItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pokemonItem_displaysPokemonNumberAndName() {
        val testPokemon = Pokemon(
            id = "25",
            name = "Pikachu"
        )

        composeTestRule.setContent {
            PokedexTheme {
                PokemonItem(pokemon = testPokemon)
            }
        }

        composeTestRule.onNodeWithText("25").assertIsDisplayed()
        composeTestRule.onNodeWithText("Pikachu").assertIsDisplayed()
    }
}
