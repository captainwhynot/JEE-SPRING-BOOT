package com.mainapp.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainapp.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository cr;
}
