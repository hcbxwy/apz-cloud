/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：UserController.java</li>
 * <li>日期：2019/4/3 16:23</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.cloud.service.user.controller;

import com.hcbxwy.apz.cloud.service.user.vo.RegisterUserVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务控制器
 *
 * @author Billson
 * @since 2019/4/3 16:23
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * 用户注册
     *
     * @param registerUserVo 注册信息
     * @return java.lang.String
     * @author Billson
     * @since 2019/4/3 16:47
     */
    @PostMapping("/")
    public String addUser(@RequestBody RegisterUserVo registerUserVo) {
        return "用户注册成功。注册信息为：" + registerUserVo.toString();
    }
}
