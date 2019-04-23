/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：UserVo.java</li>
 * <li>日期：2019/4/18 14:26</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.open.user.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 用户Vo
 *
 * @author Billson
 * @since 2019/4/18 14:26
 */
@Data
public class UserVo {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码长度至少6位")
    private String password;
}
