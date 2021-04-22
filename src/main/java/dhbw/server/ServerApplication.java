package dhbw.server;

import dhbw.server.services.SchedulerServiceImpl;
import dhbw.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    public void run(String... args) {
        /*if(!userService.getAllLecturers().isEmpty()){
            schedulerService.setNutzerArrayList(userService.getAllLecturers());
        }*/

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("testaccess"));
    }
}
