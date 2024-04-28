package com.iwmnetwork.aqtos.internship.identify.model.exceptions;

public class Fido2Exception extends RuntimeException {

    public Fido2Exception(Exception exception) {
        super(String.format(exception.getMessage()));
    }
}
