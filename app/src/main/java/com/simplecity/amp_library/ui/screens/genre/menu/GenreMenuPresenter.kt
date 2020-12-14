package com.simplecity.amp_library.ui.screens.genre.menu

import android.content.Context
import com.simplecity.amp_library.ShuttleApplication
import com.simplecity.amp_library.model.Genre
import com.simplecity.amp_library.model.Playlist
import com.simplecity.amp_library.model.Song
import com.simplecity.amp_library.playback.MediaManager
import com.simplecity.amp_library.ui.common.Presenter
import com.simplecity.amp_library.ui.screens.album.menu.AlbumMenuPresenter
import com.simplecity.amp_library.utils.LogUtils
import com.simplecity.amp_library.utils.extensions.getSongs
import com.simplecity.amp_library.utils.playlists.PlaylistManager
import edu.usf.sas.pal.muser.model.UiEventType
import edu.usf.sas.pal.muser.util.EventUtils
import edu.usf.sas.pal.muser.util.FirebaseIOUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GenreMenuPresenter @Inject constructor(
    private val context: Context,
    private val mediaManager: MediaManager,
    private val playlistManager: PlaylistManager
) : Presenter<GenreMenuContract.View>(), GenreMenuContract.Presenter {
    override fun createPlaylist(genre: Genre) {
        getSongs(genre) { songs ->
            view?.presentCreatePlaylistDialog(songs)
        }
    }

    override fun addToPlaylist(context: Context, playlist: Playlist, genre: Genre) {
        getSongs(genre) { songs ->
            if (playlist.type == Playlist.Type.FAVORITES) {
                songs.forEach {
                    newUiEvent(it)
                }
            }
            playlistManager.addToPlaylist(context, playlist, songs) { numSongs ->
                view?.onSongsAddedToPlaylist(playlist, numSongs)
            }
        }
        newUiGenreEvent(genre, UiEventType.ADD_TO_PLAYLIST_GENRE)
    }

    override fun addToQueue(genre: Genre) {
        getSongs(genre) { songs ->
            mediaManager.addToQueue(songs) { numSongs ->
                view?.onSongsAddedToQueue(numSongs)
            }
        }
    }

    override fun play(genre: Genre) {
        newUiGenreEvent(genre, UiEventType.PLAY_GENRE)
        mediaManager.playAll(genre.getSongs(context)) {
            view?.onPlaybackFailed()
        }
    }

    override fun playNext(genre: Genre) {
        newUiGenreEvent(genre, UiEventType.PLAY_GENRE_NEXT)
        getSongs(genre) { songs ->
            mediaManager.playNext(songs) { numSongs ->
                view?.onSongsAddedToQueue(numSongs)
            }
        }
    }

    private fun getSongs(genre: Genre, onSuccess: (songs: List<Song>) -> Unit) {
        addDisposable(
            genre.getSongs(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    onSuccess,
                    { error -> LogUtils.logException(AlbumMenuPresenter.TAG, "Failed to retrieve songs", error) }
                )
        )
    }
    private fun newUiGenreEvent(genre: Genre, uiEventType: UiEventType){
        val uiEvent = EventUtils.newUiGenreEvent(genre, uiEventType)
        FirebaseIOUtils.saveUiEvent(uiEvent)
    }

    private fun newUiEvent(song: Song){
        val uiEvent = EventUtils.newUiEvent(song, UiEventType.FAVORITE, ShuttleApplication.get())
        FirebaseIOUtils.saveUiEvent(uiEvent)
    }
}
