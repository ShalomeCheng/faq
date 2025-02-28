package com.ctrip.flight.faq.soa;

import com.ctrip.framework.soa.hellosoa.HelloRequestType;
import com.ctrip.framework.soa.hellosoa.HelloSOAService;
import com.ctrip.framework.spring.boot.soa.SOAClient;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestController
@RequestMapping("/soa")
public class HelloSOAClientController {

    @SOAClient
    private HelloSOAService helloService;

    @GetMapping
    public String index(HttpServletRequest request) {
        final StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<html>");
        responseBuilder.append("<head><title>SOA Demo</title></head>");
        responseBuilder.append("<body>");
        responseBuilder.append("<h1>SOA Demo</h1>");
        responseBuilder.append("Please use following URLs:");
        responseBuilder.append("<ul>");
        responseBuilder.append("<li>").append(request.getRequestURL()).append("/hello").append("</li>");
        responseBuilder.append("</ul>");
        responseBuilder.append("</body>");
        responseBuilder.append("</html>");
        return responseBuilder.toString();
    }

    @GetMapping("/hello")
    public String callHello(@RequestParam(required = false) String name, @RequestParam(required = false) String service) {
        final StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<html>");
        responseBuilder.append("<head><title>SOA Demo</title></head>");
        responseBuilder.append("<body>");
        responseBuilder.append("<h1>SOA Client Demo</h1>");

        HelloRequestType helloRequest = new HelloRequestType();
        helloRequest.setName(StringUtils.firstNonBlank(name, "FX_SPRING_BOOT"));
        helloRequest.setService(StringUtils.firstNonBlank(service, "HelloService"));
        responseBuilder.append("<h3>Request Data</h3>");
        responseBuilder.append("name: ").append(helloRequest.getName());
        responseBuilder.append("<br/>");
        responseBuilder.append("service: ").append(helloRequest.getService());

        responseBuilder.append("<h3>Response Data</h3>");
        try {
            String info = helloService.hello(helloRequest).getHelloInfo().toString();
            responseBuilder.append("helloInfo: ").append(info);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            try (PrintWriter pw = new PrintWriter(sw)) {
                e.printStackTrace(pw);
                pw.flush();
                responseBuilder.append("Error: ").append(sw);
            }
        }

        responseBuilder.append("</body>");
        responseBuilder.append("</html>");
        return responseBuilder.toString();
    }
}
