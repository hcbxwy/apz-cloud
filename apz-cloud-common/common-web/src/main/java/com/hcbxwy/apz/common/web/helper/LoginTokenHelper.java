/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：LoginTokenHelper.java</li>
 * <li>日期：2019/4/22 19:31</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.helper;

import com.hcbxwy.apz.common.web.annotation.LoginAuth;
import com.hcbxwy.apz.common.web.constant.HeaderConstant;
import com.hcbxwy.apz.common.web.entity.LoginToken;
import com.hcbxwy.apz.common.util.ApplicationContextUtil;
import com.hcbxwy.apz.common.util.CookieUtil;
import com.hcbxwy.apz.common.util.StringUtils;
import com.hcbxwy.apz.common.web.entity.LoginUser;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 登录token辅助类
 *
 * @author Billson
 * @since 2019/4/22 19:31
 */
public class LoginTokenHelper {

    private final static String SECRET_KEY = "Ld4Dl5f9OoYTezPK";

    private final static String LOGIN_TOKEN_KEY = "LOGIN-TOKEN";

    /**
     * 根据登录的相关信息生成TOKEN ID
     */
    public static String generateId(String loginAccount, String accountType, String ip, String platform, Date loginTime, long ttl) {
        String noEncodeLoginTokenId = loginAccount +
                accountType +
                ip +
                platform +
                loginTime +
                ttl;
        return DigestUtils.sha256Hex(SECRET_KEY + DigestUtils.md5Hex(noEncodeLoginTokenId) + DigestUtils.md5Hex(SECRET_KEY));
    }

    /**
     * 添加登录TOKEN的ID信息到COOKIE中
     */
    public static void addLoginTokenIdToCookie(String loginTokenId, Integer expiredTimeSec) {
        HttpServletResponse response = ApplicationContextUtil.getResponse();
        CookieUtil.addCookie(response, HeaderConstant.X_TOKEN, loginTokenId, expiredTimeSec == null ? -1 : expiredTimeSec, true);
    }

    /**
     * 清理登录账号信息从COOKIE中
     */
    public static void delLoginTokenIdFromCookie() {
        HttpServletRequest request = ApplicationContextUtil.getRequest();
        HttpServletResponse response = ApplicationContextUtil.getResponse();

        CookieUtil.delCookie(request, response, HeaderConstant.X_TOKEN);
    }

    /**
     * 获取登录的TOKEN的ID（取头信息或Cookie中）
     */
    public static String getLoginTokenId() {
        HttpServletRequest request = ApplicationContextUtil.getRequest();
        String token = request.getHeader(HeaderConstant.X_TOKEN);
        if (StringUtils.isEmpty(token)) {
            token = CookieUtil.getCookieValue(request, HeaderConstant.X_TOKEN, true);
        }
        return token;
    }

    /**
     * 将登录TOKEN信息放入请求对象
     */
    public static void addLoginTokenToRequest(LoginToken loginToken) {
        ApplicationContextUtil.getRequest().setAttribute(LOGIN_TOKEN_KEY, loginToken);
    }

    /**
     * 获取登录用户信息从请求对象 备注：使用该方法时需要在对应controller类或方法上加{@link LoginAuth}}注解
     */
    public static LoginUser getLoginUserFromRequest() {
        LoginToken loginToken = getLoginTokenFromRequest();
        if (loginToken == null) {
            return null;
        }

        return loginToken.getLoginUser();
    }

    /**
     * 获取登录TOKEN信息从请求对象 备注：使用该方法时需要在对应controller类或方法上加{@link LoginAuth}}注解
     */
    public static LoginToken getLoginTokenFromRequest() {
        Object loginTokenO = ApplicationContextUtil.getRequest().getAttribute(LOGIN_TOKEN_KEY);
        if (loginTokenO == null) {
            return null;
        }

        return (LoginToken) loginTokenO;
    }
}
