package com.iwmnetwork.aqtos.internship.discussion.model.exceptions;

public class SomethingWentWrong extends Exception{
    public SomethingWentWrong(){
        super(String.format("Something went wrong"));
    }
}
