package edu.usf.sas.pal.muser.model

/**
 * A model interface that stores the actions performed on the song track. The actions like play, pause,
 * skip, repeat and seek are stored along with the nested [SongData] class.
 */

interface Event

/**
 * [currentTimeMs] - Stores the timestamp of the action recorded.
 * [nanoTime] - Stores the  value of the running JVM's time source in nanoseconds.
 * [seekPositionMs] - Stores the seek position of the track.
 * [song] - Song on which the action was performed.
 */
{
    val currentTimeMs: Long
    val nanoTime: Long
    val seekPositionMs: Long
    val song: SongData?
}