package com.hotelco.application;

import com.hotelco.constants.RoomType;
import com.hotelco.utilities.DatabaseUtil;

public class DevMode {
    public static void run(){
        Boolean result[] = DatabaseUtil.getAvailabilities(14, RoomType.KING);
        for (int i = 0; i < result.length; i++){
            System.out.println(result[i]);
        }
    }
}
