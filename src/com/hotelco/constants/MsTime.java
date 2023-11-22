package com.hotelco.constants;

/**
 * Defines constants representing various time units in milliseconds. 
 * It serves as a convenient reference for converting between different time 
 * intervals and their equivalent values in milliseconds.
 * @author      Daniel Schwartz
 * @version     %I%, %G%
 */
public class MsTime{
    /**
    * A millisecond, in milliseconds
    */
    public static final Integer MILLISECOND = 1;
    /**
     * A second, in milliseconds
     */
    public static final Integer SECOND = 1000 * MILLISECOND;
    /**
     * A minute, in milliseconds
     */
    public static final Integer MINUTE = 60 * SECOND;
    /**
     * An hour, in milliseconds
     */
    public static final Integer HOUR = 60 * MINUTE;
    /**
     * A day, in milliseconds
     */
    public static final Integer DAY = 24 * HOUR;
}