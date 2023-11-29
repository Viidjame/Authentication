package com.example.exception;
public class CustomExceptionClass extends RuntimeException{
    public CustomExceptionClass(String msg){
        super(msg);
    }
}
