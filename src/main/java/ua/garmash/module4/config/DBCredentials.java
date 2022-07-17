package com.heroku.module3.config;

import lombok.Value;

import java.net.URI;
import java.net.URISyntaxException;

@Value
public class DBCredentials {
    private static DBCredentials instance;
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    private DBCredentials(String dbUrl, String dbUser, String dbPassword) {
        this.DB_URL = dbUrl;
        this.DB_USER = dbUser;
        this.DB_PASSWORD = dbPassword;
    }

    public static DBCredentials getCredentials() {
        if (instance == null) {
            final String databaseUriEnv = System.getenv("DATABASE_URL");
            String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
            String username = "postgres";
            String password = "root";
            if (databaseUriEnv != null) {
                try {
                    URI dbUri = new URI(databaseUriEnv);
                    dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +
                            "?sslmode=require";
                    username = dbUri.getUserInfo().split(":")[0];
                    password = dbUri.getUserInfo().split(":")[1];
                } catch (URISyntaxException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
            instance = new DBCredentials(dbUrl, username, password);
        }
        return instance;
    }
}
