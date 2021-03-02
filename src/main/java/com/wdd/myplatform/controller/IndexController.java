package com.wdd.myplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/websocketDemo")
    public String websocketServer(){
        return "websocketDemo";
    }

}
