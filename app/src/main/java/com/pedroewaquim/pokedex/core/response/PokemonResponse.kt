package com.pedroewaquim.pokedex.core.response

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    val id: Int,
    val name: String,
    val sprites: Sprite,
) {
    data class Sprite(
        @SerializedName("front_default")
        val default: String
    )
}


