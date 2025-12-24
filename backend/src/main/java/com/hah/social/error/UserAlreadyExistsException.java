package com.hah.social.error;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException (String message) {
        super(message);
    }
}
