package edu.usf.sas.pal.muser.model

/**
 * A model data class that stores the event occurred while playing a song and implements the
 * Event interface.
 */
data class PlayerEvent

/**
 * [playerEventType] - The enum object of the event occurred.
 */
(
    val playerEventType: PlayerEventType,
    override val currentTimeMs: Long,
    override val nanoTime: Long,
    override val startTime: Long,
    override val elapsedTime: Long,
    override val song: SongData
) : Event
