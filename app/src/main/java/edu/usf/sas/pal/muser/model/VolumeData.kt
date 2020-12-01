package edu.usf.sas.pal.muser.model

data class VolumeData
(var currentVolumeLevel: Int = 0,
 var volumeMax: Int = 0,
 var volumeMin: Int = 0,
 var volumeDB: Float = 0F
)