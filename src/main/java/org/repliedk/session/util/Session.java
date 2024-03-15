package org.repliedk.session.util;

import org.repliedk.session.PlayerSession;

public class Session {

    public static String STARTING = "starting_timer";
    
    // Beautiful messages in English
    public static String WELCOME_MESSAGE = "Welcome to the session!";
    public static String GOODBYE_MESSAGE = "Goodbye, see you next time!";
    public static String SUCCESS_MESSAGE = "Operation successful!";
    public static String ERROR_MESSAGE = "Oops, something went wrong!";
    
    public static boolean isStartingTimer(PlayerSession player) {
        return player.getTimer(STARTING) != null;
    }

}