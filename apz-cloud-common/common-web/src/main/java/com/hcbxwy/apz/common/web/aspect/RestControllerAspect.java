/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：RestControllerAspect.java</li>
 * <li>日期：2019/4/22 14:46</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hcbxwy.apz.common.web.constant.HeaderConstant;
import com.hcbxwy.apz.common.web.handler.GlobalExceptionHandler;
import com.hcbxwy.apz.common.util.IpUtil;
import com.hcbxwy.apz.common.web.entity.LoginUser;
import com.hcbxwy.apz.common.web.helper.LoginTokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 请求参数、响应体统一日志打印
 *
 * @author Billson
 * @since 2019/4/22 14:46
 */
@Slf4j
//@Aspect
//@Component
public class RestControllerAspect {

    /**
     * 环绕通知
     * @param joinPoint 连接点
     * @return 切入点返回值
     * @throws Throwable 异常信息
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController) || @annotation(org.springframework.web.bind.annotation.RestController)")
    public Object apiLog(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        boolean logFlag = this.needToLog(method);
        if (!logFlag) {
            return joinPoint.proceed();
        }

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        LoginUser loginUser = LoginTokenHelper.getLoginUserFromRequest();

        String ip = IpUtil.getRealIp(request);
        String methodName = this.getMethodName(joinPoint);
        String params = this.getParamsJson(joinPoint);
        String requester = loginUser == null ? "unknown" : String.valueOf(loginUser.getUserId());

        String callSource = request.getHeader(HeaderConstant.CALL_SOURCE);
        String appVersion = request.getHeader(HeaderConstant.APP_VERSION);
        String apiVersion = request.getHeader(HeaderConstant.API_VERSION);
        String userAgent = request.getHeader("user-agent");

        log.info("Started request requester [{}] method [{}] params [{}] IP [{}] callSource [{}] appVersion [{}] apiVersion [{}] userAgent [{}]", requester, methodName, params, ip, callSource, appVersion, apiVersion, userAgent);
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("Ended request requester [{}] method [{}] params[{}] response is [{}] cost [{}] millis ",
                requester, methodName, params, this.deleteSensitiveContent(result), System.currentTimeMillis() - start);
        return result;
    }

    private String getMethodName(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        String shortMethodNameSuffix = "(..)";
        if (methodName.endsWith(shortMethodNameSuffix)) {
            methodName = methodName.substring(0, methodName.length() - shortMethodNameSuffix.length());
        }
        return methodName;
    }

    private String getParamsJson(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            //移除敏感内容
            String paramStr;
            if (arg instanceof HttpServletResponse) {
                paramStr = HttpServletResponse.class.getSimpleName();
            } else if (arg instanceof HttpServletRequest) {
                paramStr = HttpServletRequest.class.getSimpleName();
            } else if (arg instanceof MultipartFile) {
                long size = ((MultipartFile) arg).getSize();
                paramStr = MultipartFile.class.getSimpleName() + " size:" + size;
            } else {
                paramStr = this.deleteSensitiveContent(arg);
            }
            sb.append(paramStr).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    private boolean needToLog(Method method) {
        //GET请求不记录日志
        return method.getAnnotation(GetMapping.class) == null
                && !method.getDeclaringClass().equals(GlobalExceptionHandler.class);
    }

    /**
     * 删除参数中的敏感内容
     *
     * @param obj   参数对象
     * @return 去除敏感内容后的参数对象
     * @author Billson
     * @since 2019/4/22 16:30
     */
    private String deleteSensitiveContent(Object obj) {
        JSONObject jsonObject = new JSONObject();
        if (obj == null || obj instanceof Exception) {
            return jsonObject.toJSONString();
        }

        try {
            String param = JSON.toJSONString(obj);
            jsonObject = JSONObject.parseObject(param);
            List<String> sensitiveFieldList = this.getSensitiveFieldList();
            for (String sensitiveField : sensitiveFieldList) {
                if (jsonObject.containsKey(sensitiveField)) {
                    jsonObject.put(sensitiveField, "******");
                }
            }
        } catch (ClassCastException e) {
            return String.valueOf(obj);
        }
        return jsonObject.toJSONString();
    }

    /**
     * 敏感字段列表
     * TODO 可以写到配置文件
     */
    private List<String> getSensitiveFieldList() {
        List<String> sensitiveFieldList = new ArrayList<>();
        sensitiveFieldList.add("pwd");
        sensitiveFieldList.add("password");
        return sensitiveFieldList;
    }
}
