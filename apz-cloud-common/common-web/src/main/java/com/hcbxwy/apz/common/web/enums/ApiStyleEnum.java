/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：ApiStyleEnum.java</li>
 * <li>日期：2019/4/22 22:21</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.enums;

/**
 * 接口返回值风格样式枚举类
 *
 * @author Billson
 * @since 2019/4/22 22:21
 */
public enum ApiStyleEnum {

    /**
     * null
     */
    NONE;

    public static boolean isValid(String name) {
        for (ApiStyleEnum callSource : ApiStyleEnum.values()) {
            if (callSource.name().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
