package dhbw.server.services;

import dhbw.server.entities.Nutzer;
import dhbw.server.repositories.NutzerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ScheduledFuture;


@Component
public class SchedulerServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    @Autowired
    private TaskScheduler taskScheduler;
    @Autowired
    private SchedulerServiceImpl schedulerService;
    @Autowired
    private UserService userService;

    private ArrayList<Nutzer> nutzerArrayList = new ArrayList<>();
    private ScheduledFuture job1;
    private boolean initial = true;

    public synchronized void scheduleJob(int period) throws NoSuchMethodException {
        if (job1 != null) {
            job1.cancel(true);
        }

        job1 = taskScheduler.scheduleAtFixedRate(new ScheduledMethodRunnable(
                schedulerService, "job"), period);
    }

    public void job() throws MessagingException {
        try {
            if (initial) {
                Nutzer nutzer = nutzerArrayList.get(0);
                userService.addEditorRole(nutzer.getNut_id());
                initial = false;
                String name = nutzer.getNut_vorname() + " " + nutzer.getNut_nachname();
                sendEmail(nutzer.getNut_email(), nutzer.getNut_anrede(), name);
                System.out.println(nutzerArrayList.get(0).getNut_email());
            } else {
                Integer nutzerId = nutzerArrayList.get(0).getNut_id();
                userService.removeEditorRole(nutzerId);
                nutzerArrayList.remove(0);

                Nutzer newNutzer = nutzerArrayList.get(0);
                userService.addEditorRole(newNutzer.getNut_id());
                String name = newNutzer.getNut_vorname() + " " + newNutzer.getNut_nachname();
                sendEmail(newNutzer.getNut_email(), newNutzer.getNut_anrede(), name);
                System.out.println(nutzerArrayList.get(0).getNut_email());
            }
        } catch (IndexOutOfBoundsException e) {
            job1.cancel(true);
        }
    }

    private void sendEmail(String email, String anrede, String name) throws MessagingException {

        final String username = "dhbwplanner@gmail.com";
        final String password = "dhbwplanner_1234";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("from@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Beginnen Sie mit der Vorlesungsplanung");

        String msg = "Sehr geehrte/r " + anrede + " " + name + "," + "\n" + "\n"
                + "bitte beginnen Sie mit ihrer Planung." ;

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);

    }

    public ArrayList<Nutzer> getNutzerArrayList() {
        return nutzerArrayList;
    }

    public void setNutzerArrayList(ArrayList<Nutzer> nutzerArrayList) {
        this.nutzerArrayList = nutzerArrayList;
    }
}
