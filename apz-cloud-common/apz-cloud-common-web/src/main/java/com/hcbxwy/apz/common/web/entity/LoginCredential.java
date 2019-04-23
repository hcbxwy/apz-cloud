/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：LoginCredential.java</li>
 * <li>日期：2019/4/22 20:13</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.entity;

import com.hcbxwy.apz.common.web.annotation.EnumValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * 登录凭证
 *
 * @author Billson
 * @since 2019/4/22 20:13
 */
@ApiModel("登录凭证")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredential {

    @ApiModelProperty(value = "凭证主键")
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @ApiModelProperty(value = "账号")
    @NotBlank
    @Length(min=1, max=128)
    private String account;

    @ApiModelProperty(value = "密码")
    private String pwd;

    @ApiModelProperty(value = "密码加密随机盐")
    @Length(max=64)
    private String randomSalt;

    @ApiModelProperty(value = "用户主键")
    @NotBlank
    private String userId;

    @ApiModelProperty(value = "账号类型")
    @EnumValue(enumClass=LoginCredential.TypeEnum.class, enumMethod="isValidName")
    private String type;

    /**
     * 账号类型枚举
     */
    public enum TypeEnum {
        /**自定义*/
        CUSTOM,
        /**微信UNION ID*/
        UNION_ID;

        public static boolean isValidName(String name) {
            for (LoginCredential.TypeEnum typeEnum : LoginCredential.TypeEnum.values()) {
                if (typeEnum.name().equals(name)) {
                    return true;
                }
            }
            return false;
        }
    }
}
