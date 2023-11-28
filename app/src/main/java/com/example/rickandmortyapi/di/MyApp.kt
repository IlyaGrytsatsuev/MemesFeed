package com.example.rickandmortyapi.di

import android.app.Application
import android.content.Context


class MyApp : Application() {

    lateinit var appComponent:AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory()
           .create(context = this)
    }


}

val Context.appComponent: AppComponent?
    get() = when (this) {
        is MyApp -> appComponent
        else -> this.applicationContext.appComponent
    }
