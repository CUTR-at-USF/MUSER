package edu.usf.sas.pal.muser.util

import android.content.Context
import com.simplecity.amp_library.ShuttleApplication
import com.simplecity.amp_library.model.Album
import com.simplecity.amp_library.model.AlbumArtist
import com.simplecity.amp_library.model.Genre
import com.simplecity.amp_library.model.Song
import com.simplecity.amp_library.utils.MusicServiceConnectionUtils
import edu.usf.sas.pal.muser.model.*


object EventUtils {

    private const val TAG = "EventUtils"

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
        val audioData = AudioDeviceUtils.getAudioData(context)
        val seekPositionMs = MusicServiceConnectionUtils.getPosition()
        return PlayerEvent(capturedEvent, currentTimeMS, nanoTime, seekPositionMs,
                songData, audioData = audioData)
    }

    /**
     * Function to populate the UiEvent data class
     * @param song - The song for which the action was performed.
     * @param capturedUiAction - The action that was captured
     * @param context - The context of the fragment.
     * @param seekPosition - seek position of the song calculated outside the function. If the seek
     * position is Long.MAX_VALUE, the seek position is calculated inside the function
     * @return UiEvent object
     */
    @JvmStatic
    fun newUiEvent(song: Song, capturedUiAction: UiEventType, context: Context, seekPosition: Long = Long.MAX_VALUE): UiEvent {
        val currentTimeMS = System.currentTimeMillis()
        val nanoTime = System.nanoTime()
        val songData = SongData(song, context)
        val audioData = AudioDeviceUtils.getAudioData(context)
        var seekPositionMs = seekPosition
        if (seekPositionMs == Long.MAX_VALUE) {
            seekPositionMs = MusicServiceConnectionUtils.getPosition()
        }
        return UiEvent(uiEventType = capturedUiAction, currentTimeMs = currentTimeMS,
                nanoTime = nanoTime, seekPositionMs = seekPositionMs, song = songData,
                audioData = audioData)
    }

    /**
     *  @see newUiEvent(Song, UiEventType, Context, Long)
     */

    @JvmStatic
    fun newUiEvent(song: Song, capturedUiAction: UiEventType, context: Context): UiEvent{
        return newUiEvent(song, capturedUiAction, context, Long.MAX_VALUE)
    }

    /**
     * Function to populate the UiEvent class with Album data when the overflow button is clicked
     * @param album - The Album for which the action was performed
     * @param capturedUiAction = The action that was captured.
     * @return UiEvent object
     */
    @JvmStatic
    fun newUiAlbumEvent(album: Album, capturedUiAction: UiEventType): UiEvent {
        val currentTimeMS = System.currentTimeMillis()
        val nanoTime = System.nanoTime()
        val albumData = AlbumData(album)
        val audioData = AudioDeviceUtils.getAudioData(ShuttleApplication.get())
        return UiEvent(uiEventType = capturedUiAction, currentTimeMs = currentTimeMS,
                nanoTime = nanoTime, album = albumData, audioData = audioData)
    }

    /**
     * Function to populate the UiEvent class with AlbumArtist data when the overflow button is clicked
     * @param albumArtist - The Album Artist for which the action was performed
     * @param capturedUiAction - The action that was captured.
     * @return UiEvent object
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
        val audioData = AudioDeviceUtils.getAudioData(ShuttleApplication.get())
        return UiEvent(uiEventType = capturedUiAction, currentTimeMs = currentTimeMS,
                nanoTime = nanoTime, albumArtist = albumArtistData, audioData = audioData)
    }

    /**
     * Function to populate the UiEvent class with Genre data when the overflow button is clicked
     * @param genre - The Genre for which the action was performed.
     * @param capturedUiAction - The action that was captured
     * @return UiEvent object
     */
    @JvmStatic
    fun newUiGenreEvent(genre: Genre, capturedUiAction: UiEventType): UiEvent {
        val currentTimeMS = System.currentTimeMillis()
        val nanoTime = System.nanoTime()
        val genreData = GenreData(genre)
        val audioData = AudioDeviceUtils.getAudioData(ShuttleApplication.get())
        return UiEvent(uiEventType = capturedUiAction, currentTimeMs = currentTimeMS,
                nanoTime = nanoTime, genre = genreData, audioData = audioData)
    }

    @JvmStatic
    fun newUiVolumeEvent(capturedUiAction: UiEventType, context: Context): UiEvent{
        val currentTimeMS = System.currentTimeMillis()
        val nanoTime = System.nanoTime()
        val audioData = AudioDeviceUtils.getAudioData(context)
        return UiEvent(uiEventType = capturedUiAction, currentTimeMs = currentTimeMS,
                nanoTime = nanoTime, audioData = audioData)
    }
}