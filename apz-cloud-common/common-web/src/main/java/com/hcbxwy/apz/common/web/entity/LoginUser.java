/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：LoginUser.java</li>
 * <li>日期：2019/4/22 19:19</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录用户信息
 *
 * @author Billson
 * @since 2019/4/22 19:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements Serializable {

    private static final long serialVersionUID = -1379044960015490320L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "登陆账号")
    private String nickname;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "用户类型")
    private String userType;

    @ApiModelProperty(value = "最新登陆IP")
    private String latestLoginIp;

    @ApiModelProperty(value = "最新登陆时间")
    private Date latestLoginTime;
}
