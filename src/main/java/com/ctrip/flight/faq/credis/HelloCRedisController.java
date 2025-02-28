package com.ctrip.flight.faq.credis;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.framework.spring.boot.credis.CRedisClient;
import credis.java.client.AsyncCacheProvider;
import credis.java.client.CacheProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path = "/credis")
public class HelloCRedisController {

    @CRedisClient("RedisTest5group")
    private CacheProvider cacheProvider;

    @CRedisClient("RedisTest5group")
    private AsyncCacheProvider asyncCacheProvider;

    @GetMapping
    public String demo() throws ExecutionException, InterruptedException {
        final StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<html>");
        responseBuilder.append("<head><title>CRedis Demo</title></head>");
        responseBuilder.append("<body>");

        responseBuilder.append("<h1>CRedis Demo</h1>");

        final String appId = Foundation.app().getAppId();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        final String now = formatter.format(LocalDateTime.now());

        responseBuilder.append("<h3>Sync Operation Demo</h3>");
        final String key = "demo.key." + appId;
        responseBuilder.append("Key: ").append(key);
        responseBuilder.append("<br/>");
        final String value = "demoValue_" + now;
        cacheProvider.set(key, value);
        responseBuilder.append("ValueSet: ").append(value);
        responseBuilder.append("<br/>");
        final String actualValue = cacheProvider.get(key);
        responseBuilder.append("ValueGot: ").append(actualValue);

        responseBuilder.append("<h3>Async Operation Demo</h3>");
        final String asyncKey = "demo.async-key." + appId;
        responseBuilder.append("Key: ").append(asyncKey);
        responseBuilder.append("<br/>");
        final String asyncValue = "demoAsyncValue_" + now;
        asyncCacheProvider.set(asyncKey, asyncValue);
        responseBuilder.append("ValueSet: ").append(asyncValue);
        responseBuilder.append("<br/>");
        final String actualAsyncValue = asyncCacheProvider.get(asyncKey).get();
        responseBuilder.append("ValueGot: ").append(actualAsyncValue);

        responseBuilder.append("</body>");
        responseBuilder.append("</html>");
        return responseBuilder.toString();
    }
}
