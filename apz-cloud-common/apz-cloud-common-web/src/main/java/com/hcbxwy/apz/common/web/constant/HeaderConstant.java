/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：HeaderConstant.java</li>
 * <li>日期：2019/4/22 17:39</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.constant;

import com.hcbxwy.apz.common.web.enums.CallSourceEnum;

/**
 * HTTP头部
 *
 * @author Billson
 * @since 2019/4/22 17:39
 */
public class HeaderConstant {

    /**
     * 用户的登录token
     */
    public static final String X_TOKEN = "X-Token";

    /**
     * api的版本号
     */
    public static final String API_VERSION = "Api-Version";

    /**
     * app版本号
     */
    public static final String APP_VERSION = "App-Version";

    /**
     * 调用来源 {@link CallSourceEnum}
     */
    public static final String CALL_SOURCE = "Call-Source";

    /**
     * API的返回格式 {@link ApiStyleEnum}
     */
    public static final String API_STYLE = "Api-Style";
}
