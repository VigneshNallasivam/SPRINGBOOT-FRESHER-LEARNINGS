package com.spring.basics.exception;

public class EncryptionException extends RuntimeException
{
    public EncryptionException(String message)
    {
        super(message);
    }
}
