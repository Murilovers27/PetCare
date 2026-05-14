package com.patcare.facens.conexaoBD;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Conexão{

    @Bean
    CommandLineRunner testarConexao(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("SUBIU ESSA MERDA");
            }
        };
    }
}
