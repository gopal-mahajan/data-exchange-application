package com.data.kaveri.dataexchange.exception;

public class DataNotFoundException extends Exception {

    public DataNotFoundException(String id) {
        super("Data with ID : " + id + " does not exists!!");
    }

}
