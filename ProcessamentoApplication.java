package com.job.processamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProcessamentoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProcessamentoApplication.class, args);
    }
}