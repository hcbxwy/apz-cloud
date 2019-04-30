/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：BusinessException.java</li>
 * <li>日期：2019/4/22 15:23</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.exception;

import com.hcbxwy.apz.common.web.enums.BusinessExceptionEnum;
import com.hcbxwy.apz.common.web.enums.ResultCodeEnum;
import com.hcbxwy.apz.common.util.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常类
 *
 * @author Billson
 * @since 2019/4/22 15:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 4282557553773422598L;

    protected String code;

    protected String message;

    protected ResultCodeEnum resultCodeEnum;

    protected Object data;

    public BusinessException() {
        BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.getByEClass(this.getClass());
        if (exceptionEnum != null) {
            resultCodeEnum = exceptionEnum.getResultCodeEnum();
            code = exceptionEnum.getResultCodeEnum().code().toString();
            message = exceptionEnum.getResultCodeEnum().message();
        }

    }

    public BusinessException(String message) {
        this();
        this.message = message;
    }

    public BusinessException(String format, Object... objects) {
        this();
        this.message = StringUtils.formatIfArgs(format, "{}", objects);
    }

    public BusinessException(ResultCodeEnum resultCodeEnum, Object data) {
        this(resultCodeEnum);
        this.data = data;
    }

    public BusinessException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.code().toString();
        this.message = resultCodeEnum.message();
    }
}
