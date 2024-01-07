package com.example.rickandmortyapi.domain.models

class CharacterDetailsModel(val created: String = "",
                            val isNullReceived: Boolean = false,
                            val episodeIds:List<Int> = emptyList(),
                            var episode: List<EpisodeModel>
                            = emptyList(),
                            val gender: String = "",
                            override val id: Int = 0,
                            val image: String = "",
                            val location: CharacterModelLocation
                            = CharacterModelLocation(),
                            val name: String = "",
                            val origin: CharacterModelOrigin
                            = CharacterModelOrigin(),
                            val species: String = "",
                            val status: String = "",
                            val type: String = "",
                            val url: String = "",
                            override var listSize:Int
                            = episode.size) : RecyclerModel()


