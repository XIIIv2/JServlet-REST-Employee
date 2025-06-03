package icu.xiii.app.config;

import icu.xiii.app.entity.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateUtil {

    private static final Logger LOGGER =
            Logger.getLogger(HibernateUtil.class.getName());

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = getConfiguration();
                // В конфігурації можуть бути декілька сутностей
                configuration.addAnnotatedClass(Employee.class);

                ServiceRegistry serviceRegistry =
                        new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration
                        .buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
        return sessionFactory;
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        Properties props = new Properties();
        try {
            props.load(HibernateUtil.class.getResourceAsStream("/app.properties"));
            props.put(Environment.JAKARTA_JDBC_DRIVER, props.getProperty("dbDriver"));
            props.put(Environment.JAKARTA_JDBC_URL, props.getProperty("dbUrl"));
            props.put(Environment.JAKARTA_JDBC_USER, props.getProperty("dbUser"));
            props.put(Environment.JAKARTA_JDBC_PASSWORD, props.getProperty("dbPass"));
            props.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            configuration.setProperties(props);
            return configuration;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
