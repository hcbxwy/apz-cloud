/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：BaseGlobalExceptionHandler.java</li>
 * <li>日期：2019/4/22 16:37</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.handler;

import com.hcbxwy.apz.common.web.entity.ParameterInvalidItem;
import com.hcbxwy.apz.common.web.enums.ResultCodeEnum;
import com.hcbxwy.apz.common.web.exception.BusinessException;
import com.hcbxwy.apz.common.web.helper.ParameterInvalidItemHelper;
import com.hcbxwy.apz.common.web.result.DefaultErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 全局异常处理基础类
 *
 * @author Billson
 * @since 2019/4/22 16:37
 */
@Slf4j
public class BaseGlobalExceptionHandler {

    /**
     * 处理违反约束异常
     */
    protected DefaultErrorResult handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        log.info("handleConstraintViolationException start, uri:{}, caused by: ", request.getRequestURI(), e);
        List<ParameterInvalidItem> parameterInvalidItemList = ParameterInvalidItemHelper.convertCVSetToParameterInvalidItemList(e.getConstraintViolations());
        return DefaultErrorResult.failure(ResultCodeEnum.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST, parameterInvalidItemList);
    }

    /**
     * 处理验证参数封装错误时异常
     */
    protected DefaultErrorResult handleConstraintViolationException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.info("handleConstraintViolationException start, uri:{}, caused by: ", request.getRequestURI(), e);
        return DefaultErrorResult.failure(ResultCodeEnum.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理参数绑定时异常（返回400错误码）
     */
    protected DefaultErrorResult handleBindException(BindException e, HttpServletRequest request) {
        log.info("handleBindException start, uri:{}, caused by: ", request.getRequestURI(), e);
        List<ParameterInvalidItem> parameterInvalidItemList = ParameterInvalidItemHelper.convertBindingResultToMapParameterInvalidItemList(e.getBindingResult());
        return DefaultErrorResult.failure(ResultCodeEnum.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST, parameterInvalidItemList);
    }

    /**
     * 处理使用@Validated注解时，参数验证错误异常（返回400错误码）
     */
    protected DefaultErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.info("handleMethodArgumentNotValidException start, uri:{}, caused by: ", request.getRequestURI(), e);
        List<ParameterInvalidItem> parameterInvalidItemList = ParameterInvalidItemHelper.convertBindingResultToMapParameterInvalidItemList(e.getBindingResult());
        return DefaultErrorResult.failure(ResultCodeEnum.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST, parameterInvalidItemList);
    }

    /**
     * 处理通用自定义业务异常
     */
    protected ResponseEntity<DefaultErrorResult> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.info("handleBusinessException start, uri:{}, exception:{}, caused by: {}", request.getRequestURI(), e.getClass(), e.getMessage());

        DefaultErrorResult defaultErrorResult = DefaultErrorResult.failure(e);
        return ResponseEntity
                .status(HttpStatus.valueOf(defaultErrorResult.getStatus()))
                .body(defaultErrorResult);
    }

    /**
     * 处理未预测到的其他错误（返回500错误码）
     */
    protected DefaultErrorResult handleThrowable(Throwable e, HttpServletRequest request) {
        log.error("handleThrowable start, uri:{}, caused by: ", request.getRequestURI(), e);
        return DefaultErrorResult.failure(ResultCodeEnum.SYSTEM_INNER_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
