package com.heroku.module3.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class HibernateFactoryUtil {
    private static EntityManager entityManager;

    public static void init() {
        DBCredentials dbCredentials = DBCredentials.getCredentials();
        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.jdbc.url", dbCredentials.getDB_URL());
        props.put("javax.persistence.jdbc.user", dbCredentials.getDB_USER());
        props.put("javax.persistence.jdbc.password", dbCredentials.getDB_PASSWORD());

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("persistence", props);
        entityManager = entityManagerFactory.createEntityManager();
    }

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            init();
        }
        return entityManager;
    }
}
