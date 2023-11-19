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

@Service
public class UserService {
	
	private UserRepository ur;

	@Autowired
	public void setDependencies(UserRepository ur) {
		this.ur = ur;
	}

	public UserRepository getUr() {
		return ur;
	}

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
	
	public User getUser(String email) {
		return ur.getUser(email);
	}
	
	public User getUser(int id) {
		return ur.getUser(id);
	}
	
	public boolean updateUser(User user, String email, String username, String password) {
		int update = ur.updateUser(user, email, username, password);
		return (update > 0);
	}
	
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
	        
			//Create profil folder if it does not exist
			File saveDir = new File(savePath);
	        if (!saveDir.exists()) {
	            saveDir.mkdirs();
	        }

	        //Save the profile picture in the folder
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
	
	public boolean deleteProfilePicture(User user, String savePath) {
		try {
			int userId = user.getId();

	        File imgDir = new File(savePath);
	        File[] files = imgDir.listFiles((dir, name) -> name.startsWith(userId + "_"));
	        
	        //Delete all the old profile picture of the user
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
	
	public boolean checkUserMail(User user) {
		return (ur.checkMailUser(user.getEmail()).isEmpty());
	}
	
	public List<User> getAllNoAdministrator() {
		return ur.getAllNoAdministrator();
	}
	
	public boolean checkUserLogin(String email, String password) {
		try {
			User user = ur.getUser(email);
			return BCrypt.checkpw(password, user.getPassword());
		} catch(Exception e){
			return false;
		}
	}
	
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
