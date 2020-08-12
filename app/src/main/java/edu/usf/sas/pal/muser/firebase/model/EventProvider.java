package edu.usf.sas.pal.muser.firebase.model;

import org.jetbrains.annotations.NotNull;

/**
 *  The provider enumerator that stores different types of action that can be performed by the user
 *  on a sound track.
 */
public enum EventProvider {

        PLAY("PLAY"),
        PLAY_MANUAL("PLAY_MANUAL"),
        PAUSE("PAUSE"),
        PAUSE_MANUAL("PAUSE_MANUAL"),
        SKIP("SKIP"),
        REPEAT("REPEAT"),
        SEEK("SEEK");

        private String event;

        EventProvider(String event) {
            this.event = event;
        }

        @NotNull
        public String toString(){
            return event;
        }
}
