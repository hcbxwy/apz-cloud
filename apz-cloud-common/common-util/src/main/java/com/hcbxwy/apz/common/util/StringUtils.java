/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：StringUtils.java</li>
 * <li>日期：2019/4/11 11:34</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.util;

/**
 * 字符串工具类
 *
 * @author Billson
 * @since 2019/4/11 11:34
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     *  格式化字符串（替换符为%s）
     */
    public static String formatIfArgs(String format, Object... args) {
        if (isEmpty(format)) {
            return format;
        }
        return (args == null || args.length == 0)  ? format.replaceAll("%([^n])", "%%$1") : String.format(format, args);
    }

    /**
     *  格式化字符串(替换符自己指定)
     */
    public static String formatIfArgs(String format, String replaceOperator, Object... args) {
        if (isEmpty(format) || isEmpty(replaceOperator)) {
            return format;
        }

        format = replace(format, replaceOperator, "%s");
        return formatIfArgs(format, args);
    }

}
