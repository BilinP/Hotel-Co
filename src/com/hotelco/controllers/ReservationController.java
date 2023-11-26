package com.hotelco.controllers;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.hotelco.constants.Constants;
import com.hotelco.constants.RoomType;
import com.hotelco.entities.CreditCard;
import com.hotelco.entities.Reservation;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.entities.Room;
import com.hotelco.utilities.DatabaseUtil;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.Instances;
import com.hotelco.utilities.ReservationCalculator;
import com.hotelco.utilities.TaxRate;
import com.hotelco.utilities.TextFormatters;
import com.hotelco.utilities.Verifier;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * The RoomSearchController class is the associated controller class of the 'RoomSearchGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Grigor Azakian
 */
public class ReservationController extends BaseController {

    /***************************************************************************
     *                                                                         *
     * JavaFX Nodes                                                            *
     *                                                                         *
     **************************************************************************/    

    /**
     * DatePicker that contains the date the user wants to be the first day of their booking.
     */
    @FXML
    private DatePicker startDate;

    /**
     * DatePicker that contains the date the user wants to be the last day of their booking.
     */
    @FXML
    private DatePicker endDate;

    /**
     * Text that contains the number of guests.
     */
    @FXML
    private Text guests;

    /**
     * Text that notifies user of improper payment details.
     */
    @FXML
    private Text paymentNotification;

    /**
     * Text that notifies user of empty DatePicker fields.
     */
    @FXML
    private Text dateNotification;

    /**
     * TextField that contains a users credit card number.
     */
    @FXML
    private TextField cardNumber;

    /**
     * TextField that contains a users credit cards expiration month.
     */
    @FXML
    private TextField expDateMonth;

    /**
     * TextField that contains a users credit cards expiration year.
     */    
    @FXML
    private TextField expDateYear;

    /**
     * TextField that contains a users credit cards CVC.
     */    
    @FXML
    private TextField CVC;

    /**
     * Text that displays the number of nights a user will be staying.
     */
    @FXML
    private Text nights;

    /**
     * Text that displays the tax rate.
     */
    @FXML
    private Text tax;

    /**
     * Text that displays a bookings total cost.
     */
    @FXML
    private Text total;

    /**
     * Text of a '/' that appears when entering expiration date.
     */
    @FXML
    private Text slash;

    /**
     * Text that displays the users current room choice.
     */
    @FXML
    private Text roomText;

    /**
     * Text that displays a bookings subtotal cost.
     */
    @FXML
    private Text rate;

    /***************************************************************************
     *                                                                         *
     * Instance Variables                                                      *
     *                                                                         *
     **************************************************************************/

     /**
      * RoomType that stored a users selected RoomType.
      */
    private RoomType room;

    /***************************************************************************
     *                                                                         *
     * DatePickers                                                             *
     *                                                                         *
     **************************************************************************/

     /**
      * ChangeListener associated with startDate.<p>
      * If startDate isn't set to null:<ul>
      * <li>Enables the ability to set endDate after making a selection.
      * <li>If endDate is already set, calls updateTotals().
      * </ul>
      */
    final ChangeListener<LocalDate> startChangeListener = new ChangeListener<LocalDate>() {
        @Override
        public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
                LocalDate newValue) {
            if (newValue == null) {
                return;
            }
            endDate.setDisable(false);
            if (endDate.getValue() != null) {
                updateTotals();
            }
        }
    };

     /**
      * ChangeListener associated with endDate.<p>
      * If endDate isn't set to null, calls updateTotals().
      */    
    final ChangeListener<LocalDate> endChangeListener = new ChangeListener<LocalDate>() {
        @Override
        public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
                LocalDate newValue) {
            if (newValue == null) {
                return;
            }
            updateTotals();
        }
    };

    /**
     * ChangeListener associated with expDateMonth.<p>
     * Creates a '/' and jumps to expDateYear upon field completion.
     * Manages if field properties are correct or false, and displays relevant error message.
     */
    final ChangeListener<String> mmCL = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue,
                String newValue) {
            if (newValue.length() == 2) {
                expDateYear.requestFocus();
                slash.setVisible(true);
            }    
            processCompleteExpDate();
            processInvalidExpDate();
        }
    };

    /**
     * ChangeListener associated with expDateYear.<p>
     * Removes the '/' and jumps to expDateMonth upon field deletion.
     * Manages if field properties are correct or false, and displays relevant error message.
     */
    final ChangeListener<String> yyCL = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue,
                String newValue) {
            if (newValue.length() == 0 && oldValue.length() == 1) {
                expDateMonth.requestFocus();
                expDateMonth.positionCaret(expDateMonth.getLength() + 1);
                slash.setVisible(false);
            }
            processCompleteExpDate();                 
            processInvalidExpDate();
        }
    };

    /**
     * ChangeListener associated with the focus property of expDateMonth.<p>
     * Displays error message if user leaves focus without finishing entering MM field.
     * Sets a '/' if MM field is successfully entered.
     */
    final ChangeListener<Boolean> mmFocusListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (!newValue && expDateMonth.getLength() == 2) {
                slash.setVisible(true);
            }
            processIncompleteExpDate(newValue);
        }
    };
  
    /**
     * ChangeListener associated with the focus property of expDateMonth.<p>
     * Displays error message if user leaves focus without finishing entering YY field.
     */    
    final ChangeListener<Boolean> yyFocusListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            processIncompleteExpDate(newValue);
        }
    };     

    /**
     * ChangeListener associated with the focus property of cardNumber.<p>
     * Displays error message if user leaves focus without finishing entering credit card field.
     */       
    final ChangeListener<Boolean> ccFocusListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (!newValue && (cardNumber.getLength() != 16 && cardNumber.getLength() != 15)) {
                setRedBorder(cardNumber);
                paymentNotification.setText("Please enter a valid credit card number");
            }
        }
    };

    /**
     * ChangeListener associated with the String property of cardNumber.<p>
     * Resets error status if user successfully finishes entering credit card information.
     */           
    final ChangeListener<String> ccStringListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (cardNumber.getLength() == 16 || cardNumber.getLength() == 15) {
                setWhiteBorder(cardNumber);
                paymentNotification.setText("");
            }
        }  
    };

     
    /**
     * ChangeListener associated with the focus property of CVC.<p>
     * Displays error message if user leaves focus without finishing entering CVC field.
     */          
    final ChangeListener<Boolean> CVCFocusListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (!newValue && (CVC.getLength() != 3 && CVC.getLength() != 4)) {
                setRedBorder(CVC);
                paymentNotification.setText("Please enter a valid CVC");
            }
        }
    };          

    /**
     * ChangeListener associated with the String property of CVC.<p>
     * Resets error status if user successfully finishes entering CVC information.
     */          
    final ChangeListener<String> CVCStringListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (CVC.getLength() == 3 || CVC.getLength() == 4) {
                setWhiteBorder(CVC);
                paymentNotification.setText("");
            }
        }  
    };

    /***************************************************************************
     *                                                                         *
     * DayCellFactories                                                        *
     *                                                                         *
     **************************************************************************/  

    final Callback<DatePicker, DateCell> startDayCellFactory = new Callback<DatePicker, DateCell>() {
        @Override
        public DateCell call(DatePicker param) {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    Boolean availability = DatabaseUtil.checkAvailability(item, item, room);
                    LocalDate currentDate = LocalDate.now();
                    setDisable(empty || item.compareTo(currentDate) < 0 || 
                        !availability || checkConflict(item));
                }

                private boolean checkConflict(LocalDate item) {
                    if (endDate.getValue() != null) {
                        if (item.isEqual(endDate.getValue())) {
                            return true;
                        }
                        return item.isAfter(endDate.getValue());
                    }
                    else {
                        return false;
                    }
                }
            };
        }
    };

    final Callback<DatePicker, DateCell> endDayCellFactory = new Callback<DatePicker, DateCell>() {
        @Override
        public DateCell call(DatePicker param) {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    Boolean availability = DatabaseUtil.checkAvailability(startDate.getValue(), item, room);
                    setDisable(empty || item.compareTo(startDate.getValue()) < 0 || !availability ||
                        item.isEqual(LocalDate.now()) || item.isEqual(startDate.getValue()));
                }
            };
        }
    }; 

    /**
     * This method is called immediately upon controller creation.
     * It applies the DayCellFactories and ChangeListeners to all relevant DatePicker and TextField variables.
     */
    @FXML
    private void initialize() {
        TextFormatters textFormatters = new TextFormatters();
        Platform.runLater(() -> {
            startDate.valueProperty().addListener(startChangeListener);
            startDate.setDayCellFactory(startDayCellFactory);
            endDate.setDayCellFactory(endDayCellFactory);
            endDate.valueProperty().addListener(endChangeListener);
            expDateMonth.setTextFormatter(textFormatters.EXP_DATE_MONTH);
            expDateMonth.textProperty().addListener(mmCL);
            expDateMonth.focusedProperty().addListener(mmFocusListener); 
            expDateYear.setTextFormatter(textFormatters.EXP_DATE_YEAR);          
            expDateYear.textProperty().addListener(yyCL);
            expDateYear.focusedProperty().addListener(yyFocusListener);
            cardNumber.setTextFormatter(textFormatters.CREDIT_CARD);
            cardNumber.focusedProperty().addListener(ccFocusListener);
            cardNumber.textProperty().addListener(ccStringListener);
            CVC.textProperty().addListener(CVCStringListener);
            CVC.focusedProperty().addListener(CVCFocusListener);
            CVC.setTextFormatter(textFormatters.CVC);
        });
    }

    /**
     * Called when pressing the 'Book' button.<p> 
     * Starts by performing a series of checks:<p>
     * <ul>
     * <li>Checks to see if any of the date pickers are empty.
     * <li>Checks to see if any of the payment details are empty.
     * <li>Checks to see if payment details are accurate.
     * </ul>
     * If any of these checks fail, it will return from the function.<p>
     * Otherwise, creates the users desired reservation.
     * After creating the booking, it will enter 'ThankYouGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Book' button.
     */
    @FXML
    private void createBooking(MouseEvent event) {
        if (handleErrorMessage(isDatePickersEmpty(), isPaymentEmpty())) {
            return;
        }
        if (!verifyCard()) {
            return;
        }

        Reservation reservation = createReservation();
        /*
        OrderSession.setStartDate(startDate.getValue());
        OrderSession.setEndDate(endDate.getValue());
        OrderSession.setGuests(Integer.parseInt(guests.getText()));
        OrderSession.setRoomType(room);
        */
        //Instances.getDashboardController().switchAnchor("/com/hotelco/views/ThankYouGUI.fxml");
    }

    /**
     * This method is called by pressing the '-' text to the right of the guest number.
     * It decrements the current guest number as long as the current guest number is greater than '1'.
     * @param event The 'mouse released' event that is triggered by pressing the '-' text to the left of the guest number.
     */
    @FXML
    private void decrementGuest(MouseEvent event) {
        if (Integer.parseInt(guests.getText()) > 1) {
            guests.setText(Integer.toString(Integer.parseInt(guests.getText()) - 1));
        }
    }

    /**
     * This method is called by pressing the '+' text to the right of the guest number.
     * It increments the current guest number as long as it is below the maximum amount of allowed guests.
     * @param event The 'mouse released' event that is triggered by pressing the '+' text to the right of the guest number.
     */
    @FXML
    private void incrementGuest(MouseEvent event) {
        if (Integer.parseInt(guests.getText()) < Constants.MAX_CAP) {
            guests.setText(Integer.toString(Integer.parseInt(guests.getText()) + 1));
        }
    }

    /**
     * This method is called by pressing the 'Change Room Type' text.
     * It exits the 'ReservationGUI' and enters the 'RoomChoiceGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Change Room Type' text.
     */
    @FXML
    private void switchToRoomChoiceScene(MouseEvent event) {
        Instances.getDashboardController().switchAnchor(FXMLPaths.ROOMS);
    }

    /**
     * Sets all DatePickers to null and resets all Text displaying payment information to their default values.
     * @param event The 'mouse released' event that is triggered by pressing the Button labeled 'x'.
     */
    @FXML
    private void resetDates(MouseEvent event) {
        endDate.setValue(null);
        startDate.setValue(null);
        endDate.setDisable(true);
        nights.setText("0");
        rate.setText("$0.00");
        tax.setText("$0.00");
        total.setText("$0.00");
    }

    /**
     * Checks to see if any of the payment TextFields are empty.
     * Marks all empty TextFields with a red border.
     * @return true if any of the payment TextFields are empty, false if they are all filled
     */
    private boolean isPaymentEmpty() {
        TextField[] textFields = new TextField[] {
            cardNumber, expDateMonth, 
            expDateYear, CVC
        };
        boolean empty = false;
        for (TextField textField: textFields) {
            if (textField.getText().isEmpty()) {
                setRedBorder(textField);
                empty = true;
            } 
        }
        if (cardNumber.getLength() != 15 || cardNumber.getLength() != 16) {
            setRedBorder(cardNumber);
            empty = true;
        }
        if (expDateMonth.getLength() != 2 || expDateYear.getLength() != 2) {
            setRedBorder(expDateMonth);
            setRedBorder(expDateYear);
            empty = true;
        }
        if (CVC.getLength() != 3 || CVC.getLength() != 4) {
            setRedBorder(CVC);
            empty = true;
        }
        return empty;
    }

    /**
     * Checks to see if any of the DatePickers are empty.
     * Marks all empty DatePickers with a red border.
     * @return true if any of the DatePickers are empty, false if they are all filled
     */    
    private boolean isDatePickersEmpty() {
        DatePicker[] datePickers = new DatePicker[] {
            startDate, endDate
        };
        boolean empty = false;
        for (DatePicker datePicker: datePickers) {
            if (datePicker.getValue() == null) {
                empty = true;
                datePicker.setStyle("-fx-border-color: #FF0000;");
            }
        }
        return empty;
    }

    /**
     * Changes the border color of a TextField to red.
     * @param textField TextField to change the border color of.
     */
    private void setRedBorder(TextField textField) {
        textField.setStyle("-fx-border-color: #FF0000;");
    }

    /**
     * Changes the border color of a TextField to white.
     * @param textField TextField to change the border color of.
     */
    private void setWhiteBorder(TextField textField) {
        textField.setStyle("-fx-border-color: white;");
    }

    /**
     * Calculates booking cost based on users input and updates relevant Text fields.
     */
    private void updateTotals() {
        long nightsLong = ChronoUnit.DAYS.between(startDate.getValue(), endDate.getValue());
        nights.setText(Long.toString(nightsLong));
        ReservationCalculator.calcTotal(startDate.getValue(), endDate.getValue(), room);
        rate.setText("$" + ReservationCalculator.calcTotal(startDate.getValue(), endDate.getValue(), room).toString());
        tax.setText("$" + TaxRate.getTaxRate().toString());
        total.setText("$" + ReservationCalculator.calcTotal(startDate.getValue(), endDate.getValue(), room).add(TaxRate.getTaxRate()).toString());
    }

    /**
     * Sets expiration date TextField borders to red and displays error message if
     * user goes out of focus without entering a valid expiration date.
     * @param newValue The focus state of any expiration date TextField.
     */
    private void processIncompleteExpDate(Boolean newValue) {
        if (!newValue && !(expDateMonth.getLength() == 2 && expDateYear.getLength() == 2)) {
            setRedBorder(expDateMonth);
            setRedBorder(expDateYear);
            paymentNotification.setText("Please enter a valid expiry date");
        }
    }

    /**
     * Sets expiration date TextField borders to white and clears error message if
     * user finished entering either MM or YY, or if the user deletes either the MM or YY fields. 
     * Used to refresh the error status if user is retrying to enter a valid expiration date.<p>
     * If user enters an invalid number in the MM field, displays an error instead.
     * @param newValue The focus state of any expiration date TextField.
     */    
    private void processCompleteExpDate() {
        if (expDateMonth.getText().isEmpty() || expDateMonth.getText().isEmpty()) {
            setWhiteBorder(expDateMonth);
            setWhiteBorder(expDateYear);
            paymentNotification.setText("");            
            return;
        }
        if (Integer.parseInt(expDateMonth.getText()) > 12) {
            setRedBorder(expDateMonth);
            setRedBorder(expDateYear);
            paymentNotification.setText("Please enter a valid expiry date");  
            return;
        }        
        if (expDateMonth.getLength() == 2 || expDateMonth.getLength() == 2) {
            setWhiteBorder(expDateMonth);
            setWhiteBorder(expDateYear);
            paymentNotification.setText("");
        } 
    }

    /**
     * Sets expiration date TextField borders to red and displays error message if
     * entered expiration date has already passed.
     * @param newValue The focus state of any expiration date TextField.
     */
    private void processInvalidExpDate() {
        if (expDateMonth.getLength() == 2 && expDateYear.getLength() == 2) {
            if (Integer.parseInt(expDateMonth.getText()) > 12) {
                setRedBorder(expDateMonth);
                setRedBorder(expDateYear);
                paymentNotification.setText("Please enter a valid expiry date");  
                return;
            }
            if (parseExpDate().isBefore(LocalDate.now())) {
                setRedBorder(expDateMonth);
                setRedBorder(expDateYear);
                paymentNotification.setText("Please enter a valid expiry date");    
            }
        }
    }

    /**
     * Displays relevant error message(s) if DatePicker(s) or payment TextField(s) are empty.
     * @param datePickerEmpty Boolean status of filled DatePicker fields.
     * @param paymentEmpty Boolean status of filled payment TextFields.
     * @return
     */
    private boolean handleErrorMessage(Boolean datePickerEmpty, Boolean paymentEmpty) {
        if (datePickerEmpty && paymentEmpty) {
            dateNotification.setText("Please fill out reservation details");
            paymentNotification.setText("Please fill out payment information");
            return true;
        }
        else if (datePickerEmpty) {
            dateNotification.setText("Please fill out reservation details");
            paymentNotification.setText("");
            return true;
        }
        else if (paymentEmpty) {
            dateNotification.setText("");
            paymentNotification.setText("Please fill out payment information");
            return true;
        }
        return false;
    }

    /**
     * Parses the users entered expiration date.
     * @return LocalDate of entered expiration date.
     */
    private LocalDate parseExpDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM yy");
        YearMonth yearMonth = YearMonth.parse(expDateMonth.getText() + " " + expDateYear.getText(), dateFormatter);
        LocalDate expDate = yearMonth.atDay(1);      
        return expDate;
    }

    /**
     * Verifies a users credit card information.
     * If any details are inaccurate, sets payment TextField borders to red and displays an error message.
     * @return
     */
    private boolean verifyCard() {
        if (Integer.parseInt(expDateMonth.getText()) > 12) {
            return false;
        }
        CreditCard card = new CreditCard(
            cardNumber.getText(), CVC.getText(), parseExpDate(),
            ReservationSystem.getCurrentUser()
        );

        if (!card.verify() || !Verifier.cvvCheck(CVC.getText(), Verifier.getIssuer(card.getCreditCardNum()))) {
            paymentNotification.setText("Please check your payment details");
            setRedBorder(CVC);
            setRedBorder(cardNumber);
            setRedBorder(expDateMonth);
            setRedBorder(expDateYear);
            return false;            
        }
        card.assign();
        return true;
    }

    /**
     * Creates a reservation based on users input.
     * @return The newly created Reservation.
     */
    private Reservation createReservation() {
        Room room = new Room(
            DatabaseUtil.findEmptyRoom(
                startDate.getValue(), endDate.getValue(),
                this.room));
        Reservation reservation = new Reservation(
            room, startDate.getValue(), endDate.getValue(),
            ReservationSystem.getCurrentUser(), Integer.parseInt(guests.getText()));
        ReservationSystem.setCurrentReservation(reservation);
        ReservationSystem.book();
        return ReservationSystem.getCurrentReservation();
    }

    /**
     * <b>This function must be manually called upon switching to ReservationGUI.</b><p>
     * Sets the users chosen RoomType upon controller creation.
     * @param roomType Users chosen RoomType
     */
    public void setRoomType(RoomType roomType) {
        this.room = roomType;
        roomText.setText(roomType.toPrettyString());
    }
}