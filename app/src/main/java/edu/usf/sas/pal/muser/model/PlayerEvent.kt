package edu.usf.sas.pal.muser.model

data class PlayerEvent
(
    val playerEventType: PlayerEventType,
    override val currentTimeMs: Long,
    override val nanoTime: Long,
    override val startTime: Long,
    override val elapsedTime: Long,
    override val song: SongData
) : Event
