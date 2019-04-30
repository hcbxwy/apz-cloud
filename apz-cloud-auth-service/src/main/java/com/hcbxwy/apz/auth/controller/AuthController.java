/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：AuthController.java</li>
 * <li>日期：2019/4/30 16:51</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.auth.controller;

import com.hcbxwy.apz.common.web.annotation.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证授权控制器
 *
 * @author Billson
 * @since 2019/4/30 16:51
 */
@ResponseResult
@RestController
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping("/test")
    public String test(){
        return "测试认证服务器";
    }
}
