package com.example.rickandmortyapi.di.modules

import androidx.lifecycle.ViewModel
import com.example.rickandmortyapi.di.ViewModelKey
import com.example.rickandmortyapi.presenter.viewmodels.InternetConnectionObserverViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface InternetConnectionViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(InternetConnectionObserverViewModel::class)]
    fun provideCharacterDetailsViewModel
                (internetConnectionObserverViewModel
                 : InternetConnectionObserverViewModel): ViewModel
}