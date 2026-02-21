package top.leonam.encurtadorurl.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UrlView {

    public String index(){
        return "index";
    }

    @GetMapping("/not-found")
    public String notFound(){
        return "404";
    }

}
