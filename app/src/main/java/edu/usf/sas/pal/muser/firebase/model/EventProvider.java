package edu.usf.sas.pal.muser.firebase.model;

/**
 *  The provider enumerator that stores different types of action that can be performed by the user
 *  on a sound track.
 */
public enum EventProvider {

        EVENT_PLAY("Play"),
        EVENT_PLAY_MANUAL("PLAY_MANUAL"),
        EVENT_PAUSE("Pause"),
        EVENT_SKIP("Skip"),
        EVENT_REPEAT("Repeat"),
        EVENT_SEEK("Seek");

        private String event;

        EventProvider(String event) {
            this.event = event;
        }

        public String getEvent(){
            return event;
        }
}
