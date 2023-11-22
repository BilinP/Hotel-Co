package com.hotelco.constants;

/**
 * Type of room from {double, queen, king, suite}
 * @author      Daniel Schwartz
 * @version     %I%, %G%
 */
public enum RoomType {
    DBL("Double"),
    QUEEN("Queen"),
    KING("King"),
    SUITE("Suite");
/**
 * Snake case and "pretty" string of this enum 
 */
    private final String prettyName;
/**
 * Constructs an empty roomtype
 */
    RoomType(){
        prettyName = null;
    }

    RoomType(String prettyName) {
        this.prettyName = prettyName;
    }

/**
 * Parses the string argument as a RoomType enume
 * @param roomTypeStr string to be converted
 * @return a RoomType enum based on the supplied string
 */
    public static RoomType parseString(String roomTypeStr) {
        RoomType rt = null;
        switch (roomTypeStr.toUpperCase()){
            case "DBL":
            case "DOUBLE":
                rt = DBL;
                break;
            case "QUEEN":
                rt = QUEEN;
                break;
            case "KING":
                rt = KING;
                break;
            case "SUITE":
                rt = SUITE;
                break;
        }
        return rt;
    }

    
/**
 * Method used to return the pretty version of a room.
 * @return prettyName
 */
    public String toPrettyString() {
        return prettyName;
    }
}