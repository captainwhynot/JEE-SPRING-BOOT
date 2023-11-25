package com.mainapp.service;

import com.mainapp.entity.Administrator;
import com.mainapp.repository.AdministratorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Administrator entities.
 * Handles interactions between the application and the AdministratorRepository.
 */
@Service
public class AdministratorService {
	
	private AdministratorRepository ar;

    /**
     * Sets the dependencies for the service.
     *
     * @param administratorRepository The AdministratorRepository to be injected.
     */
	@Autowired
	public void setDependencies(AdministratorRepository ar) {
		this.ar = ar;
	}

    /**
     * Retrieves an administrator by their email address.
     *
     * @param email The email address of the administrator.
     * @return The administrator associated with the specified email address.
     */
	public Administrator getAdministrator(String email) {
		return ar.getAdministrator(email);
	}

    /**
     * Retrieves an administrator by their ID.
     *
     * @param id The ID of the administrator.
     * @return The administrator associated with the specified ID.
     */
	public Administrator getAdministrator(int id) {
		return ar.getAdministrator(id);
	}
}
