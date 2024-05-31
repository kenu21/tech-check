package org.keniu.controllers;

import org.keniu.beans.ApplicationScopedBean;
import org.keniu.beans.RequestScopedBean;
import org.keniu.beans.SessionScopedBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scope")
public class ScopeController {
    private final ObjectFactory<RequestScopedBean> requestScopedBeanObjectFactory;
    private final ObjectFactory<SessionScopedBean> sessionScopedBeanObjectFactory;
    private final ObjectFactory<ApplicationScopedBean> applicationScopedBeanObjectFactory;

    @Autowired
    public ScopeController(ObjectFactory<RequestScopedBean> requestScopedBeanObjectFactory,
                           ObjectFactory<SessionScopedBean> sessionScopedBeanObjectFactory,
                           ObjectFactory<ApplicationScopedBean> applicationScopedBeanObjectFactory) {
        this.requestScopedBeanObjectFactory = requestScopedBeanObjectFactory;
        this.sessionScopedBeanObjectFactory = sessionScopedBeanObjectFactory;
        this.applicationScopedBeanObjectFactory = applicationScopedBeanObjectFactory;
    }

    @PostMapping("/request")
    public String setRequestScopedValue(@RequestParam String value) {
        RequestScopedBean requestScopedBean = requestScopedBeanObjectFactory.getObject();
        requestScopedBean.setValue(value);
        RequestScopedBean newRequestScopedBean = requestScopedBeanObjectFactory.getObject();
        return "Request scoped value set to: " + newRequestScopedBean.getValue();
    }

    @GetMapping("/request")
    public String getRequestScopedValue() {
        RequestScopedBean requestScopedBean = requestScopedBeanObjectFactory.getObject();
        return "Request scoped value: " + requestScopedBean.getValue();
    }

    @PostMapping("/session")
    public String setSessionScopedValue(@RequestParam String value) {
        sessionScopedBeanObjectFactory.getObject().setValue(value);
        return "Session scoped value set to: " + sessionScopedBeanObjectFactory.getObject().getValue();
    }

    @GetMapping("/session")
    public String getSessionScopedValue() {
        return sessionScopedBeanObjectFactory.getObject().getValue();
    }

    @PostMapping("/application")
    public String setApplicationScopedValue(@RequestParam String value) {
        applicationScopedBeanObjectFactory.getObject().setValue(value);
        return "Application scoped value set to: " + applicationScopedBeanObjectFactory.getObject().getValue();
    }

    @GetMapping("/application")
    public String getApplicationScopedValue() {
        return applicationScopedBeanObjectFactory.getObject().getValue();
    }
}
