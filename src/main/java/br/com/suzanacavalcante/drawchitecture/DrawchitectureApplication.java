package br.com.suzanacavalcante.drawchitecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.boot.SpringApplication.*;

@SpringBootApplication
@EnableScheduling
public class DrawchitectureApplication {

	public static void main(String[] args) {
		run(DrawchitectureApplication.class, args);
	}

}
