/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：UserController.java</li>
 * <li>日期：2019/4/9 17:19</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.cloud.open.user.controller;

import com.hcbxwy.apz.cloud.open.user.vo.UserVo;
import org.springframework.web.bind.annotation.*;

/**
 * 用户模块controller
 *
 * @author Billson
 * @since 2019/4/9 17:19
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/test")
    public String test() {
        return "测试成功";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserVo userVo) {
        System.out.println("登录成功。登录信息为：" + userVo.toString());
        return "登录成功。登录信息为：" + userVo.toString();
    }
}
