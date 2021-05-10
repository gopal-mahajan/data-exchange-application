package com.data.kaveri.dataexchange.exception;

public class DataAlreadyExist extends Exception{
    public DataAlreadyExist(String dataId){
        super("DataId "+dataId+" Already Exist");
    }
}
