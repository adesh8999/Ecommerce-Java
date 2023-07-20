package springboot.mysql.SpringBootRestApi.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import springboot.mysql.SpringBootRestApi.dto.EmailDetails;
import springboot.mysql.SpringBootRestApi.entity.Order;
import springboot.mysql.SpringBootRestApi.entity.OrderItem;
import springboot.mysql.SpringBootRestApi.service.EmailService;


@Service
public class EmailServiceImpl implements EmailService {
	 
    @Autowired
    private JavaMailSender javaMailSender;
 
    @Value("${spring.mail.username}")
    private String sender;
 
    // Method 1
    // To send a simple email
    @Override
    public String sendSimpleMail(EmailDetails details)
    {
 
        // Try block to check for exceptions
        try {
 
//            // Creating a simple mail message
//            SimpleMailMessage mailMessage
//                = new SimpleMailMessage();
 
            // Setting up necessary details
//            mailMessage.setFrom(sender);
//            mailMessage.setTo(details.getRecipient());
//            mailMessage.setText(details.getMsgBody());
//            mailMessage.setSubject(details.getSubject());
            
            List<Order> ord = details.getOrderList();
            String name = "";
            Date date = new Date();
            String address = "";
            for (Order order : ord) {
				 name = order.getCustomerName();
				 date = order.getDateCreated();
				 address = order.getCustomerAddress();
				System.out.println(name + " " + date + " " + address);
			}
            
           
            
            
            String header = "<!DOCTYPE html>\r\n"
            		+ "<html lang = \"en\">\r\n"
            		+ "<body>\r\n"
            		+ "\r\n"
            		+ "<p>Name : "+name +"</p>\r\n"
            		+ "<p>Date : "+date +"</p>\r\n"
            		+ "<p>Address : "+address +"</p>\r\n"
            		+ "\r\n"
            		+ "<table style=\"width:100%;  border:1px solid black;\">\r\n"
            		+ "  <tr>\r\n"
            		+ "    <th style=\" border:1px solid black;\">Product Name  </th>\r\n"
            		+ "    <th style=\" border:1px solid black;\">Quantity  </th>\r\n"
            		+ "    <th style=\" border:1px solid black;\">Price  </th>\r\n"
            		+ "    <th style=\" border:1px solid black;\">Total Amount  </th>\r\n"
            		+ "  </tr>\r\n";
            
            Set<OrderItem> ordItems = details.getOrderItems();
            
            String content = "";
            
            for (OrderItem orderItems : ordItems) {
            	
				content += 
			
            		 "  <tr>\r\n"
            		+ "    <td style=\" border:1px solid black;\">  "+orderItems.getName()+" </td>\r\n"
            		+ "    <td style=\" border:1px solid black; text-align:centre\">  "+orderItems.getQuantity()+"</td>\r\n"
            		+ "    <td style=\" border:1px solid black;\">  "+orderItems.getPrice()+"</td>\r\n"
            		+ "    <td style=\" border:1px solid black;\"> "+orderItems.getTotalAmount()+"</td>\r\n"
            		+ "  </tr>\r\n";
            		
            }
            
            String footer = 
            		 "</table>\r\n"
            		+ "\r\n"
            		+ "<p>This is Footer</p>\r\n"
            		+ "\r\n"
            		+ "</body>\r\n"
            		+ "</html>";
            
            String message = header.concat(content).concat(footer);
            
            // to send HTML mail
            
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            
            // setting up necessary details
            boolean html = true;
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody()+message,html);
            mimeMessageHelper.setSubject(details.getSubject());
            
 
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail Sent Successfully...";
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
 
    // Method 2
    // To send an email with attachment
    @Override
    public String sendMailWithAttachment(EmailDetails details)
    {
        // Creating a mime message
        MimeMessage mimeMessage
            = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
 
        try {
 
            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                details.getSubject());
            
            
           
 
            // Adding the attachment
            FileSystemResource file
                = new FileSystemResource(
                    new File(details.getAttachment()));
 
            mimeMessageHelper.addAttachment(
                file.getFilename(), file);
 
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }
 
        // Catch block to handle MessagingException
        catch (MessagingException e) {
 
            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }

//	@Override
//	public void sendEmail(String to, String subject, String text) {
//		
//		 SimpleMailMessage mailMessage
//         = new SimpleMailMessage();
//
//     // Setting up necessary details
//     mailMessage.setFrom(sender);
//     mailMessage.setTo(to);
//     mailMessage.setText(text);
//     mailMessage.setSubject(subject);
//     
//     // Sending the mail
//     javaMailSender.send(mailMessage);
//     
//	}   
   

	
}