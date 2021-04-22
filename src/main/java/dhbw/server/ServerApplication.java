package dhbw.server;

import dhbw.server.services.SchedulerServiceImpl;
import dhbw.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ServerApplication {

    @Autowired
    SchedulerServiceImpl schedulerService;
    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
