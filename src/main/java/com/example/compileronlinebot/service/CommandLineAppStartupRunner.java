package com.example.compileronlinebot.service;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final DataSource dataSource;

    public CommandLineAppStartupRunner( DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void run(String... args) {
        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();

    }
}
