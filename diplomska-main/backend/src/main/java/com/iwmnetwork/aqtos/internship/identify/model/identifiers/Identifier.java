package com.iwmnetwork.aqtos.internship.identify.model.identifiers;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class Identifier implements Serializable {
    protected String id;

    public Identifier() {
        this.id = UUID.randomUUID().toString();
    }

    public Identifier(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s", this.id);
    }
}
