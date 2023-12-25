package com.example.rickandmortyapi.di.daggerComponents

import android.content.Context
import com.example.rickandmortyapi.di.modules.ApiModule
import com.example.rickandmortyapi.di.modules.CharacterDetailsViewModelsModule
import com.example.rickandmortyapi.di.modules.DBModule
import com.example.rickandmortyapi.di.modules.EpisodeDetailsViewModelModule
import com.example.rickandmortyapi.di.modules.InternetConnectionViewModelModule
import com.example.rickandmortyapi.di.modules.PaginationModule
import com.example.rickandmortyapi.di.modules.ViewModelFactoryModule
import com.example.rickandmortyapi.presenter.ui.CharacterDetailsFragment
import com.example.rickandmortyapi.presenter.ui.EpisodeDetailsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApiModule::class, DBModule::class,
    PaginationModule::class,
    ViewModelFactoryModule::class,
    EpisodeDetailsViewModelModule::class,
    InternetConnectionViewModelModule::class])
interface EpisodeDetailsFragmentComponent {
    @Component.Factory
    interface ComponentBuilder{
        fun create(@BindsInstance context: Context,
                   @BindsInstance id: Int): EpisodeDetailsFragmentComponent
    }
    fun inject(fragment: EpisodeDetailsFragment)
}