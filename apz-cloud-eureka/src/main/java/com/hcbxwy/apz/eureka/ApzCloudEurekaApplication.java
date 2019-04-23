package com.hcbxwy.apz.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心server启动入口
 *
 * @author Billson
 * @since 2019/4/1 20:30
 */
@EnableEurekaServer
@SpringBootApplication
public class ApzCloudEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApzCloudEurekaApplication.class, args);
    }

}
