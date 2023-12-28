package com.example.rickandmortyapi.di.modules

import com.example.rickandmortyapi.data.network.repository.CharactersApiRepositoryImpl
import com.example.rickandmortyapi.data.network.repository.EpisodeDetailsApiRepositoryImpl
import com.example.rickandmortyapi.data.network.repository.EpisodesApiRepositoryImpl
import com.example.rickandmortyapi.data.network.service.CharactersApiService
import com.example.rickandmortyapi.data.network.service.EpisodesApiService
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.domain.repository.EpisodeDetailsApiRepository
import com.example.rickandmortyapi.domain.repository.EpisodesApiRepository
import com.example.rickandmortyapi.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface ApiModule{
    companion object{
        @Provides
        fun provideGsonConverterFactory() : GsonConverterFactory
                = GsonConverterFactory.create(GsonBuilder().create())

        @Provides
        fun provideOkHttpClient() = OkHttpClient().newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        @Provides
        fun provideCharactersApiService(converterFactory: GsonConverterFactory, client: OkHttpClient)
                : CharactersApiService = Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(CharactersApiService::class.java)

        @Provides
        fun provideEpisodesApiService(converterFactory: GsonConverterFactory, client: OkHttpClient)
                : EpisodesApiService = Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(EpisodesApiService::class.java)


    }

    @Binds
    fun provideCharactersApiRepository(charactersApiRepositoryImpl: CharactersApiRepositoryImpl)
            : CharactersApiRepository

    @Binds
    fun provideEpisodesApiRepository(episodesApiRepositoryImpl: EpisodesApiRepositoryImpl)
            : EpisodesApiRepository

    @Binds
    fun provideEpisodeDetailsApiRepository(episodeDetailsApiRepositoryImpl: EpisodeDetailsApiRepositoryImpl)
            : EpisodeDetailsApiRepository




}