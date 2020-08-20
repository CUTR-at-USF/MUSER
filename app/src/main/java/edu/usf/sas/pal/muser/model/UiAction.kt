package edu.usf.sas.pal.muser.model

data class UiAction

(
    val uiActionType: UiActionType,
    override val currentTimeMs: Long,
    override val nanoTime: Long,
    override val startTime: Long,
    override val elapsedTime: Long,
    override val song: SongData
) : Event