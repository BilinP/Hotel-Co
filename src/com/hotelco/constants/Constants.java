package com.hotelco.constants;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * Contains all constants
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
     * Maximum capacity of a double room
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
    public static final Integer RECONNECT_INTERVAL = 1200000;
    /**
     * Interval for attempting to close and reopen database connection after
     * failed attempt to do so
     */
    public static final Integer RETRY_INTERVAL = 30000;
    /**
     * Tax rate multiplier on the weekends
     */
    public static final BigDecimal WEEKEND_MULTIPLIER = new BigDecimal("1.2");
}
