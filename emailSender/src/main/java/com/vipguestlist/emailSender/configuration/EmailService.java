package com.vipguestlist.emailSender.configuration;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	public void sendEmail(String name, String to) {
		
		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("igorgrv1@gmail.com", "Oper11Daf12"));
			email.setSSLOnConnect(true);
			
			email.setFrom("igorgrv1@gmail.com");
			email.setSubject("VIP List - Igor's Project");
			email.setMsg("Hello " + name + ", you have been added to the VIP list! Congrats");
			email.addTo(to);
			email.send();
			System.out.println("Email sended to " + name + " - email: " + to);
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
