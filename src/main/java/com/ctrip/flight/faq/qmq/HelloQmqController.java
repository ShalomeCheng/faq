package com.ctrip.flight.faq.qmq;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qunar.tc.qmq.Message;
import qunar.tc.qmq.consumer.annotation.QmqConsumer;
import qunar.tc.qmq.spring.QmqTemplate;
import qunar.tc.qmq.spring.SendResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path = "/qmq")
public class HelloQmqController {

    private static final String TOPIC = "bbz.spring.qmq.test";
    private static final String GROUP = "123456789";
    private final QmqTemplate qmqTemplate;

    public HelloQmqController(@Autowired QmqTemplate qmqTemplate) {
        this.qmqTemplate = qmqTemplate;
    }

    @QmqConsumer(prefix = TOPIC, consumerGroup = GROUP)
    public void onMessage(Message message) {
        System.out.println("Received Message: " + message);
    }

    @GetMapping
    public String index(HttpServletRequest request) {
        final StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<html>");
        responseBuilder.append("<head><title>QMQ Demo</title></head>");
        responseBuilder.append("<body>");
        responseBuilder.append("<h1>QMQ Demo</h1>");
        responseBuilder.append("Please use following URLs:");
        responseBuilder.append("<ul>");
        responseBuilder.append("<li>").append(request.getRequestURL()).append("/send/{content}").append("</li>");
        responseBuilder.append("</ul>");
        responseBuilder.append("</body>");
        responseBuilder.append("</html>");
        return responseBuilder.toString();
    }

    @GetMapping(path = "/send/{what}")
    public String send(@PathVariable String what) throws ExecutionException, InterruptedException {
        final StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<html>");
        responseBuilder.append("<head><title>QMQ Demo</title></head>");
        responseBuilder.append("<body>");
        responseBuilder.append("<h1>QMQ Demo</h1>");

        Map<String, String> data = new HashMap<>();
        data.put("message", what);
        responseBuilder.append("MessageProperties: ").append(data);
        responseBuilder.append("<br/>");
        CompletableFuture<SendResult> future = qmqTemplate.send(TOPIC, data);
        SendResult result = future.get();
        responseBuilder.append("MessageID: ").append(result.getMessageId());

        return responseBuilder.toString();
    }
}