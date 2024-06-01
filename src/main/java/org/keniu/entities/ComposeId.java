package org.keniu.entities;

import java.io.Serializable;
import java.util.Objects;

public class ComposeId implements Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComposeId composeId)) return false;
        return Objects.equals(id, composeId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
