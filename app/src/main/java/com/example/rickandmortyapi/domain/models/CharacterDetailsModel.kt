package com.example.rickandmortyapi.domain.models

class CharacterDetailsModel
    (val created: String,
     val episodeIds:List<Int>,
     var episode: List<EpisodeModel>,
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
     override val listSize:Int) : DetailsModel(id,listSize) {

        companion object {
            fun newEmptyInstance(): CharacterDetailsModel =
                CharacterDetailsModel(
                    created = "", episodeIds = emptyList(),
                    episode = emptyList(), gender = "",
                    id = 0, image = "",
                    location = CharacterModelLocation
                        .newEmptyInstance(),
                    name = "", origin = CharacterModelOrigin
                        .newEmptyInstance(),
                    species = "", status = "", type = "",
                    url = "", listSize = 0
                )
        }
     }


