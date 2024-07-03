package com.pedroewaquim.pokedex

object Extensions {
    fun String.capitalize() : String {
        return replaceFirstChar { first -> first.titlecase() }
    }
}