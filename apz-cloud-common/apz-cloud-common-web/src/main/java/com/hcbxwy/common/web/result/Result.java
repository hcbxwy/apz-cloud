/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：Result.java</li>
 * <li>日期：2019/4/19 15:34</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.common.web.result;

import com.hcbxwy.common.web.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 接口返回对象
 *
 * @author Billson
 * @since 2019/4/19 15:34
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result implements Serializable {

    private Integer code;

    private String msg;

    private Object data;

    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    public static Result failure(String message) {
        Result result = new Result();
        result.setCode(ResultCode.PARAM_IS_INVALID.code());
        result.setMsg(message);
        return result;
    }

    private void setResultCode(ResultCode resultCode) {
        this.code = resultCode.code();
        this.msg = resultCode.message();
    }
}
