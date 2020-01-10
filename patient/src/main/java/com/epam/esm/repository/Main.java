package com.epam.esm.repository;

import com.epam.esm.config.ConnectionConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) throws ParseException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConnectionConfig.class);
        PatientDao patientDao = applicationContext.getBean(PatientDao.class);
        final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(patientDao.search("%", "%", "%", 3221114,SIMPLE_DATE_FORMAT.parse("2000-08-27")));
    }
}
