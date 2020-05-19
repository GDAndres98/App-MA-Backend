package app.ma.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import app.ma.entities.Role;
import app.ma.repositories.RoleRepository;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan (basePackages="app.ma.back,app.ma.repositories,app.ma.entities,app.ma.services")
@EntityScan(basePackages = {"app.ma.entities"}) 
@EnableJpaRepositories ("app.ma.repositories")
public class AppmaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AppmaApplication.class, args);		
	}



}
