package ua.garmash.module4.config;

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
//            final String databaseUriEnv ="postgres://ehnthqyzrkxllk:0a255d3e5efff1ef873016adfd7497f4e9bcc40906aa9df11986ae6efb470412@ec2-54-246-185-161.eu-west-1.compute.amazonaws.com:5432/d6k8p174f4nvki";
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
