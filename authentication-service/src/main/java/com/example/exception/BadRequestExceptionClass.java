package com.example.exception;

public class BadRequestExceptionClass extends RuntimeException{
    public BadRequestExceptionClass(String msg){
       super(msg);
    }
}
