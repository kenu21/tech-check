package org.keniu.beans;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
public class SessionScopedBean implements Serializable {
    private String value;

    public SessionScopedBean() {
        this.value = "Session Scoped Bean";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
