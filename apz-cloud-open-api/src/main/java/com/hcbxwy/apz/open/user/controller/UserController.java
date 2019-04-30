/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：UserController.java</li>
 * <li>日期：2019/4/9 17:19</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.open.user.controller;

import com.hcbxwy.apz.common.web.annotation.ResponseResult;
import com.hcbxwy.apz.open.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户模块controller
 *
 * @author Billson
 * @since 2019/4/9 17:19
 */
@ResponseResult
@RestController
@RequestMapping("/users")
public class UserController {

    @Value("${spring.application.name}")
    private String test;

    @GetMapping("/test")
    public String test() {
        return "测试阿波罗配置：test=" + test;
    }

    @PostMapping("/login")
    public UserVo login(@Valid @RequestBody UserVo userVo) {
        System.out.println("登录成功。登录信息为：" + userVo.toString());
        return userVo;
    }
}
