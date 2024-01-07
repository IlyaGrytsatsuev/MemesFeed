package com.example.rickandmortyapi.domain.models

 class EpisodeDetailsModel (
     override val id:Int = 0,
     val isNullReceived: Boolean = false,
     val name: String = "",
     val episode: String = "",
     val charactersIds: List<Int> = emptyList(),
     var characters:List<CharacterModel> = emptyList(),
     override var listSize:Int = characters.size
 ): RecyclerModel()