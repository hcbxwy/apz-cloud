/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：CallSourceEnum.java</li>
 * <li>日期：2019/4/22 17:50</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.enums;

/**
 * 接口请求来源
 *
 * @author Billson
 * @since 2019/4/22 17:50
 */
public enum CallSourceEnum {

    /**
     * WEB网站
     */
    WEB,
    /**
     * PC客户端
     */
    PC,
    /**
     * 微信公众号
     */
    WECHAT,
    /**
     * IOS平台
     **/
    IOS,
    /**
     * 安卓平台
     */
    ANDROID;

    public static boolean isValid(String name) {
        for (CallSourceEnum callSource : CallSourceEnum.values()) {
            if (callSource.name().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
