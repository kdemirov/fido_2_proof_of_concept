package com.iwmnetwork.aqtos.internship.discussion.model;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

/**
 * Identifier for aggregates.
 */
@Data
@MappedSuperclass
public class Identifier implements Serializable {

    protected String id;

    /**
     * Constructor for creating the id.
     */
    public Identifier() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Constructor for recreation the id.
     *
     * @param id given id.
     */
    public Identifier(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return that.id.equals(this.id);
    }

    @Override
    public String toString() {
        return String.format("%s", this.id);
    }
}
