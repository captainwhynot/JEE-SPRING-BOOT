package com.mainapp.service;

import com.mainapp.entity.Administrator;
import com.mainapp.repository.AdministratorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
	
	private AdministratorRepository ar;

	@Autowired
	public void setDependencies(AdministratorRepository ar) {
		this.ar = ar;
	}
	
	public Administrator getAdministrator(String email) {
		return ar.getAdministrator(email);
	}
	
	public Administrator getAdministrator(int id) {
		return ar.getAdministrator(id);
	}
}
