package org.example;

public class WrongFormatEmailException extends RuntimeException {
    WrongFormatEmailException (String message) {
        super(message);
    }
}
