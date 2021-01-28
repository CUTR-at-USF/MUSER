package com.simplecity.amp_library.ui.screens.album.menu

import android.content.Context
import com.simplecity.amp_library.ShuttleApplication
import com.simplecity.amp_library.data.Repository
import com.simplecity.amp_library.model.AlbumArtist
import com.simplecity.amp_library.model.Playlist
import com.simplecity.amp_library.model.Song
import com.simplecity.amp_library.playback.MediaManager
import com.simplecity.amp_library.ui.common.Presenter
import com.simplecity.amp_library.ui.screens.album.menu.AlbumArtistMenuContract.View
import com.simplecity.amp_library.ui.screens.drawer.NavigationEventRelay
import com.simplecity.amp_library.ui.screens.drawer.NavigationEventRelay.NavigationEvent
import com.simplecity.amp_library.ui.screens.drawer.NavigationEventRelay.NavigationEvent.Type
import com.simplecity.amp_library.ui.screens.songs.menu.SongMenuPresenter
import com.simplecity.amp_library.utils.LogUtils
import com.simplecity.amp_library.utils.Operators
import com.simplecity.amp_library.utils.extensions.getSongs
import com.simplecity.amp_library.utils.playlists.PlaylistManager
import com.simplecity.amp_library.utils.sorting.SortManager
import edu.usf.sas.pal.muser.model.UiEventType
import edu.usf.sas.pal.muser.util.EventUtils
import edu.usf.sas.pal.muser.util.FirebaseIOUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AlbumArtistMenuPresenter @Inject constructor(
    private val playlistManager: PlaylistManager,
    private val songsRepository: Repository.SongsRepository,
    private val mediaManager: MediaManager,
    private val blacklistRepository: Repository.BlacklistRepository,
    private val navigationEventRelay: NavigationEventRelay,
    private val sortManager: SortManager

) : Presenter<View>(), AlbumArtistMenuContract.Presenter {

    override fun createArtistsPlaylist(albumArtists: List<AlbumArtist>) {
        getSongs(albumArtists) { songs ->
            view?.presentCreatePlaylistDialog(songs)
        }
    }

    override fun addArtistsToPlaylist(context: Context, playlist: Playlist, albumArtists: List<AlbumArtist>) {
        getSongs(albumArtists) { songs ->
            if (playlist.type == Playlist.Type.FAVORITES) {
                songs.forEach {
                    newUiEvent(it)
                }
            }
            playlistManager.addToPlaylist(context, playlist, songs) { numSongs ->
                view?.onSongsAddedToPlaylist(playlist, numSongs)
            }
        }
        albumArtists.forEach {
            newUiAlbumArtistEvent(it, UiEventType.ADD_TO_PLAYLIST_ALBUM_ARTIST)
        }
    }

    override fun addArtistsToQueue(albumArtists: List<AlbumArtist>) {
        getSongs(albumArtists) { songs ->
            mediaManager.addToQueue(songs) { numSongs ->
                view?.onSongsAddedToQueue(numSongs)
            }
        }
        albumArtists.forEach {
            newUiAlbumArtistEvent(it, UiEventType.ADD_TO_QUEUE_ALBUM_ARTIST)
        }
    }

    override fun playArtistsNext(albumArtists: List<AlbumArtist>) {
        getSongs(albumArtists) { songs ->
            mediaManager.playNext(songs) { numSongs ->
                view?.onSongsAddedToQueue(numSongs)
            }
        }
        albumArtists.forEach{
            newUiAlbumArtistEvent(it, UiEventType.PLAY_ALBUM_ARTIST_NEXT)
        }
    }

    override fun play(albumArtist: AlbumArtist) {
        mediaManager.playAll(albumArtist.getSongsSingle(songsRepository)) { view?.onPlaybackFailed() }
        newUiAlbumArtistEvent(albumArtist, UiEventType.PLAY_ALBUM_ARTIST)
    }

    override fun editTags(albumArtist: AlbumArtist) {
        view?.presentTagEditorDialog(albumArtist)
    }

    override fun albumArtistInfo(albumArtist: AlbumArtist) {
        newUiAlbumArtistEvent(albumArtist, UiEventType.ALBUM_ARTIST_BIOGRAPHY)
        view?.presentAlbumArtistInfoDialog(albumArtist)
    }

    override fun editArtwork(albumArtist: AlbumArtist) {
        view?.presentArtworkEditorDialog(albumArtist)
    }

    override fun blacklistArtists(albumArtists: List<AlbumArtist>) {
        getSongs(albumArtists) { songs -> blacklistRepository.addAllSongs(songs) }
    }

    override fun deleteArtists(albumArtists: List<AlbumArtist>) {
        view?.presentArtistDeleteDialog(albumArtists)
    }

    override fun goToArtist(albumArtist: AlbumArtist) {
        navigationEventRelay.sendEvent(NavigationEvent(Type.GO_TO_ARTIST, albumArtist, true))
    }

    override fun albumShuffle(albumArtist: AlbumArtist) {
        mediaManager.playAll(albumArtist.getSongs(songsRepository)
            .map { songs -> Operators.albumShuffleSongs(songs, sortManager) }) {
            view?.onPlaybackFailed()
            Unit
        }
        newUiAlbumArtistEvent(albumArtist, UiEventType.ARTIST_ALBUM_SHUFFLE)
    }

    override fun <T> transform(src: Single<List<T>>, dst: (List<T>) -> Unit) {
        addDisposable(
            src
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { items -> dst(items) },
                    { error -> LogUtils.logException(SongMenuPresenter.TAG, "Failed to transform src single", error) }
                )
        )
    }

    private fun getSongs(albumArtists: List<AlbumArtist>, onSuccess: (songs: List<Song>) -> Unit) {
        addDisposable(
            albumArtists.getSongs(songsRepository)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    onSuccess,
                    { error -> LogUtils.logException(TAG, "Failed to retrieve songs", error) }
                )
        )
    }

    companion object {
        const val TAG = "AlbumMenuContract"
    }

    private fun newUiAlbumArtistEvent(albumArtist: AlbumArtist, uiEventType: UiEventType){
        val uiEvent = EventUtils.newUiAlbumArtistEvent(albumArtist, uiEventType)
        FirebaseIOUtils.saveUiEvent(uiEvent)
    }

    private fun newUiEvent(song: Song){
        val uiEvent = EventUtils.newUiEvent(song, UiEventType.FAVORITE, ShuttleApplication.get())
        FirebaseIOUtils.saveUiEvent(uiEvent)
    }
}