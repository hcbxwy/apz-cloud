/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：ResultCode.java</li>
 * <li>日期：2019/4/19 15:32</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.common.web.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * API 统一返回状态码
 *
 * @author Billson
 * @since 2019/4/19 15:32
 */
public enum ResultCode {

    /**
     * Success
     */
    SUCCESS(200, "Success"),

    // 4xx 客户端错误

    // 400xx 参数错误
    PARAM_IS_INVALID(40001, "参数无效"),

    /**
     * Invalid Signature
     */
    INVALID_SIGNATURE(403, "无效签名"),

    // 5xx 服务端错误
    /**
     * Internal Server Error
     */
    INTERNAL_SERVER_ERROR(500, "系统内部发生错误，请稍后重试");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }

    /***
     * 校验重复的code值
     */
    public static void main(String[] args) {
        ResultCode[] apiResultCodes = ResultCode.values();
        List<Integer> codeList = new ArrayList<>();
        for (ResultCode apiResultCode : apiResultCodes) {
            if (codeList.contains(apiResultCode.code)) {
                System.out.println(apiResultCode.code);
            } else {
                codeList.add(apiResultCode.code());
            }

            System.out.println(apiResultCode.code() + " " + apiResultCode.message());
        }
    }
}
