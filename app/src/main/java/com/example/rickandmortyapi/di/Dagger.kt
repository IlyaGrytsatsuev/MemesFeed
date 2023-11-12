package com.example.rickandmortyapi.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.data.network.repository.MemesApiRepositoryImpl
import com.example.rickandmortyapi.data.network.service.MemesApiService
import com.example.rickandmortyapi.domain.repository.MemesApiRepository
import com.example.rickandmortyapi.presenter.ui.FeedFragment
import com.example.rickandmortyapi.presenter.ui.MainActivity
import com.example.rickandmortyapi.presenter.viewmodels.FeedViewModelFactory
import com.example.rickandmortyapi.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface AppComponent{
    @Component.Factory
    interface ComponentBuilder{
        fun create(@BindsInstance context: Context):AppComponent
    }
    fun inject(activity:MainActivity)
    fun inject(fragment: FeedFragment)
}

@Module
interface ApiModule{
    companion object{
        @Provides
        fun provideGsonConverterFactory() :GsonConverterFactory
                = GsonConverterFactory.create(GsonBuilder().create())

        @Provides
        fun provideOkHttpClient() = OkHttpClient().newBuilder()
           .addInterceptor(HttpLoggingInterceptor()
               .setLevel(HttpLoggingInterceptor.Level.BODY))
               .build()

        @Provides
        fun provideMemesApiService(converterFactory: GsonConverterFactory, client: OkHttpClient)
                : MemesApiService = Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(MemesApiService::class.java)
    }

    @Binds
    fun provideViewModelFactory(viewModelFactory: FeedViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun provideMemesApiRepository(memesApiRepositoryImpl: MemesApiRepositoryImpl) : MemesApiRepository



}
