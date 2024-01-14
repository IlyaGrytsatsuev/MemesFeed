package com.example.rickandmortyapi.domain.models

class CharacterModel(val created: String,
                     val gender: String,
                     override val id: Int,
                     val image: String,
                     val location: CharacterModelLocation,
                     val name: String,
                     val origin: CharacterModelOrigin,
                     val species: String,
                     val status: String,
                     val type: String,
                     val url: String,

) : RecyclerModel(id)


data class CharacterModelLocation(
    val name: String,
    val url: String
){
    companion object{
        fun newEmptyInstance(): CharacterModelLocation =
            CharacterModelLocation(name = "", url = "")
    }
}

data class CharacterModelOrigin(
    val name: String,
    val url: String
) {
    companion object{
        fun newEmptyInstance(): CharacterModelOrigin =
            CharacterModelOrigin(name = "", url = "")
    }
}

