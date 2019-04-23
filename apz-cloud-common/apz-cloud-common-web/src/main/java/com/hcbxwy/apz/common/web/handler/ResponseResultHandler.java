/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：ResponseResultHandler.java</li>
 * <li>日期：2019/4/22 22:17</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.handler;

import com.alibaba.fastjson.JSONObject;
import com.hcbxwy.apz.common.web.annotation.ResponseResult;
import com.hcbxwy.apz.common.web.constant.HeaderConstant;
import com.hcbxwy.apz.common.web.enums.ApiStyleEnum;
import com.hcbxwy.apz.common.web.result.DefaultErrorResult;
import com.hcbxwy.apz.common.web.result.DefaultResult;
import com.hcbxwy.apz.common.web.result.Result;
import com.hcbxwy.apz.common.util.ApplicationContextUtil;
import com.hcbxwy.apz.common.web.interceptor.ResponseResultInterceptor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口响应体处理器
 *
 * @author Billson
 * @since 2019/4/22 22:17
 */
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        HttpServletRequest request = ApplicationContextUtil.getRequest();
        ResponseResult responseResultAnn = (ResponseResult) request.getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);
        return responseResultAnn != null && !ApiStyleEnum.NONE.name().equalsIgnoreCase(request.getHeader(HeaderConstant.API_STYLE));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ResponseResult responseResultAnn = (ResponseResult) ApplicationContextUtil.getRequest().getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);

        Class<? extends Result> resultClazz = responseResultAnn.value();

        if (resultClazz.isAssignableFrom(DefaultResult.class)) {
            if (body instanceof DefaultErrorResult) {
                DefaultErrorResult defaultErrorResult = (DefaultErrorResult) body;
                return DefaultResult.builder()
                        .code(defaultErrorResult.getCode())
                        .msg(defaultErrorResult.getMessage())
                        .data(defaultErrorResult.getErrors())
                        .build();
            } else if (body instanceof String) {
                return JSONObject.toJSONString(DefaultResult.success(body));
            }

            return DefaultResult.success(body);
        }

        return body;
    }
}
