package com.mainapp.demo;

import java.util.Date;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mainapp.entity.*;
import com.mainapp.service.*;

/**
 * The main class for the ProjetJeeSpringBoot application.
 * This class serves as the entry point for the Spring Boot application.
 * It initializes and runs the application, populates the database with demo data,
 * and demonstrates various functionalities.
 */
@SuppressWarnings("deprecation")
@SpringBootApplication(scanBasePackages = "com.mainapp")
@EnableJpaRepositories(basePackages = "com.mainapp")
@EntityScan(basePackages = "com.mainapp.entity")
public class ProjetJeeSpringBootApplication {

    /**
     * The main method that serves as the entry point for the Spring Boot application.
     *
     * @param args The command line arguments.
     */
	public static void main(String[] args) {
		SpringApplication.run(ProjetJeeSpringBootApplication.class, args);
	}

    /**
     * CommandLineRunner bean for initializing and demonstrating application functionalities.
     *
     * This bean is executed on application startup and performs various actions such as creating users, customers, moderators, products, credit cards, and baskets. It simulates the order and payment processes.
     *
     * @param us UserService for user-related operations.
     * @param cs CustomerService for customer-related operations.
     * @param ms ModeratorService for moderator-related operations.
     * @param ps ProductService for product-related operations.
     * @param ccs CreditCardService for credit card-related operations.
     * @param bs BasketService for basket-related operations.
     * @return A CommandLineRunner instance for the Spring Boot application.
     */
    @Bean
    CommandLineRunner initializeDatabase(UserService us, CustomerService cs, ModeratorService ms, ProductService ps, CreditCardService ccs, BasketService bs) {
        return (args) -> {
        	try {              
        		// Créer un administrateur
                Administrator admin = new Administrator("mailAdmin",  BCrypt.hashpw("password", BCrypt.gensalt(12)), "Admin");
                us.saveUser(admin);
                
                // Créer et modifier un modo
        		Moderator moderator = new Moderator("mailModo", BCrypt.hashpw("password", BCrypt.gensalt(12)), "modo");
        		us.saveUser(moderator);
        		Moderator modo = ms.getModerator("mailModo");
        		System.out.println(modo.getEmail());
        		System.out.println(ms.modifyRight(modo, "add_product", true));
        		
        		moderator = new Moderator("nie", BCrypt.hashpw("password", BCrypt.gensalt(12)), "nie");
        		System.out.println(us.saveUser(moderator));
        		modo = ms.getModerator("nie");
        		
        		User user = us.getUser("mailAdmin");
        		
                // Créer un client
        		Customer customer = new Customer("mailCustomer", BCrypt.hashpw("password", BCrypt.gensalt(12)), "chris");
        		System.out.println(us.saveUser(customer));
        		Customer cust = cs.getCustomer("mailCustomer");
        		List<Customer> liste = cs.getCustomerList();
        		
        		//Modifier un client
        		System.out.println(cust.getUsername());
        		System.out.println(liste.get(0).getUsername());
        		System.out.println(cs.setFidelityPoint(cust, 10));
        		System.out.println(cs.setFidelityPoint(cust, -5));
                
        		//Créer une carte bancaire
				CreditCard card = new CreditCard(123, 111, new Date(2024 - 1900, 12 - 1, 20));
        		card.setCredit(1000);
        		System.out.println(ccs.saveCreditCard(card));
                
        		//Créer et modifier un produit
        		Product product = new Product("DragonBall Tome 1", 7.95, 101, "img/products/db_1.jpg", user);
        		System.out.println(ps.addProduct(product));
        		
        		product = new Product("Naruto Tome 1", 7.95, 100, "img/products/naruto_1.jpg", user);
        		System.out.println(ps.addProduct(product));
        		
        		product = new Product("One Piece Tome 1", 7.95, 100, "img/products/op_1.jpg", user);
        		System.out.println(ps.addProduct(product));
        		
        		product = new Product("L'Attaque des Titans Tome 1", 5.95, 17, "img/products/snk_1.jpg", modo);
        		System.out.println(ps.addProduct(product));
        		
        		product = new Product("L'Attaque des Titans Tome 30", 6.55, 9, "img/products/snk_30.jpg", modo);
        		System.out.println(ps.addProduct(product));
        		
        		product = new Product("Vinland Saga Tome 6", 6.55, 12, "img/products/vs_6.jpg", modo);
        		System.out.println(ps.addProduct(product));
        		
        		product = new Product("Hunter X Hunter Tome 37", 6.99, 19, "img/products/hxh_37.jpg",modo);
        		System.out.println(ps.addProduct(product));
        		
        		product = new Product("Jujutsu Kaisen Tome 0", 7.99, 4, "img/products/jjk_0.jpg", modo);
        		System.out.println(ps.addProduct(product));
        		
        		moderator = new Moderator("UnLibraire", BCrypt.hashpw("password", BCrypt.gensalt(12)), "UnLibraire");
        		System.out.println(us.saveUser(moderator));
        		modo = ms.getModerator("UnLibraire");
        		
        		product = new Product("My Hero Academia Tome 25", 6.95, 13, "img/products/mha_25.jpg", modo);
        		System.out.println(ps.addProduct(product));
        		
        		product = new Product("Hunter X Hunter Tome 1", 6.90, 11, "img/products/hxh_1.jpg", modo);
        		System.out.println(ps.addProduct(product));
        		
        		product = new Product("Fire Punch Tome 1", 6.90, 7, "img/products/fp_1.jpg", modo);
        		System.out.println(ps.addProduct(product));
        		
        		product = new Product("Blue Lock Tome 1", 6.90, 8, "img/products/bl_1.jpg", modo);
        		System.out.println(ps.addProduct(product));
        		
        		product = new Product("Chainsaw Man Tome 14", 7.00, 22, "img/products/csm_14.jpg", modo);
        		System.out.println(ps.addProduct(product));
        		
        		//Créer un panier
        		Basket basket = new Basket(product, 2, cust);

        		//Modifier et payer un panier
        		System.out.println(bs.addOrder(basket, cust.getId(), 1));
        		System.out.println(bs.updateQuantity(basket.getId(), 2));
        		System.out.println(bs.confirmOrder(cust.getId()));
        		if (ccs.checkCreditCard(123, 111, new Date(2023 - 1900, 12 - 1, 20))) {
        			if (ccs.checkBalance(123, bs.totalPrice(cust.getId()))) {
        				System.out.println(bs.finalizePaiement(cust.getId(), 123, bs.totalPrice(cust.getId()),"testMain"));
        			}
        		}
        	} catch(Exception e) {
        		e.printStackTrace();
        	}
        };
    }
}
