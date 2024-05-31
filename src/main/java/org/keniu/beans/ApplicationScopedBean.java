package org.keniu.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("application")
public class ApplicationScopedBean {
    private String value;

    public ApplicationScopedBean() {
        this.value = "Application Scoped Bean";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
