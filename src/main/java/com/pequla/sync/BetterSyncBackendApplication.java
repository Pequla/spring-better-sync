package com.pequla.sync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BetterSyncBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BetterSyncBackendApplication.class, args);
    }

}
