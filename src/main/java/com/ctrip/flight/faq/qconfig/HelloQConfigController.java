package com.ctrip.flight.faq.qconfig;

import com.ctrip.flight.faq.qconfig.entity.JsonEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qunar.tc.qconfig.client.spring.QConfig;
import qunar.tc.qconfig.client.spring.QMapConfig;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/qconfig")
public class HelloQConfigController {

    /**
     * This demo uses the public configuration file under appid: 123456789.
     * see "http://qconfig.ctripcorp.com/webapp/page/index.html#/qconfig/123456789/fat:"
     *
     * Applications can use own configuration files by using without "appid#":
     * @QMapConfig(value = "fileName", key = "keyName")
     *
     * Reference:
     * http://docs.fx.ctripcorp.com/docs/qconfig/how-to/java-client/3.2_access-config/3.2.2_spring-boot
     */
    @QMapConfig(value = "123456789#spring-boot-demo.properties", key = "title")
    private String title;
    @QMapConfig(value = "123456789#spring-boot-demo.properties", key = "data.int")
    private int intData;
    @QMapConfig(value = "123456789#spring-boot-demo.properties", key = "data.bool")
    private boolean boolData;
    @QMapConfig(value = "123456789#spring-boot-demo.properties", key = "data.list")
    private List<String> listData;
    @QMapConfig(value = "123456789#spring-boot-demo.properties", key = "data.map")
    private Map<String, Integer> mapData;

    @QConfig("123456789#spring-boot-demo.json")
    private JsonEntity jsonEntity;

    @GetMapping
    public String index() {
        final StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<html>");
        responseBuilder.append("<head><title>QConfig Demo</title></head>");
        responseBuilder.append("<body>");
        responseBuilder.append("<h1>QConfig Demo</h1>");

        responseBuilder.append("<h3>Attention</h3>");
        responseBuilder.append("Using public configuration file under appid: 123456789");
        responseBuilder.append("<br/>");

        responseBuilder.append("<h3>QConfig JSON Config Demo</h3>");
        responseBuilder.append("File: spring-boot-demo.properties");
        responseBuilder.append("<br/>");
        responseBuilder.append("Entity: ").append(jsonEntity);
        responseBuilder.append("<br/>");

        responseBuilder.append("<h3>QConfig KV Config Demo</h3>");
        responseBuilder.append("File: spring-boot-demo.json");
        responseBuilder.append("<br/>");
        responseBuilder.append("Properties: ");
        responseBuilder.append("<br/>");
        responseBuilder.append("<ul>");
        responseBuilder.append("<li>title: ").append(title).append("</li>");
        responseBuilder.append("<li>intData: ").append(intData).append("</li>");
        responseBuilder.append("<li>boolData: ").append(boolData).append("</li>");
        responseBuilder.append("<li>listData: ").append(listData).append("</li>");
        responseBuilder.append("<li>mapData: ").append(mapData).append("</li>");
        responseBuilder.append("</ul>");

        responseBuilder.append("</body>");
        responseBuilder.append("</html>");
        return responseBuilder.toString();
    }
}
