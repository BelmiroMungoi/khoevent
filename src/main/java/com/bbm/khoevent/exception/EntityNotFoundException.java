package com.bbm.khoevent.exception;

public class EntityNotFoundException extends BadRequestException{

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
