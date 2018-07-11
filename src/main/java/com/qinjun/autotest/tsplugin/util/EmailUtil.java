package com.qinjun.autotest.tsplugin.util;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtil {
    private MimeMessage mimeMessage;
    private Session session;
    private String host;
    private int port;
    private String userName;
    private String password;
    private Properties props;

    public EmailUtil(String host, int port, String userName, String password) {
        this.host= host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.props = new Properties();
        this.props.put("mail.smtp.host",host);
        this.props.put("mail.smtp.port",port);
        this.props.put("mail.smtp.auth","true");
        this.props.put("mail.smtp.ssl.enable","true");
        this.session = Session.getDefaultInstance(props,null);
        this.mimeMessage = new MimeMessage(session);

    }

    public void send() throws MessagingException {
        setAddresser(userName);
        Transport transport = session.getTransport("smtp");
        transport.connect(host,port,userName,password);
        transport.sendMessage(mimeMessage,mimeMessage.getRecipients(MimeMessage.RecipientType.TO));
        transport.close();
    }

    private void setAddresser(String addresser) {
        try {
            mimeMessage.setFrom(new InternetAddress(addresser));
            mimeMessage.saveChanges();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    public void setReceiver(String receiver) {
        try {
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO,InternetAddress.parse(receiver));
            mimeMessage.saveChanges();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    public void setCc(String cc) {
        try {
            mimeMessage.setRecipients(MimeMessage.RecipientType.CC,InternetAddress.parse(cc));
            mimeMessage.saveChanges();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    public void setSubject(String subject) {
        try {
            mimeMessage.setSubject(subject);
            mimeMessage.saveChanges();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    public void setBody(String body) {
        try {
            BodyPart  bp = new MimeBodyPart();
            bp.setContent(body,"text/html;charset=UTF-8");
            MimeMultipart mmp = new MimeMultipart();
            mmp.addBodyPart(bp);
            mimeMessage.setContent(mmp);
            mimeMessage.saveChanges();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

}
