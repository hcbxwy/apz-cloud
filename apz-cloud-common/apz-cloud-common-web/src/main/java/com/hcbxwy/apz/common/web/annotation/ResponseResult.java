/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：ResponseResult.java</li>
 * <li>日期：2019/4/22 22:07</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.annotation;

import com.hcbxwy.apz.common.web.result.DefaultResult;
import com.hcbxwy.apz.common.web.result.Result;

import java.lang.annotation.*;

/**
 * 接口返回结果增强  会通过拦截器拦截后放入标记，在WebResponseBodyHandler进行结果处理
 *
 * @author Billson
 * @since 2019/4/22 22:07
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseResult {

    Class<? extends Result> value() default DefaultResult.class;
}
