package edu.usf.sas.pal.muser.firebase.models


/**
 * A model class that holds the information about the song that is currently being played by the
 * user.
 */
data class SongData

/**
 * [id] - The id of the track.
 * [name] - Name of the track.
 * [artistName] - Name of the Artist who composed the track.
 * [albumName] - Name of the album that the current track belongs to.
 * [duration] - The length of the track.
 * [dateAdded] - The date on which the track was added.
 * [year] - The year during which the track was released.
 * [playCount] - The number of times the user played this track.
 * [sampleRateLabel] - The sample rate of the song.
 * [formatLabel] - The format of the song. (mp3 or Flac etc.)
 */
(val id: Long,
 val name: String,
 val artistName: String,
 val albumName: String,
 val duration: Long,
 val dateAdded: Int,
 val year: Int,
 val playCount: Int,
 val sampleRateLabel: String,
 val formatLabel: String
)