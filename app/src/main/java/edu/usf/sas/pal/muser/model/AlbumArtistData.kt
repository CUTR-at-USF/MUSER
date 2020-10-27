package edu.usf.sas.pal.muser.model

import com.simplecity.amp_library.model.Album
import com.simplecity.amp_library.model.AlbumArtist

data class AlbumArtistData

(val name: String,
 val albumData: List<AlbumData>
){
    constructor(albumArtist: AlbumArtist, albums: List<AlbumData>): this(albumArtist.name, albums)
}