package hr.bart.userDataServer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/v3/public")
    public String home(Model model) {
         return "index.html";
    }
    
}