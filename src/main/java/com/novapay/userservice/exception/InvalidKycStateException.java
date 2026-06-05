package com.novapay.userservice.exception;

public class InvalidKycStateException extends RuntimeException{
    public InvalidKycStateException(String message){
        super(message);
    }
}
