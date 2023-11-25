package com.mainapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mainapp.entity.*;
import com.mainapp.repository.UserRepository;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mindrot.jbcrypt.*;

/**
 * Service class for managing User entities.
 * Handles interactions between the application and the UserRepository.
 */
@Service
public class UserService {
	
	private UserRepository ur;

    /**
     * Sets the UserRepository dependency for the service.
     *
     * @param ur The UserRepository to be injected.
     */
	@Autowired
	public void setDependencies(UserRepository ur) {
		this.ur = ur;
	}

    /**
     * Retrieves the UserRepository associated with this service.
     *
     * @return The UserRepository associated with this service.
     */
	public UserRepository getUr() {
		return ur;
	}

    /**
     * Saves a new User to the system based on the user type.
     *
     * @param user The User object to be saved.
     * @return True if the save operation is successful, false otherwise.
     */
	public boolean saveUser(User user) {
		if(checkUserMail(user)) {
			try {
				switch (user.getTypeUser()) {
					case "Administrator" :
						Administrator admin = new Administrator(user.getEmail(), user.getPassword(), user.getUsername());
						ur.save(admin);
						break;
					case "Customer" :
						Customer customer = new Customer(user.getEmail(), user.getPassword(), user.getUsername());
						ur.save(customer);
						break;
					case "Moderator" :
						Moderator moderator = new Moderator(user.getEmail(), user.getPassword(), user.getUsername());
						ur.save(moderator);
						break;
				}
				return true;
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}

    /**
     * Retrieves a User by email.
     *
     * @param email The email associated with the User.
     * @return The User object associated with the given email.
     */
	public User getUser(String email) {
		return ur.getUser(email);
	}

    /**
     * Retrieves a User by ID.
     *
     * @param id The ID of the User.
     * @return The User object associated with the given ID.
     */
	public User getUser(int id) {
		return ur.getUser(id);
	}

    /**
     * Updates the details of an existing User.
     *
     * @param user     The User object to be updated.
     * @param email    The new email for the user.
     * @param username The new username for the user.
     * @param password The new password for the user.
     * @return True if the update is successful, false otherwise.
     */
	public boolean updateUser(User user, String email, String username, String password) {
		int update = ur.updateUser(user, email, username, password);
		return (update > 0);
	}

    /**
     * Updates the profile picture of a User.
     *
     * @param user      The User object for which the profile picture is to be updated.
     * @param imgFile   The MultipartFile representing the new profile picture file.
     * @param savePath  The path to save the profile picture file.
     * @return True if the update is successful, false otherwise.
     */
	public boolean updateProfilePicture(User user, MultipartFile imgFile, String savePath) {
		try {
			int userId = user.getId();

	        File imgDir = new File(savePath);
	        File[] files = imgDir.listFiles((dir, name) -> name.startsWith(userId + "_"));
	        
	        if (files != null) {
	            for (File file : files) {
	                file.delete();
	            }
	        }
	        
			// Create profil folder if it does not exist
			File saveDir = new File(savePath);
	        if (!saveDir.exists()) {
	            saveDir.mkdirs();
	        }

	        // Save the profile picture in the folder
	        String profilePicture = user.getId() + "_" + imgFile.getOriginalFilename();
			Path imgFilePath = Paths.get(savePath).resolve(profilePicture);
			imgFile.transferTo(imgFilePath.toFile());
			
			profilePicture = "img/Profil/" + profilePicture;
			int update = ur.updateProfilePicture(userId, profilePicture);
			return (update > 0);
		} catch (Exception e) {
			return false;
		}
	}

    /**
     * Deletes the profile picture of a User.
     *
     * @param user      The User object for which the profile picture is to be deleted.
     * @param savePath  The path to save associated files during the deletion.
     * @return True if the deletion is successful, false otherwise.
     */
	public boolean deleteProfilePicture(User user, String savePath) {
		try {
			int userId = user.getId();

	        File imgDir = new File(savePath);
	        File[] files = imgDir.listFiles((dir, name) -> name.startsWith(userId + "_"));
	        
	        // Delete all the old profile picture of the user
	        if (files != null) {
	            for (File file : files) {
	                file.delete();
	            }
	        }

	        int update = ur.updateProfilePicture(userId, "img/profilePicture.png");
			return (update > 0);
		} catch (Exception e) {
			return false;
		}
	}

    /**
     * Checks if the email of a User is unique.
     *
     * @param user The User object for which the email uniqueness is to be checked.
     * @return True if the email is unique, false otherwise.
     */
	public boolean checkUserMail(User user) {
		return (ur.checkMailUser(user.getEmail()).isEmpty());
	}

    /**
     * Retrieves a list of all non-administrator User entities.
     *
     * @return A list of all non-administrator User entities.
     */
	public List<User> getAllNoAdministrator() {
		return ur.getAllNoAdministrator();
	}

    /**
     * Checks the login credentials of a User.
     *
     * @param email    The email associated with the User.
     * @param password The password to be checked.
     * @return True if the login is successful, false otherwise.
     */
	public boolean checkUserLogin(String email, String password) {
		try {
			User user = ur.getUser(email);
			return BCrypt.checkpw(password, user.getPassword());
		} catch(Exception e){
			return false;
		}
	}

    /**
     * Sends an email to the specified address.
     *
     * @param email    The email address to which the email is sent.
     * @param object   The subject of the email.
     * @param container The content of the email.
     * @return True if the email is sent successfully, false otherwise.
     */
	public boolean sendMail(String email, String object, String container) {
        String siteMail = "mangastorejee2023@gmail.com";
        String password = "huwc xvtz rxiy xbqf";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(siteMail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(siteMail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(object);
            message.setContent(container, "text/html");

            Transport.send(message);
            return true;

        } catch (Exception e) {
            return false;
        } 
	}
}
