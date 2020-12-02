package edu.usf.sas.pal.muser.model

data class VolumeData
(val currentVolumeLevel: Int = Int.MIN_VALUE,
 val volumeMax: Int = Int.MAX_VALUE,
 val volumeMin: Int? = Int.MIN_VALUE,
 val volumeDB: Float? = Float.NaN
)