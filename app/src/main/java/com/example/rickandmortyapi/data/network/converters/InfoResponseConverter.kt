package com.example.rickandmortyapi.data.network.converters

import com.example.rickandmortyapi.data.network.responseModels.Info
import com.example.rickandmortyapi.domain.models.InfoModel

fun Info.toDomainModel(): InfoModel =
    InfoModel(pages = this.pages?:0)
