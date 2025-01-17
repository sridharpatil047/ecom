package me.sridharpatil.ecom.notificationservice.utils;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.UnsupportedEncodingException;
import java.util.Date;


@Getter @Setter
@Log4j2
public class EmailUtil {

    public static void sendEmail(Session session,
                                 String Username,
                                 String fromEmail,
                                 String toEmail,
                                 String subject,
                                 String body
    ) throws MessagingException, UnsupportedEncodingException {

        MimeMessage msg = new MimeMessage(session);

        //set message headers
        log.trace("Setting email headers");
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        log.trace("Setting email properties");
        msg.setFrom(new InternetAddress(fromEmail, Username));
        msg.setReplyTo(InternetAddress.parse(fromEmail, false));
        msg.setSubject(subject, "UTF-8");
        msg.setText(body, "UTF-8");
        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

        log.trace("Sending email to " + toEmail);
        Transport.send(msg);
        log.trace("Email sent successfully!!");
    }
}
