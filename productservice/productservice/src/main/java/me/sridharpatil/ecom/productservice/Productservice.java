package me.sridharpatil.ecom.productservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class Productservice {

    public static void main(String[] args) {

        SpringApplication.run(Productservice.class, args);
        log.info("Application started with INFO level logging");
//        log.error("Application started with ERROR level logging");
        log.debug("Application started with DEBUG level logging");

    }

}
