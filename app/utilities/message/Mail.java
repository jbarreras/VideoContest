package utilities.message;

import play.Logger;
import play.Play;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {

    public static void sendMail(String to, String message, String subject) {
        try {
            Properties properties = System.getProperties();
            Session getMailSession = Session.getDefaultInstance(properties, null);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            MimeMessage mail = new MimeMessage(getMailSession);
            mail.addRecipients(Message.RecipientType.TO, to);
            mail.setSubject(subject);
            mail.setContent(message, "text/html");

            Transport service = getMailSession.getTransport("smtp");
            service.connect("smtp.gmail.com", Play.application().configuration().getString("videoconverter.mailUser"), Play.application().configuration().getString("videoconverter.mailPassword"));
            service.sendMessage(mail, mail.getAllRecipients());
            service.close();
        } catch (Exception ex) {
            Logger.error("Mail.sendMail " + ex.getMessage().toString());
        }
    }
}