package edu.usf.sas.pal.muser.firebase.model

/**
 * A model class that stores the actions performed on the song track. The actions like play, pause,
 * skip, repeat and seek are stored along with the nested [SongData] class.
 */

data class Event

/**
 * [event] - The event attribute includes events like play, pause, skip, repeat and seek.
 * [currentTimeMs] - Stores the timestamp of the action recorded.
 * [nanoTime] - Stores the  value of the running JVM's time source in nanoseconds.
 * [song] - Song on which the action was performed.
 */
(val event:String,
 val currentTimeMs: Long,
 val nanoTime: Long,
 val startTime: Long?,
 val elapsedTime: Long?,
 val song: SongData
)