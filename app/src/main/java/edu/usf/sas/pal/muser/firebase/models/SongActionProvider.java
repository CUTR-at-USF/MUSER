package edu.usf.sas.pal.muser.firebase.models;

/**
 *  The provider class that stores different types of action that can be performed by the user
 *  on a sound track.
 */
public interface SongActionProvider {
    @interface ActionType{
        String ACTION_PLAY = "Play";
        String ACTION_PAUSE = "Pause";
        String ACTION_SKIP = "Skip";
        String ACTION_REPEAT = "Repeat";
        String ACTION_SEEK= "Seek";
    }

}
