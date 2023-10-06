package net.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "net")
@EntityScan("net")
public class RealidadAumentadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealidadAumentadaApplication.class, args);
	}

}
