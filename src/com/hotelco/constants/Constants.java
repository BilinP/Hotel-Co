package com.hotelco.constants;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * Contains all constants.
 * @author Daniel Schwartz, John Lee
 */
public final class Constants {
    /**
     * Standard check in time
     */
    public static final Integer CHECK_IN_TIME = 13;
    /**
     * Standard check out time
     */
    public static final Integer CHECK_OUT_TIME = 14;
    /**
     * Maximum capacity of a double room. 
     */
    public static final Map<RoomType,Integer> Capacities =
        new HashMap<RoomType,Integer>();
    /**
     * Room capacities for each RoomType
     */
    public static final Map<RoomType, Integer> CAPACITIES = Stream.of(
  new AbstractMap.SimpleImmutableEntry<>(RoomType.DBL, 2),    
  new AbstractMap.SimpleImmutableEntry<>(RoomType.QUEEN, 4),
  new AbstractMap.SimpleImmutableEntry<>(RoomType.KING, 4),
  new AbstractMap.SimpleImmutableEntry<>(RoomType.SUITE, 6))
  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    /**
     * Maximum capacity of any room in the hotel
     */ 
    public static final Integer MAX_CAP = 6;
    /**
     * Interval for closing and reopening database connection
     */
    public static final Integer RECONNECT_INTERVAL = 20 * MsTime.MINUTE;
    /**
     * Interval for attempting to close and reopen database connection after
     * failed attempt to do so
     */
    public static final Integer RETRY_INTERVAL = 30 * MsTime.SECOND;
    /**
     * Tax rate multiplier on the weekends
     */
    public static final BigDecimal WEEKEND_MULTIPLIER = new BigDecimal("1.2");
    /**
     * Flavor text for the double room
     */
    public static final String DblRoomFlavor = "\t- Free WiFi\n\n" +
                                                "\t- Free Self Parking\n\n" +
                                                "\t- Shower\n\n" +
                                                "\t- 2 Double Beds\n\n" +
                                                "\t- 500 sqft";
    /**
     * Flavor text for the queen room
     */
    public static final String QueenRoomFlavor = "\t- Free WiFi\n\n" +
                                                "\t- Free Self Parking\n\n" +
                                                "\t- Shower/Tub Combination\n\n" +
                                                "\t- 2 Queen Beds\n\n" +
                                                "\t- 700 sqft";
    /**
     * Flavor text for the king room
     */
    public static final String KingRoomFlavor = "\t- Free WiFi\n\n" +
                                                "\t- Free Self Parking\n\n" +
                                                "\t- Shower/Tub Combination\n\n" +
                                                "\t- Foldable Couch\n\n" +
                                                "\t- 1 King Bed\n\n" +
                                                "\t- 950 sqft";
    /**
     * Flavor text for the suite room
     */
    public static final String SuiteRoomFlavor = "\t- Free WiFi\n\n" +
                                                "\t- Free Self Parking\n\n" +
                                                "\t- Shower/Tub Combination\n\n" +
                                                "\t- Panoramic View\n\n" +
                                                "\t- 1 King Beds\n\n" +
                                                "\t- 1300 sqft";
    /**
     * In seconds, the amount of time the system waits for automatic logout,
     * when there is no input.
     */
    public static final Integer IDLE_TIMEOUT = 30;

    /**
     * In seconds, the the amount of time the screen saver waits before starting
     * after no input.
     */
    public static final Integer SCREENSAVER_TIMEOUT = 5;
}
