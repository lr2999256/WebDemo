package org.rui.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Rui on 2017/6/26.
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/test")
    public String test(Model model) {
        return "test";
    }

    @RequestMapping("/index")
    public String index1(Model model) {
        return "admin/index";
    }

    @RequestMapping("/admin/welcome")
    public String welcome(Model model) {
        return "admin/welcome";
    }
}
