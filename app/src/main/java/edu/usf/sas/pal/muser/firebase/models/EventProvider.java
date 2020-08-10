package edu.usf.sas.pal.muser.firebase.models;

/**
 *  The provider class that stores different types of action that can be performed by the user
 *  on a sound track.
 */
public interface EventProvider {
    @interface EventType{
        String EVENT_PLAY = "Play";
        String EVENT_PAUSE = "Pause";
        String EVENT_SKIP = "Skip";
        String EVENT_REPEAT = "Repeat";
        String EVENT_SEEK= "Seek";
    }

}
