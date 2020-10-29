package edu.usf.sas.pal.muser.util

import android.content.Context
import android.util.Log
import com.simplecity.amp_library.model.Album
import com.simplecity.amp_library.model.AlbumArtist
import com.simplecity.amp_library.model.Genre
import com.simplecity.amp_library.model.Song
import com.simplecity.amp_library.utils.MusicServiceConnectionUtils
import edu.usf.sas.pal.muser.model.*

object EventUtils {
    /**
     * Function to populate the PlayerEvent data class.
     * @param song - The song for which the event occurred.
     * @param capturedEvent - The event that was captured.
     * @param context - The context of the Fragment.
     * @return PlayerEvent object
     */
    @JvmStatic
    fun newPlayerEvent(song: Song, capturedEvent: PlayerEventType, context: Context): PlayerEvent {
        val currentTimeMS = System.currentTimeMillis()
        val nanoTime = System.nanoTime()
        val songData = SongData(song, context)
        val seekPositionMs = MusicServiceConnectionUtils.getPosition()
        return PlayerEvent(capturedEvent, currentTimeMS, nanoTime, seekPositionMs,
                songData)
    }

    /**
     * Function to populate the UiAction data class
     * @param song - The song for which the action was performed.
     * @param capturedUiAction - The action that was captured
     * @param context - The context of the fragment.
     * @return UiAction object
     */
    @JvmStatic
    fun newUiEvent(song: Song, capturedUiAction: UiEventType, context: Context): UiEvent {
        val currentTimeMS = System.currentTimeMillis()
        val nanoTime = System.nanoTime()
        val songData = SongData(song, context)
        val seekPositionMs = MusicServiceConnectionUtils.getPosition();
        return UiEvent(uiEventType = capturedUiAction, currentTimeMs = currentTimeMS,
                nanoTime = nanoTime, seekPositionMs = seekPositionMs, song = songData)
    }

    /**
     * Function to populate the UiAction class with Album data when the overflow button is clicked
     * @param album - The Album for which the action was performed
     * @param capturedUiAction = The action that was captured.
     * @return UiAction object
     */
    @JvmStatic
    fun newUiAlbumEvent(album: Album, capturedUiAction: UiEventType): UiEvent {
        val currentTimeMS = System.currentTimeMillis()
        val nanoTime = System.nanoTime()
        val albumData = AlbumData(album)
        return UiEvent(uiEventType = capturedUiAction, currentTimeMs = currentTimeMS,
                nanoTime = nanoTime, album = albumData)
    }

    /**
     * Function to populate the UiAction class with AlbumArtist data when the overflow button is clicked
     * @param albumArtist - The Album Artist for which the action was performed
     * @param capturedUiAction - The action that was captured.
     * @return UiAction object
     */

    @JvmStatic
    fun newUiAlbumArtistEvent(albumArtist: AlbumArtist, capturedUiAction: UiEventType): UiEvent {
        val currentTimeMS = System.currentTimeMillis()
        val nanoTime = System.nanoTime()
        val albums = mutableListOf<AlbumData>()
        albumArtist.albums.forEach{
            val albumData = AlbumData(it.id, it.name, it.artists, it.albumArtistName, it.year,
                             it.numSongs, it.numDiscs, it.dateAdded, it.paths)
            albums.add(albumData)
        }
        val albumArtistData = AlbumArtistData(albumArtist, albums)
        return UiEvent(uiEventType = capturedUiAction, currentTimeMs = currentTimeMS,
                nanoTime = nanoTime, albumArtist = albumArtistData)
    }

    /**
     * Function to populate the UiAction class with Genre data when the overflow button is clicked
     * @param genre - The Genre for which the action was performed.
     * @param capturedUiAction - The action that was captured
     * @return UiAction object
     */
    @JvmStatic
    fun newUiGenreEvent(genre: Genre, capturedUiAction: UiEventType): UiEvent {
        val currentTimeMS = System.currentTimeMillis()
        val nanoTime = System.nanoTime()
        val genreData = GenreData(genre)
        return UiEvent(uiEventType = capturedUiAction, currentTimeMs = currentTimeMS,
                nanoTime = nanoTime, genre = genreData)
    }
}