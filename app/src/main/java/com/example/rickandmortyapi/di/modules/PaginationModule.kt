package com.example.rickandmortyapi.di.modules

import com.example.rickandmortyapi.data.PaginationData
import com.example.rickandmortyapi.data.PaginationDataRepositoryImpl
import com.example.rickandmortyapi.domain.repository.PaginationDataRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface PaginationModule{
    companion object{
        @Provides
        @Singleton
        fun providePaginationData(): PaginationData = PaginationData()
    }
    @Binds
    fun providePaginationRepository(paginationDataRepositoryImpl: PaginationDataRepositoryImpl)
            : PaginationDataRepository
}