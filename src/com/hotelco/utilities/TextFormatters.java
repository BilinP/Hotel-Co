package com.hotelco.utilities;

import javafx.scene.control.TextFormatter;

/**
 * The TextFormatters class contains the relevant TextField logic for CreateAccountController.
 * 
 * @author      Grigor Azakian
 */
public class TextFormatters {
    public final TextFormatter<String> PHONE_NUMBER = new TextFormatter<>(changed -> {
        if (changed.getControlNewText().length() > 10) {
            return null;
        }
        if (changed.getControlNewText().matches("\\d*")) {
            return changed;
        }
        else {
            return null;
        }
    });

    public final TextFormatter<String> FIRST_NAME = new TextFormatter<>(changed -> {
        if (changed.getControlNewText().isEmpty()) {
            return changed;
        }
        else if (Character.isLetter(changed.getControlNewText().charAt(changed.getControlNewText().length() - 1))) {
            return changed;
        }
        else {
            return null;
        }
    });

    public final TextFormatter<String> LAST_NAME = new TextFormatter<>(changed -> {
        if (changed.getControlNewText().isEmpty()) {
            return changed;
        }
        else if (Character.isLetter(changed.getControlNewText().charAt(changed.getControlNewText().length() - 1))) {
            return changed;
        }
        else {
            return null;
        }
    });


}
