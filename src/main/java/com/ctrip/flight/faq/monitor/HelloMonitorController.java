package com.ctrip.flight.faq.monitor;

import com.ctrip.ops.hickwall.HickwallUDPReporter;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import io.dropwizard.metrics5.Counter;
import io.dropwizard.metrics5.MetricName;
import io.dropwizard.metrics5.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/monitor")
public class HelloMonitorController {

    private static final Logger logger = LoggerFactory.getLogger(HelloMonitorController.class);

    private final MetricRegistry metrics = new MetricRegistry();

    private final String counterKey = "hello_monitor_demo_counter";
    private final Counter counter = metrics.counter(new MetricName(counterKey, Collections.emptyMap()));

    public HelloMonitorController() {
        HickwallUDPReporter.enable(metrics, 60, TimeUnit.SECONDS, "FLT");
    }

    @GetMapping
    public String demo() {
        final StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<html>");
        responseBuilder.append("<head><title>Monitor Demo</title></head>");
        responseBuilder.append("<body>");
        responseBuilder.append("<h1>Monitor Demo</h1>");

        Transaction t = Cat.newTransaction("HelloMonitor", "demo"); // create cat transaction
        try {
            responseBuilder.append("<h3>Metric Demo</h3>");
            counter.inc(); // increase metric counter
            responseBuilder.append("Counter: key=").append(counterKey).append(", value=").append(counter.getCount());

            responseBuilder.append("<br/>");

            responseBuilder.append("<h3>Log Demo</h3>");
            logger.info("Hello Monitor"); // add log
            responseBuilder.append("An INFO log is logged.");

            responseBuilder.append("<br/>");

            t.addProperty("key1", "value1"); // add property to cat transaction

            final String eventType = "HelloMonitor";
            final String eventName = "event1";
            Cat.logEvent(eventType, eventName); // add cat event

            responseBuilder.append("<h3>CAT Demo</h3>");
            responseBuilder.append("CatMessageID=").append(Cat.getCurrentMessageId());
            responseBuilder.append("<br/>");
            responseBuilder.append("TransactionLogged:  type=" + t.getType() + ", name=" + t.getName());
            responseBuilder.append("<br/>");
            responseBuilder.append("EventLogged: type=" + eventType + ", name=" + eventName);

            responseBuilder.append("<h3>Note</h3>");
            responseBuilder.append("You can find all the items above on BAT Portal (<a href=\"http://bat/\" target=\"blank\">http://bat/</a>).");
            responseBuilder.append("<br/>");
            responseBuilder.append("Please switch to your local environment before looking for them.");

            t.setStatus(Message.SUCCESS); // mark cat transaction as success
        } catch (Exception e) {
            t.setStatus(e); // mark cat transaction as failure
        } finally {
            t.complete(); // commit cat transaction
        }

        responseBuilder.append("</body>");
        responseBuilder.append("</html>");
        return responseBuilder.toString();
    }
}

