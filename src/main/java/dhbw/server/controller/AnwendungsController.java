package dhbw.server.controller;

import dhbw.server.services.KursService;
import dhbw.server.services.SchedulerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/vorlesungsplaner")
public class AnwendungsController {

    @Autowired
    private KursService kursService;
    @Autowired
    private SchedulerServiceImpl schedulerService;

    @GetMapping
    public String viewPlanner(Model model) {
        model.addAttribute("kvn_namen", kursService.getKursNamen());
        return "homepage";
    }

    @GetMapping("/process_endplanning")
    @ResponseBody
    public void endPlanning(@RequestParam(required = false, name = "order") String order, HttpServletResponse response) throws NoSuchMethodException {
        if (order == null) {
            order = "X";
        }
        int i = schedulerService.scheduleJob(172800000, order);
        if (i == 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
