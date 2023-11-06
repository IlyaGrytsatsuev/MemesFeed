package com.example.memesfeed.di

import android.content.Context
import com.example.memesfeed.data.network.repository.MemesApiRepositoryImpl
import com.example.memesfeed.data.network.service.MemesApiService
import com.example.memesfeed.domain.repository.MemesApiRepository
import com.example.memesfeed.presenter.ui.MainActivity
import com.example.memesfeed.utils.Constants
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
    fun provideMemesApiRepository(memesApiRepositoryImpl: MemesApiRepositoryImpl) : MemesApiRepository

}
