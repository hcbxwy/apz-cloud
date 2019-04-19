/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：ApiSignUtil.java</li>
 * <li>日期：2019/4/10 18:19</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.cloud.open.common.util;

import com.hcbxwy.common.util.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * api网关签名工具
 *
 * @author Billson
 * @since 2019/4/10 18:19
 */
public class ApiSignUtil {

    /**
     * API网关中所有参与签名计算的HTTP请求头的Key,以应为逗号分隔
     */
    private static final String CA_PROXY_SIGN_HEADERS = "X-Ca-Proxy-Signature-Headers";
    /**
     * API网关计算的签名
     */
    public static final String CA_PROXY_SIGN = "X-Ca-Proxy-Signature";
    /**
     * API网关用于计算签名的密钥Key
     */
    private static final String CA_PROXY_SIGN_SECRET_KEY = "X-Ca-Proxy-Signature-Secret-Key";
    /**
     * 签名算法HmacSha256
     */
    private static final String HMAC_SHA256 = "HmacSHA256";
    /**
     * 换行符
     */
    private static char LINE_BREAK = '\n';
    /**
     * 编码
     */
    private static final String ENCODING = "UTF-8";
    /**
     * HTTP POST
     */
    private static final String HTTP_METHOD_POST = "post";
    /**
     * HTTP PUT
     */
    private static final String HTTP_METHOD_PUT = "put";
    /**
     * 英文逗号
     */
    private static final String COMMA = ",";
    /**
     * HTTP HEADER是否转换成小写（部分WEB容器中接受到的所有HEADER的KEY都是小写）
     */
    private static final boolean HTTP_HEADER_TO_LOWER_CASE = false;

    /**
     * 签名密钥Map,用于存储多对服务端签名计算密钥,一旦正在使用的密钥泄露,只需要将密钥列表中的其他密钥配置到网关即可进行密钥热替换
     */
    private static Map<String, String> signSecretMap = new HashMap<>(16);

    static {
        //TODO：修改为自己的密钥组合
        signSecretMap.put("DemoKey1", "DemoSecret1");
        signSecretMap.put("DemoKey2", "DemoSecret2");
        signSecretMap.put("DemoKey3", "DemoSecret3");
    }

    /**
     * 计算HTTP请求签名
     *
     * @param uri              原始HTTP请求PATH（不包含Query）
     * @param httpMethod       原始HTTP请求方法
     * @param headerMap        原始HTTP请求所有请求头
     * @param paramsMap        原始HTTP请求所有Query+Form参数
     * @param inputStreamBytes 原始HTTP请求Body体（仅当请求为POST/PUT且非表单请求才需要设置此属性,表单形式的需要将参数放到paramsMap中）
     * @return java.lang.String
     * @author Billson
     * @since 2019/4/10 20:17
     */
    public static String serviceSign(String uri, String httpMethod, Map<String, String> headerMap, Map<String, Object> paramsMap, byte[] inputStreamBytes) throws Exception {
        Map<String, String> headersToSign = buildHeadersToSign(headerMap);
        String bodyMd5 = buildBodyMd5(httpMethod, inputStreamBytes);
        String resourceToSign = buildResource(uri, paramsMap);
        String stringToSign = buildStringToSign(headersToSign, resourceToSign, httpMethod, bodyMd5);

        Mac hmacSha256 = Mac.getInstance(HMAC_SHA256);
        String secret = signSecretMap.get(headerMap.get(HTTP_HEADER_TO_LOWER_CASE ? CA_PROXY_SIGN_SECRET_KEY.toLowerCase() : CA_PROXY_SIGN_SECRET_KEY));

        byte[] keyBytes = secret.getBytes(ENCODING);
        hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, HMAC_SHA256));

        return new String(Base64.encodeBase64(hmacSha256.doFinal(stringToSign.getBytes(ENCODING))), ENCODING);
    }

    /**
     * 构建参与签名的HTTP头
     * <pre>
     * 传入的Headers必须将默认的ISO-8859-1转换为UTF-8以支持中文
     * </pre>
     *
     * @param headerMap 参与签名的请求头
     * @return java.util.Map<java.lang.String       ,       java.lang.String>
     * @author Billson
     * @since 2019/4/18 10:51
     */
    private static Map<String, String> buildHeadersToSign(Map<String, String> headerMap) {
        Map<String, String> headersToSignMap = new TreeMap<>();

        String headersToSignString = headerMap.get(HTTP_HEADER_TO_LOWER_CASE ? CA_PROXY_SIGN_HEADERS.toLowerCase() : CA_PROXY_SIGN_HEADERS);

        if (headersToSignString != null) {
            for (String headerKey : headersToSignString.split(COMMA)) {
                headersToSignMap.put(headerKey, headerMap.get(HTTP_HEADER_TO_LOWER_CASE ? headerKey.toLowerCase() : headerKey));
            }
        }

        return headersToSignMap;
    }

    /**
     * 构建body的md5
     * （只有 HttpMethod 为 PUT 或者 POST 且 Body 为非 Form 表单时才计算 MD5）
     *
     * @param httpMethod       http请求方法
     * @param inputStreamBytes 非form表单的body二进制数据
     * @return java.lang.String
     * @author Billson
     * @since 2019/4/18 16:48
     */
    private static String buildBodyMd5(String httpMethod, byte[] inputStreamBytes) throws IOException {
        if (inputStreamBytes == null) {
            return null;
        }

        if (!httpMethod.equalsIgnoreCase(HTTP_METHOD_POST) && !httpMethod.equalsIgnoreCase(HTTP_METHOD_PUT)) {
            return null;
        }

        InputStream inputStream = new ByteArrayInputStream(inputStreamBytes);
        byte[] bodyBytes = IOUtils.toByteArray(inputStream);
        if (bodyBytes != null && bodyBytes.length > 0) {
            return base64AndMD5(bodyBytes).trim();
        }
        return null;
    }

    /**
     * 先进行MD5摘要再进行Base64编码获取摘要字符串
     *
     * @param bytes 待计算字节数组
     * @return java.lang.String
     * @author Billson
     * @since 2019/4/11 11:38
     */
    private static String base64AndMD5(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes can not be null");
        }

        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(bytes);
            final Base64 base64 = new Base64();

            return new String(base64.encode(md.digest()));
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("unknown algorithm MD5");
        }
    }

    /**
     * 组织待计算签名字符串
     *
     * @param headers        HTTP请求头
     * @param resourceToSign Uri+请求参数的签名字符串
     * @param method         HTTP方法
     * @param bodyMd5        Body Md5值
     * @return java.lang.String
     * @author Billson
     * @since 2019/4/10 20:15
     */
    private static String buildStringToSign(Map<String, String> headers, String resourceToSign, String method, String bodyMd5) {
        StringBuilder sb = new StringBuilder();
        sb.append(method).append(LINE_BREAK);
        if (StringUtils.isNotBlank(bodyMd5)) {
            sb.append(bodyMd5);
        }
        sb.append(LINE_BREAK);
        sb.append(buildHeaders(headers));
        sb.append(resourceToSign);

        return sb.toString();
    }

    /**
     * 组织Headers签名签名字符串
     *
     * @param headerMap HTTP请求头
     * @return java.lang.String
     * @author Billson
     * @since 2019/4/10 20:14
     */
    private static String buildHeaders(Map<String, String> headerMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : headerMap.entrySet()) {
            if (e.getValue() != null) {
                sb.append(e.getKey().toLowerCase()).append(':').append(e.getValue()).append(LINE_BREAK);
            }
        }
        return sb.toString();
    }

    /**
     * 组织Uri+请求参数的签名字符串
     *
     * @param path      HTTP请求path,不包含Query
     * @param paramsMap HTTP请求所有参数（Query+Form参数）
     * @return java.lang.String
     * @author Billson
     * @since 2019/4/10 20:14
     */
    private static String buildResource(String path, Map<String, Object> paramsMap) {
        StringBuilder builder = new StringBuilder();

        // uri
        builder.append(path);

        // Query+Form
        TreeMap<String, Object> sortMap = new TreeMap<>(paramsMap);

        // 有Query+Form参数
        if (sortMap.size() > 0) {
            builder.append('?');
            builder.append(buildMapToSign(sortMap));
        }

        return builder.toString();
    }

    /**
     * 将Map转换为用&及=拼接的字符串
     *
     * @param paramMap 参数map
     * @return java.lang.String
     * @author Billson
     * @since 2019/4/10 20:11
     */
    private static String buildMapToSign(Map<String, Object> paramMap) {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, Object> e : paramMap.entrySet()) {
            if (builder.length() > 0) {
                builder.append('&');
            }

            String key = e.getKey();
            Object value = e.getValue();

            if (value != null) {
                if (value instanceof List) {
                    List list = (List) value;
                    if (list.size() == 0) {
                        builder.append(key);
                    } else {
                        builder.append(key).append("=").append(String.valueOf(list.get(0)));
                    }
                } else if (value instanceof Object[]) {
                    Object[] objs = (Object[]) value;
                    if (objs.length == 0) {
                        builder.append(key);
                    } else {
                        builder.append(key).append("=").append(String.valueOf(objs[0]));
                    }
                } else {
                    builder.append(key).append("=").append(String.valueOf(value));
                }
            }
        }

        return builder.toString();
    }

    /**
     * 获取参与签名计算的header
     *
     * @param request 请求对象
     * @return java.util.Map<java.lang.String   ,   java.lang.String>
     * @author Billson
     * @since 2019/4/18 15:38
     */
    public static Map<String, String> buildHeadersToSignMap(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>(16);
        String headers = request.getHeader(CA_PROXY_SIGN_HEADERS);
        headerMap.put(CA_PROXY_SIGN_HEADERS, headers);
        if (headers != null) {
            for (String headerKey : headers.split(COMMA)) {
                headerMap.put(headerKey, request.getHeader(headerKey));
            }
        }
        headerMap.put(CA_PROXY_SIGN, request.getHeader(CA_PROXY_SIGN));
        headerMap.put(CA_PROXY_SIGN_SECRET_KEY, request.getHeader(CA_PROXY_SIGN_SECRET_KEY));
        return headerMap;
    }

    /**
     * 获取request所有参数并转换成Map
     *
     * @param request 请求对象
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author Billson
     * @since 2019/4/18 11:32
     */
    public static Map<String, Object> buildParameterToMap(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<>(16);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramKey = parameterNames.nextElement();
            paramMap.put(paramKey, request.getParameter(paramKey));
        }
        return paramMap;
    }

    /**
     * 获取request的二进制body
     *
     * @param request 请求头
     * @return byte[]
     * @author Billson
     * @since 2019/4/18 17:26
     */
    public static byte[] buildInputStream(HttpServletRequest request) {
        String httpMethod = request.getMethod();
        if (!httpMethod.equalsIgnoreCase(HTTP_METHOD_POST) && !httpMethod.equalsIgnoreCase(HTTP_METHOD_PUT)) {
            return null;
        }
        String contentType = request.getContentType();
        if (StringUtils.isBlank(contentType) || !contentType.contains(ContentType.MULTIPART_FORM_DATA.getMimeType())){
            return null;
        }
        int len = request.getContentLength();
        byte[] buffer = new byte[len];
        ServletInputStream in = null;
        try {
            in = request.getInputStream();
            in.readLine(buffer, 0, len);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer;
    }
}
