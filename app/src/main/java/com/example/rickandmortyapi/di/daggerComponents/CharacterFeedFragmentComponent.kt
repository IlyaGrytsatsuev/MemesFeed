package com.example.rickandmortyapi.di.daggerComponents

import android.content.Context
import com.example.rickandmortyapi.di.modules.ApiModule
import com.example.rickandmortyapi.di.modules.DBModule
import com.example.rickandmortyapi.di.modules.CharactersFeedViewModelModule
import com.example.rickandmortyapi.di.modules.InternetConnectionViewModelModule
import com.example.rickandmortyapi.di.modules.PaginationModule
import com.example.rickandmortyapi.di.modules.ViewModelFactoryModule
import com.example.rickandmortyapi.presenter.ui.CharactersFeedFragment
import com.example.rickandmortyapi.presenter.ui.CharacterFiltersFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DBModule::class,
    PaginationModule::class, ViewModelFactoryModule::class,
    CharactersFeedViewModelModule::class,
    InternetConnectionViewModelModule::class])
interface CharacterFeedFragmentComponent{
    @Component.Factory
    interface ComponentBuilder{
        fun create(@BindsInstance context: Context): CharacterFeedFragmentComponent
    }
    fun inject(fragment: CharactersFeedFragment)
    fun inject(fragment: CharacterFiltersFragment)
}


