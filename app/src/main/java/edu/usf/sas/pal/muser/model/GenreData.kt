package edu.usf.sas.pal.muser.model

import com.simplecity.amp_library.model.Genre

data class GenreData
(val id: Long,
 val name: String?,
 val numSongs: Int
){
    constructor(genre: Genre): this(genre.id, genre.name, genre.numSongs)
}

