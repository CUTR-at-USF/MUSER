package edu.usf.sas.pal.muser.model

/**
 * A model class that implements the Event interface and stores music player events that impact the
 * music the user hears. The timestamp and type of event, song position, and song metadata is captured
 */
data class PlayerEvent

/**
 * [playerEventType] - The enum object of the event occurred.
 */
(
    val playerEventType: PlayerEventType,
    override val currentTimeMs: Long,
    override val nanoTime: Long,
    override val seekPositionMs: Long,
    override val song: SongData
) : Event
