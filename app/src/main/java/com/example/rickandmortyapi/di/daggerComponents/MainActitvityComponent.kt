package com.example.rickandmortyapi.di.daggerComponents

import android.content.Context
import com.example.rickandmortyapi.di.modules.InternetConnectionViewModelModule
import com.example.rickandmortyapi.presenter.ui.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [InternetConnectionViewModelModule::class])
interface MainActitvityComponent {
    @Component.Factory
    interface ComponentBuilder{
        fun create(@BindsInstance context: Context)
        : MainActitvityComponent
    }
    fun inject(activity:MainActivity)
}