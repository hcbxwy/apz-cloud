/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：DefaultResult.java</li>
 * <li>日期：2019/4/19 15:34</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.result;

import com.hcbxwy.apz.common.web.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 默认接口返回对象
 *
 * @author Billson
 * @since 2019/4/19 15:34
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DefaultResult implements Result {

    private static final long serialVersionUID = -691353980906699643L;

    private Integer code;

    private String msg;

    private Object data;

    public static DefaultResult success() {
        DefaultResult defaultResult = new DefaultResult();
        defaultResult.setResultCode(ResultCodeEnum.SUCCESS);
        return defaultResult;
    }

    public static DefaultResult success(Object data) {
        DefaultResult defaultResult = new DefaultResult();
        defaultResult.setResultCode(ResultCodeEnum.SUCCESS);
        defaultResult.setData(data);
        return defaultResult;
    }

    public static DefaultResult failure(ResultCodeEnum resultCodeEnum) {
        DefaultResult defaultResult = new DefaultResult();
        defaultResult.setResultCode(resultCodeEnum);
        return defaultResult;
    }

    public static DefaultResult failure(ResultCodeEnum resultCodeEnum, Object data) {
        DefaultResult defaultResult = new DefaultResult();
        defaultResult.setResultCode(resultCodeEnum);
        defaultResult.setData(data);
        return defaultResult;
    }

    public static DefaultResult failure(String message) {
        DefaultResult defaultResult = new DefaultResult();
        defaultResult.setCode(ResultCodeEnum.PARAM_IS_INVALID.code());
        defaultResult.setMsg(message);
        return defaultResult;
    }

    private void setResultCode(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.code();
        this.msg = resultCodeEnum.message();
    }
}
