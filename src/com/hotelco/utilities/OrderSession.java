package com.hotelco.utilities;

import java.time.LocalDate;

import com.hotelco.constants.RoomType;

public class OrderSession {
    private static LocalDate startDate;

    private static LocalDate endDate;

    private static int guests;

    private static RoomType roomType;

    public static RoomType getRoomType() {
        return roomType;
    }

    public static void setRoomType(RoomType roomType) {
        OrderSession.roomType = roomType;
    }

    public static LocalDate getStartDate() {
        return startDate;
    }

    public static void setStartDate(LocalDate startDate) {
        OrderSession.startDate = startDate;
    }

    public static LocalDate getEndDate() {
        return endDate;
    }

    public static void setEndDate(LocalDate endDate) {
        OrderSession.endDate = endDate;
    }

    public static int getGuests() {
        return guests;
    }

    public static void setGuests(int guests) {
        OrderSession.guests = guests;
    }
}