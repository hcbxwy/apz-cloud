/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：ApzCloudOpenApplication.java</li>
 * <li>日期：2019/4/2 16:24</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.cloud.open;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 开放服务启动入口
 *
 * @author Billson
 * @since 2019/4/2 16:24
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApzCloudOpenApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApzCloudOpenApplication.class, args);
    }
}
