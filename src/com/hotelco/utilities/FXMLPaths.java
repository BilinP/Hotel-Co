
package com.hotelco.utilities;

import java.lang.reflect.Field;

/**
 * The FXMLPaths class contains the file paths for all FXML files.
 * 
 * @author      Grigor Azakian
 */
public class FXMLPaths {
    public static final String LOGIN = "/com/hotelco/views/LoginGUI.fxml";
    public static final String CREATE_ACCOUNT = "/com/hotelco/views/CreateAccountGUI.fxml";
    public static final String HOME = "/com/hotelco/views/HomeGUI.fxml";
    public static final String RESERVATION = "/com/hotelco/views/ReservationGUI.fxml";
    public static final String THANK_YOU = "/com/hotelco/views/ThankYouGUI.fxml";
    public static final String DASHBOARD = "/com/hotelco/views/Dashboard.fxml";
    public static final String MANAGER_DASHBOARD = "/com/hotelco/views/ManagerDashboard.fxml";
    public static final String ROOMS = "/com/hotelco/views/RoomChoiceGUI.fxml";
    public static final String RHGUI = "/com/hotelco/views/ReservationHistoryGUI.fxml";
    public static final String PROFILE = "/com/hotelco/views/Profile.fxml";
    public static final String SCREENSAVER = "/com/hotelco/views/ScreenSaverGUI.fxml";
    public static final String RESET_PASSWORD = "/com/hotelco/views/ResetPasswordGUI.fxml";
    public static final String CHECK_IN = "/com/hotelco/views/CheckInGUI.fxml";
    public static final String All_RESERVATION = "/com/hotelco/views/AllReservationGUI.fxml";

    public static Field[] obtainAllPaths() {
        return FXMLPaths.class.getDeclaredFields();
    }
}
