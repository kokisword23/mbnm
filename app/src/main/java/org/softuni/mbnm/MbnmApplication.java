package org.softuni.mbnm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MbnmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MbnmApplication.class, args);
    }

}
