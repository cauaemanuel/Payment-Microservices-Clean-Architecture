package com.payment_processor_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableRabbit
@EnableDiscoveryClient
public class PaymentProcessorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentProcessorServiceApplication.class, args);
	}

}
