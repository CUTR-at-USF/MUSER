package com.simplecity.amp_library.ui.screens.album.menu

import android.content.Context
import com.simplecity.amp_library.ShuttleApplication
import com.simplecity.amp_library.data.Repository
import com.simplecity.amp_library.model.Album
import com.simplecity.amp_library.model.Playlist
import com.simplecity.amp_library.model.Song
import com.simplecity.amp_library.playback.MediaManager
import com.simplecity.amp_library.ui.common.Presenter
import com.simplecity.amp_library.ui.screens.album.menu.AlbumMenuContract.View
import com.simplecity.amp_library.ui.screens.drawer.NavigationEventRelay
import com.simplecity.amp_library.ui.screens.drawer.NavigationEventRelay.NavigationEvent
import com.simplecity.amp_library.ui.screens.drawer.NavigationEventRelay.NavigationEvent.Type
import com.simplecity.amp_library.ui.screens.songs.menu.SongMenuPresenter
import com.simplecity.amp_library.utils.LogUtils
import com.simplecity.amp_library.utils.extensions.getSongs
import com.simplecity.amp_library.utils.extensions.getSongsSingle
import com.simplecity.amp_library.utils.playlists.PlaylistManager
import edu.usf.sas.pal.muser.model.UiEventType
import edu.usf.sas.pal.muser.util.EventUtils
import edu.usf.sas.pal.muser.util.FirebaseIOUtils
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AlbumMenuPresenter @Inject constructor(
    private val playlistManager: PlaylistManager,
    private val songsRepository: Repository.SongsRepository,
    private val mediaManager: MediaManager,
    private val blacklistRepository: Repository.BlacklistRepository,
    private val albumArtistsRepository: Repository.AlbumArtistsRepository,
    private val navigationEventRelay: NavigationEventRelay
) : Presenter<View>(), AlbumMenuContract.Presenter {

    override fun createPlaylistFromAlbums(albums: List<Album>) {
        getSongs(albums) { songs ->
            view?.presentCreatePlaylistDialog(songs)
        }
    }

    override fun addAlbumsToPlaylist(context: Context, playlist: Playlist, albums: List<Album>) {
        getSongs(albums) { songs ->
            if (playlist.type == Playlist.Type.FAVORITES) {
                songs.forEach {
                    newUiEvent(it)
                }
            }
            playlistManager.addToPlaylist(context, playlist, songs) { numSongs ->
                view?.onSongsAddedToPlaylist(playlist, numSongs)
            }
        }
        albums.forEach {
            newUiAlbumEvent(it, UiEventType.ADD_TO_PLAYLIST_ALBUM)
        }
    }

    override fun addAlbumsToQueue(albums: List<Album>) {
        getSongs(albums) { songs ->
            mediaManager.addToQueue(songs) { numSongs ->
                view?.onSongsAddedToQueue(numSongs)
            }
        }
        albums.forEach {
            newUiAlbumEvent(it, UiEventType.ADD_TO_QUEUE_ALBUM)
        }
    }

    override fun playAlbumsNext(albums: List<Album>) {
        getSongs(albums) { songs ->
            mediaManager.playNext(songs) { numSongs ->
                view?.onSongsAddedToQueue(numSongs)
            }
        }
        albums.forEach{
            newUiAlbumEvent(it, UiEventType.PLAY_ALBUM_NEXT)
        }
    }

    override fun play(album: Album) {
        mediaManager.playAll(album.getSongsSingle(songsRepository)) { view?.onPlaybackFailed() }
        newUiAlbumEvent(album, UiEventType.PLAY_ALBUM)
    }

    override fun editTags(album: Album) {
        view?.presentTagEditorDialog(album)
    }

    override fun albumInfo(album: Album) {
        view?.presentAlbumInfoDialog(album)
    }

    override fun editArtwork(album: Album) {
        view?.presentArtworkEditorDialog(album)
    }

    override fun blacklistAlbums(albums: List<Album>) {
        getSongs(albums) { songs -> blacklistRepository.addAllSongs(songs) }
    }

    override fun deleteAlbums(albums: List<Album>) {
        view?.presentDeleteAlbumsDialog(albums)
    }

    override fun goToArtist(album: Album) {
        addDisposable(albumArtistsRepository.getAlbumArtists()
            .first(emptyList())
            .flatMapObservable { Observable.fromIterable(it) }
            .filter { albumArtist -> albumArtist.name == album.albumArtist.name && albumArtist.albums.contains(album) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { albumArtist -> navigationEventRelay.sendEvent(NavigationEvent(Type.GO_TO_ARTIST, albumArtist, true)) },
                { error -> LogUtils.logException(com.simplecity.amp_library.ui.screens.songs.menu.SongMenuPresenter.TAG, "Failed to retrieve album artist", error) }
            ))
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

    private fun getSongs(albums: List<Album>, onSuccess: (songs: List<Song>) -> Unit) {
        addDisposable(
            albums.getSongs(songsRepository)
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

    private fun newUiAlbumEvent(album: Album, uiEventType: UiEventType){
            val uiEvent = EventUtils.newUiAlbumEvent(album, uiEventType)
            FirebaseIOUtils.saveUiEvent(uiEvent)
    }

    private fun newUiEvent(song: Song){
        val uiEvent = EventUtils.newUiEvent(song, UiEventType.FAVORITE, ShuttleApplication.get())
        FirebaseIOUtils.saveUiEvent(uiEvent)
    }
}