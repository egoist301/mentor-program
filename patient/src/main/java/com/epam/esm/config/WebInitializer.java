package com.epam.esm.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {
    public static final String SERVLET_NAME = "dispatcher";
    private static final String MAPPING = "/";

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext webApplicationContext
                = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(RootConfig.class);
        webApplicationContext.register(WebConfig.class);
        DispatcherServlet servlet = new DispatcherServlet(webApplicationContext);
        ServletRegistration.Dynamic servletRegistration
                = servletContext.addServlet(SERVLET_NAME, servlet);
        servletRegistration.setLoadOnStartup(1);
        servletRegistration.addMapping(MAPPING);
    }
}
