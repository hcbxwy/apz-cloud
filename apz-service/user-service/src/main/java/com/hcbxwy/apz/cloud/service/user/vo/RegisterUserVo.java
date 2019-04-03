/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：RegisterUserVo.java</li>
 * <li>日期：2019/4/3 16:36</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.cloud.service.user.vo;

import lombok.Data;

/**
 * 用户注册接口传递对象
 *
 * @author Billson
 * @since 2019/4/3 16:36
 */
@Data
public class RegisterUserVo {

    private String username;
    private String password;
}
