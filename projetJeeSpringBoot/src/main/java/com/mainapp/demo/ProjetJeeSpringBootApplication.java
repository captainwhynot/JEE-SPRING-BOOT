package com.mainapp.demo;


import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mainapp.entity.Customer;
import com.mainapp.entity.Test;
//import com.mainapp.service.CustomerService;
import com.mainapp.service.TestService;
import com.mainapp.service.UserService;
@SpringBootApplication(scanBasePackages = "com.mainapp")
@EnableJpaRepositories(basePackages = "com.mainapp")
@EntityScan(basePackages = "com.mainapp.entity")
public class ProjetJeeSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetJeeSpringBootApplication.class, args);
		
		//ModeratorRepository mr = new ModeratorRepos;
		
		//System.out.println(UserService.getUser(1));
		
		
	}

	@Bean
    public CommandLineRunner demo(TestService s) {
        return (args) -> {
        	try {
        		System.out.println("TEST");
                
             	
             	//Ajouter un test
        		//Test test = new Test("test1",10.1);		
        		//s.saveTest(test);
        		
        		
        		
        		//Trouver tous les tests
        		//List<Test> tests = s.getAllTest();
        		
        		//trouver 1 test
        		//Test test = s.findById(2);
        		
        		//Update un test
        		//s.updateTest(test, 9, "NOOOM");
        		//System.out.println(s.findById(2).getName()+" ---- "+s.findById(2).getPrice());
        		
        		
        		
        		//s.deleteTest(1);
        	}catch(Exception e) {
        		e.printStackTrace();
        		}
        	
        };
    }

}
