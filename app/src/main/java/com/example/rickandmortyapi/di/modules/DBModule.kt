package com.example.rickandmortyapi.di.modules

import android.content.Context
import androidx.room.Room
import com.example.rickandmortyapi.data.db.DB
import com.example.rickandmortyapi.data.db.repository.CharactersDbRepositoryImpl
import com.example.rickandmortyapi.data.db.repository.EpisodesDbRepositoryImpl
import com.example.rickandmortyapi.domain.repository.CharactersDbRepository
import com.example.rickandmortyapi.domain.repository.EpisodesDbRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DBModule{
    companion object{
        @Provides
        fun provideDB(context: Context) =
            Room.databaseBuilder(context,
                DB::class.java, "rickandmortydb").build()
        @Provides
        fun provideCharacterDao(db: DB) = db.characterDao()

        @Provides
        fun provideEpisodeDao(db: DB) = db.episodeDao()
    }

    @Binds
    fun provideCharactersDbRepository(charactersDbRepositoryImpl:
                                      CharactersDbRepositoryImpl
    ): CharactersDbRepository

    @Binds
    fun provideEpisodesDbRepository(episodesDbRepositoryImpl:
                                    EpisodesDbRepositoryImpl
    ): EpisodesDbRepository
}