package com.mainapp.demo;


import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mainapp.entity.*;
import com.mainapp.service.*;
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
    public CommandLineRunner demo(TestService ts, UserService us) {
        return (args) -> {
        	try {
        		System.out.println("TEST");
                
                // Créer un client
                Customer customer = new Customer("customer@email.com", "password", "CustomerUser");
                us.saveUser(customer);
                
                User user = us.getUser(1);
                System.out.println(user.getEmail());
                
             	//Ajouter un test
        		Test test = new Test("test1",10.1);		
        		ts.saveTest(test);
        		        		
        		//Trouver tous les tests
        		List<Test> tests = ts.getAllTest();
        		
        		//trouver 1 test
        		Test test1 = ts.findById(1);

        		//Update un test
        		ts.updateTest(test, 9, "NOOOM");
        		System.out.println(ts.findById(2).getName()+" ---- "+ts.findById(2).getPrice());
        		
        		
        		//s.deleteTest(1);
        	}catch(Exception e) {
        		e.printStackTrace();
        		}
        	
        };
    }

}
