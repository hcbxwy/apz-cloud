/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：DefaultErrorResult.java</li>
 * <li>日期：2019/4/19 17:24</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.result;

import com.hcbxwy.apz.common.web.enums.BusinessExceptionEnum;
import com.hcbxwy.apz.common.web.enums.ResultCodeEnum;
import com.hcbxwy.apz.common.web.exception.BusinessException;
import com.hcbxwy.apz.common.util.ApplicationContextUtil;
import com.hcbxwy.apz.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * 默认全局错误返回结果
 *
 * PS：该返回信息是spring boot的默认异常时返回结果{@link DefaultErrorAttributes}
 *
 * @author Billson
 * @since 2019/4/19 17:24
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DefaultErrorResult implements Result {

    private static final long serialVersionUID = 1698450170145441472L;

    /**
     * HTTP响应状态码 {@link HttpStatus}
     */
    private Integer status;

    /**
     * HTTP响应状态码的英文提示
     */
    private String error;

    /**
     * 异常堆栈的精简信息
     *
     */
    private String message;

    /**
     * 我们系统内部自定义的返回值编码，{@link ResultCodeEnum} 它是对错误更加详细的编码
     *
     * PS：spring boot默认返回异常时，该字段为null
     */
    private Integer code;

    /**
     * 调用接口路径
     */
    private String path;

    /**
     * 异常的名字
     */
    private String exception;

    /**
     * 异常的错误传递的数据
     */
    private Object errors;

    /**
     * 时间戳
     */
    private Date timestamp;

    public static DefaultErrorResult failure(ResultCodeEnum resultCodeEnum, Throwable e, HttpStatus httpStatus, Object errors) {
        DefaultErrorResult result = DefaultErrorResult.failure(resultCodeEnum, e, httpStatus);
        result.setErrors(errors);
        return result;
    }

    public static DefaultErrorResult failure(ResultCodeEnum resultCodeEnum, Throwable e, HttpStatus httpStatus) {
        DefaultErrorResult result = new DefaultErrorResult();
        result.setCode(resultCodeEnum.code());
        result.setMessage(resultCodeEnum.message());
        result.setStatus(httpStatus.value());
        result.setError(httpStatus.getReasonPhrase());
        result.setException(e.getClass().getName());
        String path = ApplicationContextUtil.getRequest().getRequestURI();
        result.setPath(path);
        result.setTimestamp(new Date());
        return result;
    }

    public static DefaultErrorResult failure(BusinessException e) {
        BusinessExceptionEnum ee = BusinessExceptionEnum.getByEClass(e.getClass());
        if (ee != null) {
            return DefaultErrorResult.failure(ee.getResultCodeEnum(), e, ee.getHttpStatus(), e.getData());
        }

        DefaultErrorResult defaultErrorResult = DefaultErrorResult.failure(e.getResultCodeEnum() == null ? ResultCodeEnum.SUCCESS : e.getResultCodeEnum(), e, HttpStatus.OK, e.getData());
        if (StringUtils.isNotEmpty(e.getMessage())) {
            defaultErrorResult.setMessage(e.getMessage());
        }
        return defaultErrorResult;
    }
}
