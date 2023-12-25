package com.example.rickandmortyapi.di.modules

import androidx.lifecycle.ViewModel
import com.example.rickandmortyapi.di.ViewModelKey
import com.example.rickandmortyapi.presenter.viewmodels.CharactersFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CharactersFeedViewModelModule{
    @Binds
    @[IntoMap ViewModelKey(CharactersFeedViewModel::class)]
    fun provideFeedViewModel(feedViewModel: CharactersFeedViewModel): ViewModel


}
