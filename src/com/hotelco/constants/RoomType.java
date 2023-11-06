package com.hotelco.constants;


public enum RoomType {
    DBL("Double"),
    QUEEN("Queen"),
    KING("King"),
    SUITE("Suite");

    private final String prettyName;

    RoomType(){prettyName = null;}

    RoomType(String prettyName) {
        this.prettyName = prettyName;
    }


    public static RoomType toRoomType(String roomTypeStr) {
        RoomType rt = null;
        switch (roomTypeStr.toUpperCase()){
            case "DBL":
            case "DOUBLE":
                rt = RoomType.DBL;
                break;
            case "QUEEN":
                rt = RoomType.QUEEN;
                break;
            case "KING":
                rt = RoomType.KING;
                break;
            case "SUITE":
                rt = RoomType.SUITE;
                break;
        }
        return rt;
    }

    public String toPrettyString() {
        return prettyName;
    }
}