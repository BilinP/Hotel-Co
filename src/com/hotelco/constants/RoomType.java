package com.hotelco.constants;


public enum RoomType {
    DBL("Double"),
    QUEEN("Queen"),
    KING("King"),
    SUITE("Suite");

    private final String prettyName;

    RoomType(String prettyName) {
        this.prettyName = prettyName;
    }

    public String toPrettyString() {
        return prettyName;
    }
}