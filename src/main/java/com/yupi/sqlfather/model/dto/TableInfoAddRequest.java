package com.yupi.sqlfather.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author https://github.com/liyupi
 */
@Data
public class TableInfoAddRequest implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    private static final long serialVersionUID = 1L;
}