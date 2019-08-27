package com.zaloni.training.conf;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DbConnection {
    static Session sessionObj = null;

    public static Session getSession() {
        if (sessionObj == null) {
            sessionObj = getSessionFactory().openSession();
        }
        return sessionObj;
    }

    private static SessionFactory getSessionFactory() {
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        configObj.configure("hibernate.cfg.xml");
 
        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 
 
        // Creating Hibernate SessionFactory Instance
        SessionFactory sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }
}
