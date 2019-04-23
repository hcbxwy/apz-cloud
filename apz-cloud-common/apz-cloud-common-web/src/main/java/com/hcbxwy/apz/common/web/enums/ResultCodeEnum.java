/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：ResultCodeEnum.java</li>
 * <li>日期：2019/4/19 15:32</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * API 统一返回状态码
 *
 * @author Billson
 * @since 2019/4/19 15:32
 */
public enum ResultCodeEnum {

    /**
     * Success
     */
    SUCCESS(200, "Success"),

    // 4xx 客户端错误

    // 400xx 参数错误
    PARAM_IS_INVALID(40001, "参数无效"),

    // 403xx 权限错误
    /**
     * 无效签名 InvalidSignature
     */
    INVALID_SIGNATURE(40300, "无效签名"),

    // 500xx 服务端错误
    SYSTEM_INNER_ERROR(50001, "系统繁忙，请稍后重试");

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
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
        for (ResultCodeEnum item : ResultCodeEnum.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCodeEnum item : ResultCodeEnum.values()) {
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
        ResultCodeEnum[] apiResultCodeEnums = ResultCodeEnum.values();
        List<Integer> codeList = new ArrayList<>();
        for (ResultCodeEnum apiResultCodeEnum : apiResultCodeEnums) {
            if (codeList.contains(apiResultCodeEnum.code)) {
                System.out.println(apiResultCodeEnum.code);
            } else {
                codeList.add(apiResultCodeEnum.code());
            }

            System.out.println(apiResultCodeEnum.code() + " " + apiResultCodeEnum.message());
        }
    }
}
