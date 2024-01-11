package com.example.rickandmortyapi.di.daggerComponents

import android.content.Context
import com.example.rickandmortyapi.di.modules.ApiModule
import com.example.rickandmortyapi.di.modules.DBModule
import com.example.rickandmortyapi.di.modules.EpisodesFeedViewModelModule
import com.example.rickandmortyapi.di.modules.InternetConnectionViewModelModule
import com.example.rickandmortyapi.di.modules.PaginationModule
import com.example.rickandmortyapi.di.modules.ViewModelFactoryModule
import com.example.rickandmortyapi.presenter.ui.EpisodesFeedFragment
import com.example.rickandmortyapi.presenter.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApiModule::class, DBModule::class,
    PaginationModule::class, ViewModelFactoryModule::class,
    EpisodesFeedViewModelModule::class,
    InternetConnectionViewModelModule::class])
interface EpisodesFeedFragmentComponent {
    @Component.Factory
    interface ComponentBuilder{
        fun create(@BindsInstance context: Context): EpisodesFeedFragmentComponent
    }
    fun inject(fragment: EpisodesFeedFragment)


}