RabbitmqService
===========
本项目是[RabbitMQ](http://www.rabbitmq.com/)的接口封装,用来简化发送和接收的调用,消息队列的配置,并且对RabbitMQ解耦.

# Maven POM 配置
#### SNAPSHOT

```xml
<dependency>
   <groupId>com.gkframework.jms</groupId>
    <artifactId>gk-framework-jms-rabbit</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

如果使用了消息断言,需要引入`JSONAssert`

```xml
<dependency>
    <groupId>org.skyscreamer</groupId>
    <artifactId>jsonassert</artifactId>
    <version>1.2.3</version>
    <scope>test</scope>
</dependency>
```

# 提供API
* 消息配置

使用注解进行配置,用于发送方法和接收方法

```java
@Message(exchange = "exchangeName", queue = "queueName", routeKeys = "routeKey", type = "some type", confirm = true or false)
```

路由模式

```java
@Message(exchange = "exchangeName", queue = "queueName", routeKeys = "routeKey")
```

广播模式

```java
@Message(exchange = "exchangeName", type = MessageService.FANOUT)
```

分组模式接收

```java
@Message(exchange = "exchangeName", queue = "SPEL表达式区分组别", routeKeys = "routeKey", type = "some type")
```

```java
@Message(exchange = "exchangeName", queue = "SPEL表达式区分组别", type = MessageService.FANOUT)
```

保证消息可靠性

```java
@Message(confirm = true)
```

在消息正常处理后才发送反馈给mq服务器,接着处理下一条消息;否则发送错误反馈,服务器会重新发送消息.

* 发送接口

```java
MessageService messageService = SpringUtil.getBean(MessageService.class);
messageService.sendMessage(json or entity);
```
   或者使用自动注入

```java
@Autowired
private MessageService messageService;

messageService.sendMessage(json or entity);
```
   带消息超时设定的发送
   
```java
messageService.sendMessage(json or entity,timeout);         
```

* 接收接口

继承`MessageListener`类,实现接口方法

```java
public void listen(String json);
```

* 消息断言

`junit`中用来验证发送的消息是否正确

```java
MessageAssert messageAssert = new MessageAssert();
messageAssert.setExpect(json)
             .getProxy(YourSenderClass.class).yourSendMethod(json or entity);
```

# 发布说明
见[ReleaseNote](/fangzy/rabbitmqService/wikis/ReleaseNote)

# 路线图
* 支持更多的消息队列配置
* 处理失败则反馈错误,消息服务器重发
* 超时消息转移
* 事务的支持

