package com.jpsolution.vaadin.app;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;

import com.vaadin.ui.UI;

public class MyUI extends UI{

    private Navigator navigator;
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    public static class MyUIServlet extends SpringVaadinServlet {
    }
    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {

    }
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    SpringViewProvider viewProvider;
    @Override
    protected void init(VaadinRequest request) {
        this.navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        String [] beans=applicationContext.getBeanDefinitionNames();
        for(String b:beans)System.out.println(b);
    }
}