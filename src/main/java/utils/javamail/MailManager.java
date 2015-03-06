package utils.javamail;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by jzhang on 20/02/2015.
 */
public class MailManager {
    private final static String MAILER_VERSION = "Java";
    private static Properties prop = System.getProperties();

    public MailManager(String serveur, String login, String passwd) {
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.host", serveur);
 //       prop.setProperty("mail.user", login);
 //       prop.setProperty("mail.password", passwd);
    }

    public boolean envoyerMailSMTP(String from, List<String> to, String subject, StringBuffer text) {
        boolean result = false;
        try {
            prop.setProperty("mail.from", from);
            Session session = Session.getInstance(prop);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            InternetAddress[] internetAddresses = new InternetAddress[to.size()];
            for (int i=0; i < to.size(); i++)
                internetAddresses[i] = new InternetAddress(to.get(i));
            message.setRecipients(Message.RecipientType.TO,internetAddresses);
            message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
            message.setText(text.toString());
            message.setHeader("X-Mailer", MAILER_VERSION);
            message.setSentDate(new Date());
            Transport.send(message);
            result = true;
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

}
