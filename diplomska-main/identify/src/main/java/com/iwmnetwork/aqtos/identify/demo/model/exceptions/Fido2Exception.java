package com.iwmnetwork.aqtos.identify.demo.model.exceptions;

public class Fido2Exception extends RuntimeException {

    public Fido2Exception(Exception exception) {
        super(String.format(exception.getMessage()));
    }
}
