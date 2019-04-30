/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：BasePO.java</li>
 * <li>日期：2019/4/22 20:18</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 *  基础PO类
 *
 * @author Billson
 * @since 2019/4/22 20:18
 */
@Data
public abstract class BasePO<PK> implements PO<PK> {

    private static final long serialVersionUID = 6833133858177087122L;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "update_time")
    private Date updateTime;
}
