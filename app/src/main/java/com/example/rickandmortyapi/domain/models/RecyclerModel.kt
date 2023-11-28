package com.example.rickandmortyapi.domain.models

abstract class RecyclerModel {
    abstract val id:Int
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecyclerModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

}