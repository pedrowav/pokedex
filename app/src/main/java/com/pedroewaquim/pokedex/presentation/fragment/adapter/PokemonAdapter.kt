package com.pedroewaquim.pokedex.presentation.fragment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pedroewaquim.pokedex.R
import com.pedroewaquim.pokedex.core.domain.Pokemon
import com.pedroewaquim.pokedex.databinding.PokemonItemLayoutBinding

class PokemonAdapter(
    private var pokemonList: List<Pokemon>
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            PokemonItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.number.text = pokemon.id
        holder.name.text = pokemon.name
        bindImage(holder, pokemon)
    }

    override fun getItemCount(): Int = pokemonList.size

    private fun bindImage(
        holder: PokemonViewHolder,
        pokemon: Pokemon
    ) {
        Glide.with(holder.itemView.context)
            .setDefaultRequestOptions(RequestOptions())
            .load(pokemon.imageUrl)
            .placeholder(
                R.drawable.pokeball
            ).into(holder.image)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(pokemonList: List<Pokemon>) {
        this.pokemonList = pokemonList
        notifyDataSetChanged()
    }

    inner class PokemonViewHolder(binding: PokemonItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val number: TextView = binding.pokemonNumber
        val name: TextView = binding.pokemonName
        val image: ImageView = binding.pokemonImage

        override fun toString(): String {
            return super.toString() + " '" + number.text + "'"
        }
    }
}