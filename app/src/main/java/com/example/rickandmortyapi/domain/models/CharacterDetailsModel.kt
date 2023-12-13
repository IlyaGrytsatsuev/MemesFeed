package com.example.rickandmortyapi.domain.models

class CharacterDetailsModel(val created: String,
                            val episodeIds:List<Int>,
                            val episode: MutableList<EpisodeModel>,
                            val gender: String,
                            override val id: Int,
                            val image: String,
                            val location: CharacterModelLocation,
                            val name: String,
                            val origin: CharacterModelOrigin,
                            val species: String,
                            val status: String,
                            val type: String,
                            val url: String) : RecyclerModel()


