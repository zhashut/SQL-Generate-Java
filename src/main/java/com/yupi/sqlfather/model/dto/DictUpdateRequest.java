package com.yupi.sqlfather.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @author https://github.com/liyupi
 */
@Data
public class DictUpdateRequest implements Serializable {

    /**
     * id
     */
    private long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态（0-待审核, 1-通过, 2-拒绝）
     */
    private Integer reviewStatus;

    /**
     * 审核信息
     */
    private String reviewMessage;

    private static final long serialVersionUID = 1L;
}