package edu.usf.sas.pal.muser.firebase.models;

public interface SongActionProvider {
    @interface ActionType{
        String ACTION_PLAY = "Play";
        String ACTION_PAUSE = "Pause";
        String ACTION_SKIP = "Skip";
        String ACTION_REPEAT = "Repeat";
        String ACTION_SEEK_TO = "SeekTo";
    }

}
