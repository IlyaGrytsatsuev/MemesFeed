package com.example.rickandmortyapi.di.daggerComponents

import android.content.Context
import com.example.rickandmortyapi.di.modules.ApiModule
import com.example.rickandmortyapi.di.modules.DBModule
import com.example.rickandmortyapi.di.modules.FeedViewModelModule
import com.example.rickandmortyapi.di.modules.InternetConnectionViewModelModule
import com.example.rickandmortyapi.di.modules.PaginationModule
import com.example.rickandmortyapi.di.modules.ViewModelFactoryModule
import com.example.rickandmortyapi.presenter.ui.FeedFragment
import com.example.rickandmortyapi.presenter.ui.FiltersFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DBModule::class,
    PaginationModule::class, ViewModelFactoryModule::class,
    FeedViewModelModule::class,
    InternetConnectionViewModelModule::class])
interface CharacterFeedFragmentComponent{
    @Component.Factory
    interface ComponentBuilder{
        fun create(@BindsInstance context: Context): CharacterFeedFragmentComponent
    }
    fun inject(fragment: FeedFragment)
}


