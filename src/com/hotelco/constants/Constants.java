package com.hotelco.constants;
/**
 * Contains all constants
 */

import java.math.BigDecimal;

public final class Constants {
    /**
     * Standard check in time
     */
    public static final Integer CHECK_IN_TIME = 1;
    /**
     * Standard check out time
     */
    public static final Integer CHECK_OUT_TIME = 2;
    /**
     * Maximum capacity of a double room
     */
    public static final Integer DBL_CAP = 2;
    /**
     * Maximum capacity of a queen room
     */
    public static final Integer QUEEN_CAP = 4;
    /**
     * Maximum capacity of a king room
     */
    public static final Integer KING_CAP = 4;
    /**
     * Maximum capacity of a suite room
     */
    public static final Integer SUITE_CAP = 6;
    /**
     * Maximum capacity of any room in the hotel
     */ 
    public static final Integer MAX_CAP = 6;

    public static final BigDecimal WEEKEND_MULTIPLIER = new BigDecimal("1.2");
}
