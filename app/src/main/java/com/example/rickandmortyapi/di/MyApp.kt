package com.example.rickandmortyapi.di

import android.app.Application
import android.content.Context
import com.example.rickandmortyapi.di.daggerComponents.CharacterFeedFragmentComponent


class MyApp : Application() {

    lateinit var appComponent: CharacterFeedFragmentComponent

    override fun onCreate() {
        super.onCreate()
//        appComponent = DaggerAppComponent.factory()
//           .create(context = this)
    }


}

val Context.appComponent: CharacterFeedFragmentComponent?
    get() = when (this) {
        is MyApp -> appComponent
        else -> this.applicationContext.appComponent
    }
