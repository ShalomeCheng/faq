## Getting Started

This project is a starter for a Spring Boot application that uses the Trip FX framework.

### Step 1. Start the application

**Method 1:** Using Maven plugin

Run the following command in your terminal:

```shell
./mvnw spring-boot:run
```

**Method 2:** Using IntelliJ IDEA

1. Open the project in IntelliJ IDEA.
2. Use the existed run configuration `AppSpringBootConfiguration` to start the project.

Note: If you encounter the following error when importing the project into IntelliJ IDEA, please change the Maven version to 3.3.9 in the IntelliJ IDEA Settings -> Build,Execution,Deployment -> Maven -> Maven home path

> 'parent.version' is either LATEST or RELEASE (both of them are being deprecated)

**Method 3:** Using Visual Studio Code

1. Open the project in Visual Studio Code.
2. Switch to the "Run and Debug" tag on the left navigation bar.
3. Use the existed run configuration `AppSpringBootConfiguration` to start the project.

### Step 2: Open [http://localhost:8080/]() in your browser

You can use following URLs to test basic functions of each component:

- SOA Service: [http://localhost:8080/api](http://localhost:8080/api)
- SOA Client: [http://localhost:8080/soa/hello](http://localhost:8080/soa/hello)
- QMQ Producer: [http://localhost:8080/qmq/send/{what}](http://localhost:8080/qmq/send/{what})
- QConfig: [http://localhost:8080/qconfig](http://localhost:8080/qconfig)
- CRedis: [http://localhost:8080/credis](http://localhost:8080/credis)
- DAL: [http://localhost:8080/dal](http://localhost:8080/dal)

### Step 3: View code files

You can checkout following code files to see the usage of each component:

- SOA Service: com.ctrip.flight.faq.soa.HelloSOAServiceImpl
- SOA Client: com.ctrip.flight.faq.soa.HelloSOAClientController
- QMQ: com.ctrip.flight.faq.qmq.HelloQmqController
- QConfig: com.ctrip.flight.faq.qconfig.HelloQConfigController
- CRedis: com.ctrip.flight.faq.credis.HelloCRedisController
- DAL: com.ctrip.flight.faq.dal.HelloDalController

## Reference Documentation

For further reference, please consider the following sections:

* [Framework Documentation](http://fx/)
