package edu.usf.sas.pal.muser.model

import com.simplecity.amp_library.model.Album
import com.simplecity.amp_library.model.AlbumArtist
import com.simplecity.amp_library.model.Artist

/**
 *  A model class that implements the Event interface and stores user interface interaction events.
 *  The timestamp and type of event, song position, and song metadata is captured.
 */
data class UiEvent

/**
 * [uiEventType] - The enum object for the type of action performed.
 */
(val uiEventType: UiEventType,
 override val currentTimeMs: Long,
 override val nanoTime: Long,
 override val seekPositionMs: Long = 0,
 override val song: SongData? = null,
 val album: AlbumData? = null,
 val albumArtist: AlbumArtistData? = null,
 val genre: GenreData? = null
) : Event