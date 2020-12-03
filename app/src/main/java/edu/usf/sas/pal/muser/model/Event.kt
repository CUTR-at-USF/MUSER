package edu.usf.sas.pal.muser.model

/**
 * A model interface that defines timestamps and song position, along with the song, which can be
 * extended to record various types of events.
*/

interface Event

/**
 * [currentTimeMs] - Stores the timestamp of the action recorded.
 * [nanoTime] - Stores the  value of the running JVM's time source in nanoseconds.
 * [seekPositionMs] - Stores the seek position of the track.
 * [song] - Song on which the action was performed.
 * [audioData] - Volume data of the phone.
 */
{
    val currentTimeMs: Long
    val nanoTime: Long
    val seekPositionMs: Long
    val song: SongData?
    val audioData: AudioData?
}