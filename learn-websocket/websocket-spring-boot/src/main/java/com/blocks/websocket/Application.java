package com.blocks.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

//@Configuration
//@EnableAutoConfiguration
@EnableScheduling
@SpringBootApplication
public class Application extends SpringBootServletInitializer{
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
    	SpringApplication.run(Application.class, args);
    	
    }
}
