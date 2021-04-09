package dhbw.server.services;

import dhbw.server.entities.Nutzer;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import dhbw.server.repositories.Vorlesung_Von_NutzerRepository;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired
    private Vorlesung_Von_NutzerRepository vorlesungVonNutzerRepository;

    private ArrayList<Nutzer> nutzerArrayList = new ArrayList<>();
    private ScheduledFuture job1;
    private boolean initial = true;

    public synchronized void scheduleJob(int period, String order) throws NoSuchMethodException {
        if (job1 != null) {
            job1.cancel(true);
        }

        switch (order) {
            case "A":
                jobAscending();
                break;
            case "D":
                jobDescending();
                break;
            default:
                break;
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
                //sendEmail(nutzer.getNut_email(), nutzer.getNut_anrede(), nutzer.getNut_nachname());
            } else {
                Integer nutzerId = nutzerArrayList.get(0).getNut_id();
                userService.removeEditorRole(nutzerId);
                nutzerArrayList.remove(0);

                Nutzer newNutzer = nutzerArrayList.get(0);
                userService.addEditorRole(newNutzer.getNut_id());
                //sendEmail(newNutzer.getNut_email(), newNutzer.getNut_anrede(), newNutzer.getNut_nachname());
            }
        } catch (IndexOutOfBoundsException e) {
            job1.cancel(true);
        }
    }

    public void jobAscending() {
        ArrayList<Vorlesung_Von_Nutzer> vorlesungVonNutzerList = getSummedUpList();
        Collections.sort(vorlesungVonNutzerList, Vorlesung_Von_Nutzer.ascendingComp);
        updateList(vorlesungVonNutzerList);
    }

    public void jobDescending() {
        ArrayList<Vorlesung_Von_Nutzer> vorlesungVonNutzerList = getSummedUpList();
        Collections.sort(vorlesungVonNutzerList, Vorlesung_Von_Nutzer.descendingComp);
        updateList(vorlesungVonNutzerList);
    }

    private ArrayList<Vorlesung_Von_Nutzer> getSummedUpList() {
        ArrayList<Vorlesung_Von_Nutzer> vorlesungVonNutzerList = (ArrayList<Vorlesung_Von_Nutzer>) vorlesungVonNutzerRepository.findAll();
        ArrayList<Vorlesung_Von_Nutzer> sumVorlesungVonNutzerList = new ArrayList<>();
        for (Vorlesung_Von_Nutzer vvn : vorlesungVonNutzerList) {
            if (!sumVorlesungVonNutzerList.contains(vvn)) {
                sumVorlesungVonNutzerList.add(vvn);
            } else {
                for (int i = 0; i < sumVorlesungVonNutzerList.size(); i++) {
                    if (sumVorlesungVonNutzerList.get(i).getVvn_nut_id() == vvn.getVvn_nut_id()) {
                        sumVorlesungVonNutzerList.get(i).setVvn_stnd(
                                sumVorlesungVonNutzerList.get(i).getVvn_stnd() + vvn.getVvn_stnd());
                    }
                }
            }
        }
        return sumVorlesungVonNutzerList;
    }

    private void updateList(ArrayList<Vorlesung_Von_Nutzer> vvns) {
        ArrayList<Nutzer> newList = new ArrayList<>();

        for (Vorlesung_Von_Nutzer vvn : vvns) {
            for (Nutzer nutzer : nutzerArrayList) {
                if (nutzer.getNut_id() == vvn.getVvn_nut_id()) {
                    if (!newList.contains(nutzer)) {
                        newList.add(nutzer);
                    }
                }
            }
        }
        nutzerArrayList = newList;
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
        message.setFrom(new InternetAddress("dhbwplanner@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Bitte beginnen Sie mit der Vorlesungsplanung");

        String localDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String localTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

        String msg = "Sehr geehrte/r " + anrede + " " + name + "," + "<br>" + "<br>"
                + "bitte beginnen Sie mit ihrer Vorlesungsplanung: " + "<a href='http://localhost:8080'>Vorlesungsplaner</a>."
                + "<br>" + "<br>" + "Ihr Planungsfenster schließt sich am "
                + localDate + " um " + localTime + "." + "<br>" + "Bei Fragen wenden Sie sich bitte an den Admin.";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);

    }

    public void setNutzerArrayList(ArrayList<Nutzer> nutzerArrayList) {
        this.nutzerArrayList = nutzerArrayList;
    }
}
