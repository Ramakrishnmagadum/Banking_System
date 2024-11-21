package org.jsp.banking_system.helper;
import org.jsp.banking_system.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class mailverification {
	@Autowired
	//mail sender  depedency  through that we get this one
	JavaMailSender mailsender;
	public void sendmail(Customer customer) {
		MimeMessage mimemessage	=mailsender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mimemessage);
		try {
			helper.setFrom("ramakrishnaaaaa815@gmail.com");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		try {
			helper.setTo(customer.getEmail());
		} catch (MessagingException e) {
			e.printStackTrace();
		}try {
			helper.setSubject("mail verification");
		} catch (MessagingException e) {
			e.printStackTrace();
		}try {
			helper.setText("your otp for email verification is "+customer.getOtp());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mailsender.send(mimemessage);
	}

}
