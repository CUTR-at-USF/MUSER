package com.simplecity.amp_library.utils.menu.queue

import android.content.Context
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.Toolbar
import com.simplecity.amp_library.R
import com.simplecity.amp_library.ui.screens.queue.QueueItem
import com.simplecity.amp_library.ui.screens.queue.toSongs
import com.simplecity.amp_library.utils.menu.song.SongMenuUtils
import com.simplecity.amp_library.utils.playlists.PlaylistMenuHelper
import io.reactivex.Single

object QueueMenuUtils {

    fun setupQueueSongMenu(menu: PopupMenu, playlistMenuHelper: PlaylistMenuHelper) {
        menu.inflate(R.menu.menu_queue_song)

        // Add playlist menu
        val subMenu = menu.menu.findItem(R.id.addToPlaylist).subMenu
        playlistMenuHelper.createPlaylistMenu(subMenu)
    }

    fun getQueueMenuClickListener(context: Context, queueItems: Single<List<QueueItem>>, callbacks: QueueMenuCallbacks, closeCab: () -> Unit): Toolbar.OnMenuItemClickListener {
        return Toolbar.OnMenuItemClickListener { item ->

            if (SongMenuUtils.getSongMenuClickListener(context, queueItems.map { it.toSongs() }, callbacks).onMenuItemClick(item)) {
                closeCab()
            } else {
                when (item.itemId) {
                    R.id.queue_remove -> {
                        callbacks.removeQueueItems(queueItems)
                        closeCab()
                        return@OnMenuItemClickListener true
                    }
                }
            }
            false
        }
    }

    fun getQueueMenuClickListener(context: Context, queueItem: QueueItem, callbacks: QueueMenuCallbacks): PopupMenu.OnMenuItemClickListener {
        return PopupMenu.OnMenuItemClickListener { item ->

            when (item.itemId) {
                R.id.playNext -> {
                    callbacks.moveToNext(queueItem)
                    return@OnMenuItemClickListener true
                }
                R.id.remove -> {
                    callbacks.removeQueueItem(queueItem)
                    return@OnMenuItemClickListener true
                }
            }

            SongMenuUtils.getSongMenuClickListener(context, queueItem.song, callbacks).onMenuItemClick(item)
        }
    }
}
