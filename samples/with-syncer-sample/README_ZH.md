### Apache ServiceComb Toolkit 结合 Apache ServiceComb Syncer 的实践



#### 场景

[Syncer](https://github.com/apache/servicecomb-service-center/tree/master/syncer)是一个多服务中心的同步工具，专为大型微服务架构设计，用于在网络互通的情况下，不同技术栈服务中心、跨区域的实例同步

结合Apache ServiceComb Toolkit 和Apache ServiceComb Syncer，不同开发者使用自己熟悉的框架进行开发，极大提高了开发效率。

如图1所示，开发者A和开发者B基于同一份OpenAPI规范进行开发，开发者A对于Apache ServiceComb十分熟悉，基于Apache ServiceComb框架开发了Provider应用，提供Restful API接口的实现。而开发者B对于Spring Cloud框架十分熟悉，基于Spring Cloud框架开发了Consumer应用，调用Provider应用的接口，然后进行自己的业务实现。

在通常情况下，他们是无法通过服务注册中心进行相互调用的。这时我们引入了Apache ServiceComb Syncer，这让一切变得简单。开发者A在Provider侧启动一个Syncer进程syncer-a，开发者B在Consumer侧启动一个Syncer进程syncer-b，进行一些简单的配置，它们将会把各自关联的服务注册中心的实例进行相互同步。此时Provider和Consumer就可以通过服务注册中心进行相互通信了。

![toolkit-syncer](https://github.com/kakulisen/picture_repo/blob/master/pratice/toolkit-syncer.png?raw=true)

#### 环境要求
* windows

* maven 3.3.1 +

* jdk 1.8



#### 实践步骤

* 下载并启动Apache ServiceComb服务中心ServiceCenter

  * 下载地址：http://servicecomb.apache.org/cn/release/service-center-downloads/

  * 启动

    ```cmd
    service-center.exe
    ```

* 启动Eureka服务中心

  ```
  cd ./eureka-server
  mvn spring-boot:run
  ```

* 下载并启动Apache ServiceComb Syncer 异构通信工具

  * 下载地址：http://servicecomb.apache.org/cn/release/service-center-downloads/

  * 启动 ServiceCenter 侧的syncer

    ```
    syncer daemon --node=syncer-sc --sc-addr http://127.0.0.1:30100 --bind-addr ${your_host_ip}:30190 --rpc-addr ${your_host_ip}:30191 --cluster-port=30192 --sc-plugin=servicecenter --join-addr ${your_host_ip}:30180
    ```

  * 启动 Eureka 侧的syncer

    ```
    syncer.exe daemon --node=syncer-eureka --sc-addr http://127.0.0.1:8761/eureka --bind-addr ${your_host_ip}:30180 --rpc-addr ${your_host_ip}:30181 --cluster-port=30182 --sc-plugin=eureka --join-addr ${your_host_ip}:30190
    ```

    

* 使用Apache ServiceComb Toolkit生成基于ServiceComb框架的服务提供者servicecomb-provider

  ```
  cd ./HelloService
  mvn toolkit:generate@provider
  ```

  

* 使用Apache ServiceComb Toolkit生成基于SpringCloud框架的服务消费者springcloud-consumer

  ```
  cd ./HelloService
  mvn toolkit:generate@consumer
  ```

  

* 验证

  * 为生成的服务提供者添加业务代码

    服务提供者默认生成目录 output/servicecomb-provider/project/servicecomb-provider

    编辑其中的 domain.orgnization.project.sample.api.HelloController 类

    添加如下自定义的业务逻辑

    ```
    return new ResponseEntity<String>(String.format("Hello %s", name), HttpStatus.OK);
    ```

  * 为生成的服务消费者添加调用代码

    修改消费者springcloud-consumer的启动类Application.java，添加如下代码

    ```
      @Autowired
      HelloController helloController;
    
      @Bean
      ApplicationRunner applicationRunner() {
        return args -> {
          String serviceComb = helloController.sayHello("ServiceComb");
          System.out.println(serviceComb);
        };
      }
    ```

  * 调用

    ```
    mvn spring-boot:run
    ```

    在控制台输出的日志最后有如下信息，则调用成功

    ```
    "Hello ServiceComb"
    ```