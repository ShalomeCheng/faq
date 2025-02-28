package com.ctrip.flight.faq;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class HomeController {

    @GetMapping
    public String index() {
        return "<html>" +
                "<head><title>Hello from Trip FX</title></head>" +
                "<body>" +
                "<h2>Thanks for using Trip FX Starter.</h2>" +
                "Please checkout <i><b>README.md</b></i> in the project folder for details." +
                "</body>" +
                "</html>";
    }
}
