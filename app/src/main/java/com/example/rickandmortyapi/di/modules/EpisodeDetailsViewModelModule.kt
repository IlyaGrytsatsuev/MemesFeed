package com.example.rickandmortyapi.di.modules

import androidx.lifecycle.ViewModel
import com.example.rickandmortyapi.di.ViewModelKey
import com.example.rickandmortyapi.presenter.viewmodels.EpisodeDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface EpisodeDetailsViewModelModule {

    @Binds
    @[IntoMap ViewModelKey(EpisodeDetailsViewModel::class)]
    fun provideCharacterDetailsViewModel(episodeDetailsViewModel:
                                         EpisodeDetailsViewModel
    ): ViewModel
}