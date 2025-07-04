package com.comptamaroc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {
    "com.comptamaroc.auth",
    "com.comptamaroc.config",
    "com.comptamaroc.exception",
    "com.comptamaroc.core",
    "com.comptamaroc.dashboard",
    "com.comptamaroc.clients",
    "com.comptamaroc.suppliers", 
    "com.comptamaroc.products",
    "com.comptamaroc.invoices",
    "com.comptamaroc.accounting",
    "com.comptamaroc.tva",
    "com.comptamaroc.salaries",
    "com.comptamaroc.reports",
    "com.comptamaroc.ai",
    "com.comptamaroc.settings"
})
@EnableJpaAuditing
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
