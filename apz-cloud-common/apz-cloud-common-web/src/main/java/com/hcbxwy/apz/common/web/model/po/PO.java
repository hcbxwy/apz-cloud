/*
 * <ul>
 * <li>项目名称：apz-cloud</li>
 * <li>文件名称：PO.java</li>
 * <li>日期：2019/4/22 20:17</li>
 * <li>Copyright ©2016-2019 广州职赢未来信息科技有限公司 All Rights Reserved.</li>
 * </ul>
 */
package com.hcbxwy.apz.common.web.model.po;

import com.hcbxwy.apz.common.web.model.Model;

import java.util.Date;

/**
 * 公共PO
 *
 * @author Billson
 * @since 2019/4/22 20:17
 */
public interface PO<PK> extends Model {

    PK getId();

    void setId(PK id);

    Date getCreateTime();

    void setCreateTime(Date createTime);

    Date getUpdateTime();

    void setUpdateTime(Date updateTime);
}
