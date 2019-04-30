/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：LoginToken.java</li>
 * <li>日期：2019/4/22 19:58</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.entity;

import com.hcbxwy.apz.common.web.enums.CallSourceEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * 登录token
 *
 * @author Billson
 * @since 2019/4/22 19:58
 */
@ApiModel("登录的TOKEN")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginToken {

    @ApiModelProperty(value = "登陆token ID", required = true, position = 0)
    private String id;

    @ApiModelProperty(value = "生存时长(单位：秒)", required = true, position = 1)
    private Long ttl;

    @ApiModelProperty(value = "登录IP", required = true, position = 2)
    private String ip;

    /**
     * 平台 {@link CallSourceEnum}
     */
    @ApiModelProperty(value = "登录平台", required = true, position = 3)
    private String platform;

    @ApiModelProperty(value = "登录时间", required = true, position = 4)
    private Date createTime;

    @ApiModelProperty(value = "登录凭证", required = true, position =5)
    LoginCredential loginCredential;

    @ApiModelProperty(value = "登录的用户信息", required = true, position = 6)
    private LoginUser loginUser;
}
