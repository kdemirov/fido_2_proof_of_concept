package com.iwmnetwork.aqtos.internship.discussion.model;

public class Reference<T> {
    private T id;
    private String label;

    public Reference(T id, String label) {
        this.id = id;
        this.label = label;
    }
}
