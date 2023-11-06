package com.hotelco.constants;
/**
 * Contains all constants
 */

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.hotelco.entities.Room;

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

    public static final BigDecimal WEEKEND_MULTIPLIER = new BigDecimal("1.2");
}
