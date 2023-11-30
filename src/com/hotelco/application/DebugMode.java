package com.hotelco.application;

import com.hotelco.constants.RoomType;
import com.hotelco.utilities.DatabaseUtil;

public class DebugMode {
    public static void sandbox(){
        Boolean result[] = DatabaseUtil.getAvailabilities(14, RoomType.KING);
        for (int i = 0; i < result.length; i++){
            System.out.println(result[i]);
        }
        System.out.println(1);
    }
}
