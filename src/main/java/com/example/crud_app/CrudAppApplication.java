package com.example.crud_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudAppApplication {

	public static void main(String[] args) {
    // Redirect SSL debug logs to standard output
    System.setErr(System.out);
    SpringApplication.run(CrudAppApplication.class, args);
}


}
