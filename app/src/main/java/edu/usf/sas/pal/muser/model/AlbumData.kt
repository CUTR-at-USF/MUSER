package edu.usf.sas.pal.muser.model

import com.simplecity.amp_library.model.Album
import com.simplecity.amp_library.model.Artist

data class AlbumData
(val id: Long,
 val name: String,
 val artists: List<Artist>,
 val albumArtistName: String,
 val year: Int,
 val numSongs: Int,
 val numDiscs: Int,
 val dateAdded: Long,
 val paths: List<String>
 ){
    constructor(album: Album): this(album.id, album.name, album.artists, album.albumArtistName,
            album.year, album.numSongs, album.numDiscs, album.dateAdded, album.paths)
}

