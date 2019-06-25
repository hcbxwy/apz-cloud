/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：AuthServerConfig.java</li>
 * <li>日期：2019/5/6 14:55</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 授权服务器配置
 *
 * @author Billson
 * @since 2019/5/6 14:55
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("25799629")
                .secret("{noop}df58880d0c70c2bcdff3a242b631829a")
                .authorizedGrantTypes("client_credentials")
                .scopes("*")
                .accessTokenValiditySeconds(7201);
    }
}
