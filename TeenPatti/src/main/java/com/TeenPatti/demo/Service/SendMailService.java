package com.TeenPatti.demo.Service;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.TeenPatti.demo.Entity.UserEntity;

@Service
public class SendMailService {
	
	private final TemplateEngine templateEngine;
	
	private final JavaMailSender javaMailSender;
	
	public SendMailService(TemplateEngine templateEngine,JavaMailSender javaMailSender) {
		 this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

	  @Async
	  public String sendMail(UserEntity user) throws MessagingException {
	        Context context = new Context();
	        context.setVariable("user", user);

	        String process = templateEngine.process("email/VerficationPage", context);
	        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
	        helper.setSubject("Welcome Subject " + user.getUsername());
	        helper.setText(process, true);
	        helper.setTo(user.getEmail());
	        
	        helper.setFrom("sample@dolszewski.com");
//	        helper.addInline("logo", new ClassPathResource("static/img/logo.png"), "image/png");
	        
	        javaMailSender.send(mimeMessage);
	        return "Sent";
	    }

}
