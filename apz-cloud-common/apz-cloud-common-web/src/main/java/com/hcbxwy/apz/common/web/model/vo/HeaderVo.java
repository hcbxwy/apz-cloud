/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：HeaderVo.java</li>
 * <li>日期：2019/4/23 18:02</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 请求头部参数对象
 *
 * @author Billson
 * @since 2019/4/23 18:02
 */
@Data
public class HeaderVo {

    /**
     * 网关签名
     */
    @NotBlank(message = "请求头中X-Ca-Proxy-Signature参数不能为空")
    private String xCaProxySignature;

    /**
     * 秘钥
     */
    @NotBlank(message = "请求头中X-Ca-Proxy-Signature-Secret-Key参数不能为空")
    private String xCaProxySignatureSecretKey;
}
