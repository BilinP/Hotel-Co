package com.hotelco.developer;

import com.hotelco.constants.MsTime;

public class Settings {

    /**
     * Whether emails are actually sent
     */
    public static final Boolean CAN_EMAIL = true;
    /**
     * Whether to enter developer mode or not
     */
    public static final Boolean DEV_MODE = false;
    /**
     * Whether to run main or not.
     */
    public static final Boolean RUN_MAIN = true;
    /**
     * Whether or not to run the application in full screen
     */
    public static final Boolean FULL_SCREEN = true;
    /**
     * In seconds, the the amount of time the screen saver waits before starting
     * after no input.
     */
    public static final Integer SCREENSAVER_TIMEOUT = 100;
    /**
     * In seconds, the amount of time the system waits for automatic logout,
     * when there is no input.
     */
    public static final Integer IDLE_TIMEOUT = 30;
    /**
     * Interval for closing and reopening database connection
     */
    public static final Integer RECONNECT_INTERVAL = 20 * MsTime.MINUTE;
    /**
     * Interval for attempting to close and reopen database connection after
     * failed attempt to do so
     */
    public static final Integer RETRY_INTERVAL = 30 * MsTime.SECOND;    
}
