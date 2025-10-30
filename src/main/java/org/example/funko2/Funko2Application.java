package org.example.funko2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Funko2Application {
    public static void main(String[] args) {
        SpringApplication.run(Funko2Application.class, args);

    }

}
