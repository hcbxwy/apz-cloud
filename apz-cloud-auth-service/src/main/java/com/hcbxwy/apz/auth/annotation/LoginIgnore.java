/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：LoginIgnore.java</li>
 * <li>日期：2019/5/5 20:18</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略用户认证校验，即不校验用户的登录状态
 *
 * @author Billson
 * @since 2019/5/5 20:18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginIgnore {
}
