package edu.usf.sas.pal.muser.model

/**
 *  A model class that stores different types of events actions performed on the song and implements
 *  Event interface.
 */
data class UiEvent

/**
 * [uiEventType] - The enum object for the type of action performed.
 */
(val uiEventType: UiEventType,
 override val currentTimeMs: Long,
 override val nanoTime: Long,
 override val song: SongData?
) : Event