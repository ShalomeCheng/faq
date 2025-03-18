# Trip FAQ 服务

本项目是从.NET项目 [https://github.com/travix-international/travix.faq](https://github.com/travix-international/travix.faq) 迁移而来的Java实现版本。该项目提供FAQ数据的查询和管理服务，支持多语言、多渠道的FAQ内容展示。

## 项目说明

本项目使用Spring Boot和Trip FX框架构建，主要功能包括：

- 从AI服务获取FAQ数据
- 支持多语言、多货币、多渠道和多品牌
- 结果缓存以提高性能
- 遵循SOA服务规范

## 架构设计

项目采用分层架构设计：

1. **SOA服务层** - 提供标准SOA接口，负责请求和响应处理
2. **业务服务层** - 处理核心业务逻辑，包括缓存、数据转换等
3. **数据访问层** - 与外部AI服务交互获取数据

## 开发指南

### 环境要求

- JDK 17+
- Maven 3.6+
- CRedis (用于缓存)

### 运行项目

**方法1:** 使用Maven插件

```shell
./mvnw spring-boot:run
```

**方法2:** 使用IntelliJ IDEA

1. 在IntelliJ IDEA中打开项目
2. 使用现有的运行配置`AppSpringBootConfiguration`启动项目

注意: 如果在导入项目到IntelliJ IDEA时遇到以下错误，请在IntelliJ IDEA设置中将Maven版本更改为3.3.9(Settings -> Build,Execution,Deployment -> Maven -> Maven home path)

> 'parent.version' is either LATEST or RELEASE (both of them are being deprecated)

**方法3:** 使用Visual Studio Code

1. 在Visual Studio Code中打开项目
2. 切换到左侧导航栏的"运行和调试"标签
3. 使用现有的运行配置`AppSpringBootConfiguration`启动项目

### API测试

项目启动后，可以通过以下URL测试基本功能:

- SOA服务: [http://localhost:8080/api](http://localhost:8080/api)
- FAQ查询接口: [http://localhost:8080/soa/faq](http://localhost:8080/soa/faq)

## 核心代码文件

- SOA服务: `com.ctrip.flight.faq.soa.TripFaqServiceImpl`
- 业务服务: `com.ctrip.flight.faq.service.TripFaqBusinessService`
- 模型类: `com.ctrip.flight.faq.soa.*`

## 参考文档

* [Trip FX框架文档](http://fx/)
* [原始.NET项目](https://github.com/travix-international/travix.faq)

