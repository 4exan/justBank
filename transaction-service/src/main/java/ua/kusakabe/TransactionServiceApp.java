package ua.kusakabe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TransactionServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(TransactionServiceApp.class, args);
    }
}
