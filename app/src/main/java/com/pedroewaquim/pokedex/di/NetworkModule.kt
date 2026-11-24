package com.pedroewaquim.pokedex.di

import com.pedroewaquim.pokedex.core.action.GetPokemonList
import com.pedroewaquim.pokedex.core.service.PokemonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_BASE_URL = "https://pokeapi.co/api/v2/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providePokemonApiService(retrofit: Retrofit): PokemonApiService =
        retrofit.create(PokemonApiService::class.java)

    @Provides
    fun provideGetPokemonList(service: PokemonApiService): GetPokemonList =
        GetPokemonList(service)
}
