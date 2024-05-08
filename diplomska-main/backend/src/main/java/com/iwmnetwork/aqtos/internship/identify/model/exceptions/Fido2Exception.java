package com.iwmnetwork.aqtos.internship.identify.model.exceptions;

/**
 * Fido 2 exception.
 */
public class Fido2Exception extends RuntimeException {

    /**
     * Constructor.
     *
     * @param o_O cause
     */
    public Fido2Exception(Exception o_O) {
        super(String.format(o_O.getMessage()));
    }
}
