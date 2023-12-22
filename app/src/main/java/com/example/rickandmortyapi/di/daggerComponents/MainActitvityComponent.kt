package com.example.rickandmortyapi.di.daggerComponents

import android.content.Context
import com.example.rickandmortyapi.di.modules.InternetConnectionViewModelModule
import com.example.rickandmortyapi.di.modules.ViewModelFactoryModule
import com.example.rickandmortyapi.presenter.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [InternetConnectionViewModelModule::class,
    ViewModelFactoryModule::class])
@Singleton
interface MainActivityComponent {
    @Component.Factory
    interface ComponentBuilder{
        fun create(@BindsInstance context: Context)
        : MainActivityComponent
    }
    fun inject(activity:MainActivity)
}