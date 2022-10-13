package com.example.segproject.components;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * Text input field that only accepts integers
 */
public class IntegerField extends TextField {
    public IntegerField() {
        super();
        setTextFormatter(new TextFormatter<Integer>(new PositiveIntegerStringConverter()));
    }

    private class PositiveIntegerStringConverter extends StringConverter<Integer> {
        @Override
        public Integer fromString(String str) {
            return Math.abs(Integer.parseInt(str));
        }

        @Override
        public String toString(Integer num) {
            String outStr = "";
            if (num != null && num >= 0) {
                outStr = num.toString();
            }
            return outStr;
        }
    }
}
