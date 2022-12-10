package com.csye7125group1.Webapp.Configurations;

import com.csye7125group1.Webapp.Utility.Authenticator;
import com.csye7125group1.Webapp.Utility.ConnectionValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Authenticator getauthcreds() {
        return new Authenticator();
    }

    @Bean
    public ConnectionValidator checkConnection(){return new ConnectionValidator();}
}
