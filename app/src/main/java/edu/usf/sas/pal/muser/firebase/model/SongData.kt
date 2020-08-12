package edu.usf.sas.pal.muser.firebase.model


/**
 * A model class that holds the information about the song that is currently being played by the
 * user.
 */
data class SongData

/**
 * [id] - The id of the track.
 * [name] - Name of the track.
 * [artistID] - The id of the artist.
 * [artistName] - Name of the Artist who composed the track.
 * [albumID] - The id of the album.
 * [albumName] - Name of the album that the current track belongs to.
 * [playlistID] - The id of the playlist that track belongs to.
 * [playlistPlayOrder] - The queue number of the song in a playlist.
 * [discNumber] - The disc number that the track belongs to.
 * [bitrateLabel] - The bitrate of the song.
 * [fileSizeLabel] - The file size of the song.
 * [isPodCast] - Boolean value to determine if the current audio file is a pod cast or not.
 * [duration] - The length of the track.
 * [dateAdded] - The date on which the track was added.
 * [year] - The year during which the track was released.
 * [playCount] - The number of times the user played this track.
 * [sampleRateLabel] - The sample rate of the song.
 * [formatLabel] - The format of the song. (mp3 or Flac etc.)
 */
(val id: Long,
 val name: String,
 val artistID: Long,
 val artistName: String,
 val albumID: Long,
 val albumName: String,
 val playlistID: Long,
 val playlistPlayOrder: Long?,
 val lastPlayed: Long?,
 val track: Int?,
 val discNumber: Int?,
 val bitrateLabel: String?,
 val fileSizeLabel: String?,
 val isPodCast: Boolean?,
 val duration: Long?,
 val dateAdded: Int?,
 val year: Int?,
 val path: String?,
 val playCount: Int?,
 val sampleRateLabel: String?,
 val formatLabel: String?
)