package com.example.rickandmortyapi.di.daggerComponents

import android.content.Context
import com.example.rickandmortyapi.di.modules.ApiModule
import com.example.rickandmortyapi.di.modules.DBModule
import com.example.rickandmortyapi.di.modules.DetailsViewModelsModule
import com.example.rickandmortyapi.di.modules.PaginationModule
import com.example.rickandmortyapi.di.modules.ViewModelFactoryModule
import com.example.rickandmortyapi.presenter.ui.CharacterDetailsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApiModule::class, DBModule::class,
    PaginationModule::class, ViewModelFactoryModule::class,
    DetailsViewModelsModule::class])
interface CharacterDetailsFragmentComponent {
    @Component.Factory
    interface ComponentBuilder{
        fun create(@BindsInstance context: Context,
                   @BindsInstance id: Int): CharacterDetailsFragmentComponent
    }
    fun inject(fragment: CharacterDetailsFragment)
}