package com.correoE;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;




public class enviarCorreo {

    //https://www.oodlestechnologies.com/blogs/Send-Mail-in-Android-without-Using-Intent

public static void enviarCorreoE(final String correoUsuario, final String passEncontrada){

    new Thread(new Runnable() {

        public void run() {

            try {

                GMailSender sender = new GMailSender("solvotg@gmail.com","seminario1234");
                //GMailSender sender = new GMailSender(correoUsuario,"seminario1234");
                //sender.addAttachment(Environment.getExternalStorageDirectory().getPath()+"/image.jpg");
                sender.sendMail("SOLVO - INFORMACIÓN", "Hola, tu contraseña es -->"+passEncontrada,
                        "solvotg@gmail.com",
                        correoUsuario);

            } catch (Exception e) {

                System.out.println("Error-->"+e);
                //Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }

        }

    }).start();
}

/*    public static String enviarMail(){
        final String username = "solvotg@gmail.com";
        final String password = "seminario1234";
        String respuesta = "No se Envio";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.connectiontimeout", "t1");
        props.put("mail.smtp.timeout", "t2");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("solvotg@gmail.com"));//from-email
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("solvotg@gmail.com"));//to-email
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            String file = "path of file to be attached";
            String fileName = "attachmentName";
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            respuesta = "Done";
            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return respuesta;
    }*/
}
