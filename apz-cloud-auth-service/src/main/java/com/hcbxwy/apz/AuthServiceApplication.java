/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：AuthServiceApplication.java</li>
 * <li>日期：2019/4/30 15:37</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 认证授权服务-应用程序入口
 *
 * @author Billson
 * @since 2019/4/30 15:37
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceApplication {

    public static void main(String[] args){
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
