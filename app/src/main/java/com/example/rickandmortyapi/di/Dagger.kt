package com.example.rickandmortyapi.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.rickandmortyapi.data.PaginationData
import com.example.rickandmortyapi.data.PaginationDataRepositoryImpl
import com.example.rickandmortyapi.presenter.ui.FiltersFragment
import com.example.rickandmortyapi.data.db.DB
import com.example.rickandmortyapi.data.db.repository.CharactersDbRepositoryImpl
import com.example.rickandmortyapi.data.db.repository.EpisodesDbRepositoryImpl
import com.example.rickandmortyapi.data.network.repository.CharactersApiRepositoryImpl
import com.example.rickandmortyapi.data.network.repository.EpisodesApiRepositoryImpl
import com.example.rickandmortyapi.data.network.service.CharactersApiService
import com.example.rickandmortyapi.data.network.service.EpisodesApiService
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.domain.repository.CharactersDbRepository
import com.example.rickandmortyapi.domain.repository.EpisodesApiRepository
import com.example.rickandmortyapi.domain.repository.EpisodesDbRepository
import com.example.rickandmortyapi.domain.repository.PaginationDataRepository
import com.example.rickandmortyapi.presenter.ui.CharacterDetailsFragment
import com.example.rickandmortyapi.presenter.ui.FeedFragment
import com.example.rickandmortyapi.presenter.ui.MainActivity
import com.example.rickandmortyapi.presenter.viewmodels.CharacterDetailsViewModel
import com.example.rickandmortyapi.presenter.viewmodels.FeedViewModel
import com.example.rickandmortyapi.presenter.viewmodels.MultiViewModelFactory
import com.example.rickandmortyapi.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.assisted.AssistedInject
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DBModule::class,
    PaginationModule::class, ViewModelModule::class])
interface AppComponent{
    @Component.Factory
    interface ComponentBuilder{
        fun create(@BindsInstance context: Context):AppComponent
    }
    fun inject(activity:MainActivity)
    fun inject(fragment: FeedFragment)
    fun inject(fragment: FiltersFragment)

    fun inject(fragment: CharacterDetailsFragment)

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

        @Provides
        fun provideEpisodeDao(db: DB) = db.episodeDao()
    }

    @Binds
    fun provideCharactersDbRepository(charactersDbRepositoryImpl:
                                      CharactersDbRepositoryImpl): CharactersDbRepository

    @Binds
    fun provideEpisodesDbRepository(episodesDbRepositoryImpl:
                                    EpisodesDbRepositoryImpl): EpisodesDbRepository
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
        fun provideCharactersApiService(converterFactory: GsonConverterFactory, client: OkHttpClient)
                : CharactersApiService = Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(CharactersApiService::class.java)

        @Provides
        fun provideEpisodesApiService(converterFactory: GsonConverterFactory, client: OkHttpClient)
                : EpisodesApiService = Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(EpisodesApiService::class.java)


    }

    @Binds
    fun provideCharactersApiRepository(charactersApiRepositoryImpl: CharactersApiRepositoryImpl)
    : CharactersApiRepository

    @Binds
    fun provideEpisodesApiRepository(episodesApiRepositoryImpl: EpisodesApiRepositoryImpl)
    : EpisodesApiRepository



}
@Module
interface PaginationModule{
    companion object{
        @Provides
        @Singleton
        fun providePaginationData():PaginationData = PaginationData()
    }
    @Binds
    fun providePaginationRepository(paginationDataRepositoryImpl: PaginationDataRepositoryImpl)
    :PaginationDataRepository
}

@Module
interface ViewModelModule{
    @Binds
    @[IntoMap ViewModelKey(FeedViewModel::class)]
    fun provideFeedViewModel(feedViewModel: FeedViewModel):ViewModel

    @Binds
    @[IntoMap ViewModelKey(CharacterDetailsViewModel::class)]
    fun provideCharacterDetailsViewModel(characterDetailsViewModel:
                                         CharacterDetailsViewModel):ViewModel

    @Binds
    fun provideCharacterFeedViewModelFactory(viewModelFactory: MultiViewModelFactory): ViewModelProvider.Factory


    companion object{

    }
}
