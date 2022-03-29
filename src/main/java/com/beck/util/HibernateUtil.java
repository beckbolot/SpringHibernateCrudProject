package com.beck.util;

import com.beck.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                //Hibernate settings equivalent to hibernate.cfg.xml
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
                settings.put(Environment.USER, "postgres");
                settings.put(Environment.PASS, "daniel2013");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "create");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Employee.class);
                configuration.addAnnotatedClass(Detail.class);
                configuration.addAnnotatedClass(Department.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return sessionFactory;
    }

    public static void shutDown() {
        getSessionFactory().close();

    }


}
