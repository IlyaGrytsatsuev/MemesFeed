package com.example.rickandmortyapi.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.presenter.viewmodels.MultiViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule{
    @Binds
    fun provideCharacterFeedViewModelFactory(viewModelFactory: MultiViewModelFactory): ViewModelProvider.Factory

}