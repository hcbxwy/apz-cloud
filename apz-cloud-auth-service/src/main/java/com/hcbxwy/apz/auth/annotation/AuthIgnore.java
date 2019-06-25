/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：AuthIgnore.java</li>
 * <li>日期：2019/5/5 20:15</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略客户端授权校验，即不判断客户端的授权状态
 *
 * @author Billson
 * @since 2019/5/5 20:15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthIgnore {
}
