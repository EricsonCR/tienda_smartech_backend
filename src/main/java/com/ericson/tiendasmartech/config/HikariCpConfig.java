package com.ericson.tiendasmartech.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HikariCpConfig {

    @Value("${DB_TIENDA_SMARTECH_URL}")
    private String dbUrl;

    @Value("${DB_MYSQL_USERNAME}")
    private String dbUsername;

    @Value("${DB_MYSQL_PASSWORD}")
    private String dbPassword;

    @Value("${DB_MYSQL_DRIVER}")
    private String dbDriverClassName;

    @Bean
    public HikariDataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dbUrl);
        hikariConfig.setUsername(dbUsername);
        hikariConfig.setPassword(dbPassword);
        hikariConfig.setDriverClassName(dbDriverClassName);

        hikariConfig.setMaximumPoolSize(10); // Máximo tamaño del pool
        hikariConfig.setConnectionTimeout(30000); // Timeout de conexión (30 segundos)
        hikariConfig.setIdleTimeout(600000); // Timeout de inactividad (10 minutos)
        hikariConfig.setMaxLifetime(1800000); // Tiempo máximo de vida de una conexión (30 minutos)
        hikariConfig.setValidationTimeout(5000); // Timeout de validación (5 segundos)
        hikariConfig.setLeakDetectionThreshold(0); // Umbral de detección de fugas (desactivado)

        return new HikariDataSource(hikariConfig);
    }
}