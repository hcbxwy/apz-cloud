/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：AuthInterceptor.java</li>
 * <li>日期：2019/4/10 17:04</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.cloud.open.common.config;

import com.hcbxwy.apz.cloud.open.common.util.ApiSignUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 接口权限拦截器
 *
 * @author Billson
 * @since 2019/4/10 17:04
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证阿里云API网关签名
        if (!validSignature(request)){
            response.sendError(403,"InvalidSignature");
            response.setContentType("application/json; charset=utf-8");
//            PrintWriter writer = response.getWriter();
//            writer.print(JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
//                    SerializerFeature.WriteDateUseDateFormat));
//            writer.close();
//            response.flushBuffer();

            return false;
        }
        System.out.println("请求地址：" + request.getServletPath());
        return super.preHandle(request,response,handler);
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
