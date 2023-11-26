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
import javafx.scene.control.Control;
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

    @FXML
    private Text paymentNotification;

    @FXML
    private Text dateNotification;

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField expDateMonth;

    @FXML
    private TextField expDateYear;

    @FXML
    private TextField CVC;

    @FXML
    private Text nights;

    @FXML
    private Text tax;

    @FXML
    private Text total;

    @FXML
    private Text slash;

    @FXML
    private Text roomText;

    @FXML
    private Text rate;

    private RoomType room;



    /**
     * This method is called immediately upon controller creation.
     * It sets up the state of all DatePicker variables.
     */
    @FXML
    private void initialize() {
        endDate.setDisable(true);
        //This lets a DatePicker tell when its value has changed, and disables all Button variables if it has.
        final ChangeListener<LocalDate> startChangeListener = new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
                    LocalDate newValue) {
                        if (newValue == null) {
                            return;
                        }
                        endDate.setDisable(false);
                        if (endDate.getValue() != null) {
                            long nightsLong = ChronoUnit.DAYS.between(startDate.getValue(), endDate.getValue());
                            nights.setText(Long.toString(nightsLong));
                            ReservationCalculator.calcTotal(startDate.getValue(), endDate.getValue(), room);
                            rate.setText("$" + ReservationCalculator.calcTotal(startDate.getValue(), endDate.getValue(), room).toString());
                            tax.setText("$" + TaxRate.getTaxRate().toString());
                            total.setText("$" + ReservationCalculator.calcTotal(startDate.getValue(), endDate.getValue(), room).add(TaxRate.getTaxRate()).toString());
                        }
            }
        };

        final ChangeListener<LocalDate> endChangeListener = new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
                    LocalDate newValue) {
                        if (newValue == null) {
                            return;
                        }
                        long nightsLong = ChronoUnit.DAYS.between(startDate.getValue(), endDate.getValue());
                        nights.setText(Long.toString(nightsLong));
                        ReservationCalculator.calcTotal(startDate.getValue(), endDate.getValue(), room);
                        rate.setText("$" + ReservationCalculator.calcTotal(startDate.getValue(), endDate.getValue(), room).toString());
                        tax.setText("$" + TaxRate.getTaxRate().toString());
                        total.setText("$" + ReservationCalculator.calcTotal(startDate.getValue(), endDate.getValue(), room).add(TaxRate.getTaxRate()).toString());
            }
            
        };

        final ChangeListener<String> mmCL = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                        if (newValue.length() == 2) {
                            expDateYear.requestFocus();
                            slash.setVisible(true);
                        }    
                        if ((expDateMonth.getLength() == 2 || expDateMonth.getLength() == 2)) {
                            expDateMonth.setStyle("-fx-border-color: white;");
                            expDateYear.setStyle("-fx-border-color: white;");
                            paymentNotification.setText("");
                        } 
                        if (expDateMonth.getLength() == 2 && expDateYear.getLength() == 2) {
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM yy");
                            YearMonth yearMonth = YearMonth.parse(expDateMonth.getText() + " " + expDateYear.getText(), dateFormatter);
                            LocalDate expDate = yearMonth.atDay(1);
                            if (expDate.isBefore(LocalDate.now())) {
                                expDateMonth.setStyle("-fx-border-color: #FF0000;");
                                expDateYear.setStyle("-fx-border-color: #FF0000;");
                                paymentNotification.setText("Please enter a valid expiry date");    
                            }
                        }

            }
        };

        final ChangeListener<String> yyCL = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                        if (newValue.length() == 0 && oldValue.length() == 1) {
                            expDateMonth.requestFocus();
                            expDateMonth.positionCaret(expDateMonth.getLength() + 1);
                            slash.setVisible(false);
                        }      
                        if (expDateMonth.getLength() == 2 || expDateMonth.getLength() == 2) {
                            expDateMonth.setStyle("-fx-border-color: white;");
                            expDateYear.setStyle("-fx-border-color: white;");
                            paymentNotification.setText("");
                        }                        
                        if (expDateMonth.getLength() == 2 && expDateYear.getLength() == 2) {
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM yy");
                            YearMonth yearMonth = YearMonth.parse(expDateMonth.getText() + " " + expDateYear.getText(), dateFormatter);
                            LocalDate expDate = yearMonth.atDay(1);
                            if (expDate.isBefore(LocalDate.now())) {
                                expDateMonth.setStyle("-fx-border-color: #FF0000;");
                                expDateYear.setStyle("-fx-border-color: #FF0000;");
                                paymentNotification.setText("Please enter a valid expiry date");    
                            }
                        }
            }
        };

        final ChangeListener<Boolean> ccFocusListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && (cardNumber.getLength() != 16 && cardNumber.getLength() != 15)) {
                    cardNumber.setStyle("-fx-border-color: #FF0000;");
                    paymentNotification.setText("Please enter a valid credit card number");
                }
            }
        };

        final ChangeListener<String> ccStringListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (cardNumber.getLength() == 16 || cardNumber.getLength() == 15) {
                    cardNumber.setStyle("-fx-border-color: white;");
                    paymentNotification.setText("");
                }
            }  
        };

        final ChangeListener<Boolean> mmFocusListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && (expDateMonth.getLength() != 2 || expDateYear.getLength() != 2)) {
                    expDateMonth.setStyle("-fx-border-color: #FF0000;");
                    expDateYear.setStyle("-fx-border-color: #FF0000;");
                    paymentNotification.setText("Please enter a valid expiry date");
                }
                if (!newValue && expDateMonth.getLength() == 2) {
                    slash.setVisible(true);
                }
            }
        };     
        
        final ChangeListener<Boolean> yyFocusListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && (expDateMonth.getLength() != 2 || expDateYear.getLength() != 2)) {
                    expDateMonth.setStyle("-fx-border-color: #FF0000;");
                    expDateYear.setStyle("-fx-border-color: #FF0000;");
                    paymentNotification.setText("Please enter a valid expiry date");
                }
            }
        };      
        
        final ChangeListener<Boolean> CVCFocusListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && (CVC.getLength() != 3 && CVC.getLength() != 4)) {
                    CVC.setStyle("-fx-border-color: #FF0000;");
                    paymentNotification.setText("Please enter a valid CVC");
                }
            }
        };          

        final ChangeListener<String> CVCStringListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (CVC.getLength() == 3 || CVC.getLength() == 4) {
                    CVC.setStyle("-fx-border-color: white;");
                    paymentNotification.setText("");
                }
            }  
        };        


        //This sets up the DatePicker to not allow a user to choose a date before the current date.
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

        //This sets up the DatePicker to not allow a user to choose a date before the current date.
        final Callback<DatePicker, DateCell> endDayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        Boolean availability = DatabaseUtil.checkAvailability(startDate.getValue(), item, room);
                        setDisable(empty || item.compareTo(startDate.getValue()) < 0 || !availability 
                            || item.isEqual(LocalDate.now()) || item.isEqual(startDate.getValue()));
                    }
                };
            }

        };      
        
        TextFormatters textFormatters = new TextFormatters();

        Platform.runLater(() -> {
            startDate.valueProperty().addListener(startChangeListener);
            startDate.setDayCellFactory(startDayCellFactory);
            endDate.setDayCellFactory(endDayCellFactory);
            endDate.valueProperty().addListener(endChangeListener);
            expDateMonth.setTextFormatter(textFormatters.EXP_DATE_MONTH);
            expDateYear.setTextFormatter(textFormatters.EXP_DATE_YEAR);
            expDateMonth.textProperty().addListener(mmCL);
            expDateYear.textProperty().addListener(yyCL);
            expDateMonth.focusedProperty().addListener(mmFocusListener);
            expDateYear.focusedProperty().addListener(yyFocusListener);
            CVC.setTextFormatter(textFormatters.CVC);
            cardNumber.setTextFormatter(textFormatters.CREDIT_CARD);
            cardNumber.focusedProperty().addListener(ccFocusListener);
            cardNumber.textProperty().addListener(ccStringListener);
            CVC.textProperty().addListener(CVCStringListener);
            CVC.focusedProperty().addListener(CVCFocusListener);
        });
    }

    /**
     * Called when pressing the 'Book' button. 
     * Saves all user input to OrderSession.
     * After creating the booking, it will enter 'PaymentGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Book' button.
     */
    @FXML
    private void createBooking(MouseEvent event) {
        boolean datePickerEmpty = isDatePickersEmpty();
        boolean paymentEmpty = isPaymentEmpty();
        if (datePickerEmpty && paymentEmpty) {
            dateNotification.setText("Please fill out reservation details");
            paymentNotification.setText("Please fill out payment information");
            return;
        }
        else if (datePickerEmpty) {
            dateNotification.setText("Please fill out reservation details");
            paymentNotification.setText("");
            return;
        }
        else if (paymentEmpty) {
            dateNotification.setText("");
            paymentNotification.setText("Please fill out payment information");
            return;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM yy");
        YearMonth yearMonth = YearMonth.parse(expDateMonth.getText() + " " + expDateYear.getText(), dateFormatter);
        LocalDate expDate = yearMonth.atDay(1);
        CreditCard card = new CreditCard(
            cardNumber.getText(), CVC.getText(), expDate,
            ReservationSystem.getCurrentUser()
        );

        if (!card.verify() || !Verifier.cvvCheck(CVC.getText(), Verifier.getIssuer(card.getCreditCardNum()))) {
            paymentNotification.setText("Please check your payment details");
            return;            
        }
        card.assign();
        
        Room room = new Room(
            DatabaseUtil.findEmptyRoom(
                startDate.getValue(), endDate.getValue(),
                this.room));
        Reservation reservation = new Reservation(
            room, startDate.getValue(), endDate.getValue(),
            ReservationSystem.getCurrentUser(), Integer.parseInt(guests.getText()));
        ReservationSystem.setCurrentReservation(reservation);
        ReservationSystem.book();
        reservation = ReservationSystem.getCurrentReservation();
        /*
        OrderSession.setStartDate(startDate.getValue());
        OrderSession.setEndDate(endDate.getValue());
        OrderSession.setGuests(Integer.parseInt(guests.getText()));
        OrderSession.setRoomType(room);
        */
        //Instances.getDashboardController().switchAnchor("/com/hotelco/views/ThankYouGUI.fxml");
    }

    /**
     * This method is called by pressing the '-' text to the left of the guest number.
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
     * This method is called by pressing the 'Go Back' text.
     * It exits the 'SearchGUI' and enters the 'MenuGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Go Back' text.
     */
    @FXML
    private void switchToRoomChoiceScene(MouseEvent event) {
        Instances.getDashboardController().switchAnchor(FXMLPaths.ROOMS);
    }

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

    private boolean isPaymentEmpty() {
        Control[] controls = new Control[] {
            cardNumber, expDateMonth, 
            expDateYear, CVC
        };
        for (Control control: controls) {
            if (control instanceof TextField) {
                TextField textField = (TextField) control;
                if (textField.getText().isEmpty()) {
                    return true;
                }
            }       
        }
        if (cardNumber.getLength() != 12) {
            return true;
        }
        if (expDateMonth.getLength() != 2 || expDateYear.getLength() != 2) {
            return true;
        }
        if (CVC.getLength() != 3) {
            return true;
        }
        return false;
    }

    private boolean isDatePickersEmpty() {
        DatePicker[] datePickers = new DatePicker[] {
            startDate, endDate
        };
        for (DatePicker datePicker: datePickers) {
            if (datePicker.getValue() == null) {
                return true;
            }
        }
        return false;
    }

    public void setRoomType(RoomType roomType) {
        this.room = roomType;
        roomText.setText(roomType.toPrettyString());
    }
}