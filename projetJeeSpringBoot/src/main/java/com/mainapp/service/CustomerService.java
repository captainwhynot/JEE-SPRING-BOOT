package com.mainapp.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainapp.entity.Customer;
import com.mainapp.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository cr;
	
	public void addCustomer(Customer customer) {
		cr.save(customer);
	}
	
	public Customer getCustomer(int idCustomer) {
		return cr.getCustomer(idCustomer);
	}
	
	
	
	public List<Customer> findAll(){
		return cr.findAll();
	}
}
