/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：LoginAuth.java</li>
 * <li>日期：2019/4/22 21:30</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.annotation;

import java.lang.annotation.*;

/**
 * 已登录权限验证注解
 *
 * @author Billson
 * @since 2019/4/22 21:30
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginAuth {
}
