package com.mainapp.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainapp.entity.*;
import com.mainapp.repository.AdministratorRepository;
import com.mainapp.repository.CustomerRepository;
import com.mainapp.repository.ModeratorRepository;
import com.mainapp.repository.UserRepository;

import jakarta.servlet.http.Part;

import java.io.*;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mindrot.jbcrypt.*;

@Service
public class UserService {
	
	@Autowired
	private UserRepository ur;
	@Autowired
	private ModeratorRepository mr;
	
	@Autowired
	private AdministratorRepository ar;
	
	@Autowired
	private CustomerRepository cr;
	
	
	public User getUser(String email) {
		return ur.getUser(email);
	}
	
	public User getUser(int id) {
		return ur.getUser(id);
	}
	
	public void updateUser(User user, String email, String username, String password) {
		ur.updateUser(user, email, username, password);
	}
	
	public boolean checkUserMail(User user) {
		if(ur.checkMailUser(user).isEmpty() == false)
			return false;
		else
			return true;
	}
	
	public boolean checkUserLogin(String email, String password) {
		try {
			boolean isAuthentificated= false;
			User user = this.getUser(email);
			isAuthentificated = BCrypt.checkpw(password, user.getPassword());

			return isAuthentificated;
		}catch(Exception e){
			return false;
			}
	}
	
	public void saveUser(User user) {
		if(ur.checkMailUser(user).isEmpty() == false) {
			try {
				switch (user.getTypeUser()) {
					case "Administrator" :
						Administrator admin = new Administrator(user.getEmail(), user.getPassword(), user.getUsername());
						//save = (Integer) session.save(admin);
						ar.addAdministrator(admin);
						
						break;
					case "Customer" :
						Customer customer = new Customer(user.getEmail(), user.getPassword(), user.getUsername());
						cr.addCustomer(customer);
						break;
					case "Moderator" :
						Moderator moderator = new Moderator(user.getEmail(), user.getPassword(), user.getUsername());
						mr.addModerator(moderator);
						break;
				}
		} catch(Exception e) {
			}
		}
		
	}
	
	public void updateProfilePicture(User user, Part filePart, String profilePicture, String savePath) {
		
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
