/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：AuthInterceptor.java</li>
 * <li>日期：2019/4/10 17:04</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.interceptor;

import com.hcbxwy.apz.common.web.util.ApiSignUtil;
import com.hcbxwy.apz.common.util.StringUtils;
import com.hcbxwy.apz.common.web.enums.ResultCodeEnum;
import com.hcbxwy.apz.common.web.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 阿里云网关拦截器
 *
 * @author Billson
 * @since 2019/4/10 17:04
 */
@Component
public class AliGatewayInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证阿里云API网关签名
        if (StringUtils.isBlank(request.getHeader(ApiSignUtil.CA_PROXY_SIGN))){
            throw new BusinessException(ResultCodeEnum.PARAM_SIGNATURE_IS_BLANK);
        }
        if (StringUtils.isBlank(request.getHeader(ApiSignUtil.CA_PROXY_SIGN_SECRET_KEY))){
            throw new BusinessException(ResultCodeEnum.PARAM_SECRET_IS_BLANK);
        }
        if (!validSignature(request)) {
            throw new BusinessException(ResultCodeEnum.INVALID_SIGNATURE);
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 验证阿里云API网关签名是否正确
     *
     * @param request 请求对象
     * @return boolean
     * @author Billson
     * @since 2019/4/10 17:47
     */
    private boolean validSignature(HttpServletRequest request) throws Exception {
        String uri = request.getServletPath();
        String httpMethod = request.getMethod();
        Map<String, String> headerMap = ApiSignUtil.buildHeadersToSignMap(request);
        Map<String, Object> paramsMap = ApiSignUtil.buildParameterToMap(request);

        byte[] inputStreamBytes = ApiSignUtil.buildInputStream(request);

        String gatewaySign = request.getHeader(ApiSignUtil.CA_PROXY_SIGN);
        System.out.println("API网关签名:" + gatewaySign);

        String serviceSign = ApiSignUtil.serviceSign(uri, httpMethod, headerMap, paramsMap, inputStreamBytes);
        System.out.println("服务端签名:" + serviceSign);

        System.out.println("签名是否相同:" + gatewaySign.equals(serviceSign));
        System.out.println("请求地址：" + uri);
        return gatewaySign.equals(serviceSign);
    }

}
