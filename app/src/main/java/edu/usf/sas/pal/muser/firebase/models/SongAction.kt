package edu.usf.sas.pal.muser.firebase.models

/**
 * A model class that stores the actions performed on the song track. The actions like play, pause,
 * skip, repeat and seek are stored along with the nested [SongData] class.
 */

data class SongAction

/**
 * [action] - The action attribute includes The actions like play, pause, skip, repeat and seek.
 * [currentTimeMs] - Stores the timestamp of the action recorded.
 * [nanoTime] - Stores the  value of the running JVM's time source in nanoseconds.
 * [song] - Song on which the action was performed.
 */
(val action:String,
 val currentTimeMs: Long,
 val nanoTime: Long,
 val song: SongData
)