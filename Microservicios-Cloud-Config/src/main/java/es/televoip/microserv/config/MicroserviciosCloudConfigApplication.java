package es.televoip.microserv.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class MicroserviciosCloudConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosCloudConfigApplication.class, args);
	}

}
