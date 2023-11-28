package com.example.rickandmortyapi.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.rickandmortyapi.presenter.ui.FiltersFragment
import com.example.rickandmortyapi.data.db.DB
import com.example.rickandmortyapi.data.db.repository.CharactersDbRepositoryImpl
import com.example.rickandmortyapi.data.network.repository.CharactersApiRepositoryImpl
import com.example.rickandmortyapi.data.network.service.CharactersApiService
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.domain.repository.CharactersDbRepository
import com.example.rickandmortyapi.presenter.ui.FeedFragment
import com.example.rickandmortyapi.presenter.ui.MainActivity
import com.example.rickandmortyapi.presenter.viewmodels.FeedViewModelFactory
import com.example.rickandmortyapi.presenter.viewmodels.FilteredFeedViewModelFactory
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
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DBModule::class])
interface AppComponent{
    @Component.Factory
    interface ComponentBuilder{
        fun create(@BindsInstance context: Context):AppComponent
    }
    fun inject(activity:MainActivity)
    fun inject(fragment: FeedFragment)
    fun inject(fragment: FiltersFragment)
}

@Module
interface DBModule{
    companion object{
        @Provides
        fun provideDB(context:Context) =
            Room.databaseBuilder(context,
            DB::class.java, "rickandmortydb").build()
        @Provides
        fun provideCharacterDao(db: DB) = db.characterDao()
    }

    @Binds
    fun provideCharactersDbRepository(charactersDbRepositoryImpl:
                                      CharactersDbRepositoryImpl): CharactersDbRepository
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
                : CharactersApiService = Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(CharactersApiService::class.java)
    }

    @Binds
    @Named("characterFeed")
    fun provideCharacterFeedViewModelFactory(viewModelFactory: FeedViewModelFactory): ViewModelProvider.Factory
    @Binds
    @Named("filteredCharacterFeed")
    fun provideFilteredViewModelFactory(viewModelFactory: FilteredFeedViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun provideMemesApiRepository(memesApiRepositoryImpl: CharactersApiRepositoryImpl) : CharactersApiRepository

}
