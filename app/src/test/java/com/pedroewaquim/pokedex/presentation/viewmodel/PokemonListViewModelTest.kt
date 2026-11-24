package com.pedroewaquim.pokedex.presentation.viewmodel


import com.pedroewaquim.pokedex.core.action.GetPokemonList
import com.pedroewaquim.pokedex.core.callback.PokemonListCallback
import com.pedroewaquim.pokedex.core.domain.Pokemon
import com.pedroewaquim.pokedex.presentation.PokedexState
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PokemonListViewModelTest {

    private lateinit var getPokemonList: GetPokemonList
    private lateinit var viewModel: PokemonListViewModel

    private val callbackSlot = slot<PokemonListCallback>()
    private val offsetSlot = slot<Int>()

    @Before
    fun setup() {
        getPokemonList = mockk(relaxed = true)
        every {
            getPokemonList(capture(callbackSlot), capture(offsetSlot))
        } answers {}

        viewModel = PokemonListViewModel(getPokemonList)
    }

    @Test
    fun `initial state is Success with empty list and does not fetch automatically`() {
        assertEquals(PokedexState.Success(emptyList()), viewModel.pokedexState.value)
        verify(exactly = 0) { getPokemonList(any(), any()) }
    }

    @Test
    fun `getMorePokemonIfNeeded with default arg fetches initial page with offset 0`() {
        viewModel.getMorePokemonIfNeeded()

        verify(exactly = 1) {
            getPokemonList(viewModel, offset = 0)
        }
    }

    @Test
    fun `concurrent calls while request is in flight do not trigger duplicate fetches`() {
        viewModel.getMorePokemonIfNeeded(lastItemPos = -1)
        viewModel.getMorePokemonIfNeeded(lastItemPos = -1)
        viewModel.getMorePokemonIfNeeded(lastItemPos = 0)

        verify(exactly = 1) {
            getPokemonList(viewModel, offset = 0)
        }
    }

    @Test
    fun `success appends pokemon, updates state, and allows subsequent page fetch`() {
        val page1 = listOf(fakePokemon(1), fakePokemon(2))
        val page2 = listOf(fakePokemon(3), fakePokemon(4))

        viewModel.getMorePokemonIfNeeded(lastItemPos = -1)
        callbackSlot.captured.success(page1)

        assertEquals(PokedexState.Success(page1), viewModel.pokedexState.value)

        // Con 2 items: 2 - 3 <= lastItemPos + 1 -> -1 <= lastItemPos + 1 (cualquier lastItemPos >= -2 cumple)
        viewModel.getMorePokemonIfNeeded(lastItemPos = 1)
        verify(exactly = 1) { getPokemonList(viewModel, offset = 2) }

        callbackSlot.captured.success(page2)
        assertEquals(PokedexState.Success(page1 + page2), viewModel.pokedexState.value)
    }

    @Test
    fun `does not fetch next page before reaching threshold of last 3 items`() {
        val twentyPokemon = (1..20).map { fakePokemon(it) }

        viewModel.getMorePokemonIfNeeded(lastItemPos = -1)
        callbackSlot.captured.success(twentyPokemon)

        // Umbral: 20 - 3 <= lastItemPos + 1  =>  17 <= lastItemPos + 1  =>  lastItemPos >= 16
        viewModel.getMorePokemonIfNeeded(lastItemPos = 15)

        // Solo se llamó 1 vez (la carga inicial con offset 0)
        verify(exactly = 1) { getPokemonList(any(), any()) }
    }

    @Test
    fun `fetches next page when reaching threshold of last 3 items`() {
        val twentyPokemon = (1..20).map { fakePokemon(it) }

        viewModel.getMorePokemonIfNeeded(lastItemPos = -1)
        callbackSlot.captured.success(twentyPokemon)

        // lastItemPos = 16 -> 20 - 3 <= 16 + 1 (17 <= 17) -> true
        viewModel.getMorePokemonIfNeeded(lastItemPos = 16)

        verify(exactly = 1) { getPokemonList(viewModel, offset = 20) }
    }

    @Test
    fun `error resets in-flight flag so retry is possible with same offset`() {
        val page1 = listOf(fakePokemon(1))

        viewModel.getMorePokemonIfNeeded(lastItemPos = -1)
        callbackSlot.captured.error()

        // Debe poder reintentar con offset 0
        viewModel.getMorePokemonIfNeeded(lastItemPos = -1)
        verify(exactly = 2) { getPokemonList(viewModel, offset = 0) }

        callbackSlot.captured.success(page1)
        val state = viewModel.pokedexState.value
        assertTrue(state is PokedexState.Success && state.pokemonList == page1)
    }

    private fun fakePokemon(id: Int): Pokemon = Pokemon(
        id = id.toString(), name = "fakePokemon"
    )
}