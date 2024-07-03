package com.pedroewaquim.pokedex.core.domain

data class Pokemon(val id: String, val name: String, val imageUrl: String? = null) {
    override fun toString(): String = "$id $name"
}