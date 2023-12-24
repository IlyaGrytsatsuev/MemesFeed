package com.example.rickandmortyapi.di.modules

import androidx.lifecycle.ViewModel
import com.example.rickandmortyapi.di.ViewModelKey
import com.example.rickandmortyapi.presenter.viewmodels.CharacterDetailsViewModel
import com.example.rickandmortyapi.presenter.viewmodels.EpisodesFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface EpisodesFeedViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(EpisodesFeedViewModel::class)]
    fun provideEpisodeViewModel(episodesFeedViewModel: EpisodesFeedViewModel)
    : ViewModel
}