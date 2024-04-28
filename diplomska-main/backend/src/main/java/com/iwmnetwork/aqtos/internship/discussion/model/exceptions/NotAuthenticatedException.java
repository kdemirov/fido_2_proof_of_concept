package com.iwmnetwork.aqtos.internship.discussion.model.exceptions;

public class NotAuthenticatedException extends RuntimeException{
    public NotAuthenticatedException(){
        super("Not authenticated");
    }
}
