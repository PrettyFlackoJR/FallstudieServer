package dhbw.server;

import dhbw.server.services.SchedulerServiceImpl;
import dhbw.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

    @Autowired
    SchedulerServiceImpl schedulerService;
    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        schedulerService.setNutzerArrayList(userService.getAllNutzer());
       // schedulerService.scheduleJob(10000);

        //schedulerService.scheduleJob(172800000);
    }
}
