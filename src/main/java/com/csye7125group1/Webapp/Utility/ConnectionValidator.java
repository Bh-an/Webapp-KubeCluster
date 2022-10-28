package com.csye7125group1.Webapp.Utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.ConnectionHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ConnectionValidator {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String pass;
    public boolean checkConnection(){

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            if (conn.isValid(30)){
                return TRUE;
            }

            return FALSE;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return FALSE;
    }
}
