package com.example.rickandmortyapi.di.modules

import androidx.lifecycle.ViewModel
import com.example.rickandmortyapi.di.ViewModelKey
import com.example.rickandmortyapi.presenter.viewmodels.CharacterDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DetailsViewModelsModule{
    @Binds
    @[IntoMap ViewModelKey(CharacterDetailsViewModel::class)]
    fun provideCharacterDetailsViewModel(characterDetailsViewModel:
                                         CharacterDetailsViewModel
    ): ViewModel
}