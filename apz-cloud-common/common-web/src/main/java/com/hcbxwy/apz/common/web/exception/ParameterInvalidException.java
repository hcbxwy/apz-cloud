/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：ParameterInvalidException.java</li>
 * <li>日期：2019/4/22 15:48</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.exception;

import com.hcbxwy.apz.common.web.enums.ResultCodeEnum;

/**
 * 参数无效异常
 *
 * @author Billson
 * @since 2019/4/22 15:48
 */
public class ParameterInvalidException extends BusinessException {

    private static final long serialVersionUID = -907171717806319916L;

    public ParameterInvalidException() {
        super();
    }

    public ParameterInvalidException(Object data) {
        super.data = data;
    }

    public ParameterInvalidException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum);
    }

    public ParameterInvalidException(ResultCodeEnum resultCodeEnum, Object data) {
        super(resultCodeEnum, data);
    }

    public ParameterInvalidException(String msg) {
        super(msg);
    }

    public ParameterInvalidException(String formatMsg, Object... objects) {
        super(formatMsg, objects);
    }
}
