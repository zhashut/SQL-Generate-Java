package com.yupi.sqlfather.core;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yupi.sqlfather.core.builder.DataBuilder;
import com.yupi.sqlfather.core.builder.JsonBuilder;
import com.yupi.sqlfather.core.builder.SqlBuilder;
import com.yupi.sqlfather.core.model.vo.GenerateVO;
import com.yupi.sqlfather.core.schema.SchemaException;
import com.yupi.sqlfather.core.schema.TableSchema;
import com.yupi.sqlfather.core.schema.TableSchema.Field;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 集中数据生成器
 * 门面模式，统一生成
 *
 * @author: 炸薯条
 * Date: 2023/6/4
 * Time: 15:32
 * Description: No Description
 */
@Component
@Slf4j
public class GeneratorFacade {

    /**
     * 生成所有内容
     *
     * @param tableSchema
     * @return
     */
    public static GenerateVO generateAll(TableSchema tableSchema) {
        // 校验
        validSchema(tableSchema);
        SqlBuilder sqlBuilder = new SqlBuilder();
        // 构造建表 SQL
        String createSql = sqlBuilder.buildCreateTableSql(tableSchema);
        int mockNum = tableSchema.getMockNum();
        // 生成模拟数据
        List<Map<String, Object>> dataList = DataBuilder.generateData(tableSchema, mockNum);
        // 生成插入 SQL
        String insertSql = sqlBuilder.buildInsertSql(tableSchema, dataList);
        // 生成数据 json
        String dataJson = JsonBuilder.buildJson(dataList);
        // 封装返回
        GenerateVO generateVO = new GenerateVO();
        generateVO.setTableSchema(tableSchema);
        generateVO.setCreateSql(createSql);
        generateVO.setDataList(dataList);
        generateVO.setInsertSql(insertSql);
        generateVO.setDataJson(dataJson);
        return generateVO;
    }

    /**
     * 验证 schema
     *
     * @param tableSchema 表概要
     */
    public static void validSchema(TableSchema tableSchema) {
        if (tableSchema == null) {
            throw new SchemaException("数据为空");
        }
        String tableName = tableSchema.getTableName();
        if (StringUtils.isBlank(tableName)) {
            throw new SchemaException("表名不能为空");
        }
        Integer mockNum = tableSchema.getMockNum();
        // 默认生成 20 条
        if (tableSchema.getMockNum() == null) {
            tableSchema.setMockNum(20);
            mockNum = 20;
        }
        if (mockNum > 100 || mockNum < 10) {
            throw new SchemaException("生成条数设置错误");
        }
        List<Field> fieldList = tableSchema.getFieldList();
        if (CollectionUtils.isEmpty(fieldList)) {
            throw new SchemaException("字段列表不能为空");
        }
        for (Field field : fieldList) {
            validField(field);
        }
    }

    /**
     * 校验字段
     *
     * @param field
     */
    public static void validField(Field field) {
        String fieldName = field.getFieldName();
        String fieldType = field.getFieldType();
        if (StringUtils.isBlank(fieldName)) {
            throw new SchemaException("字段名不能为空");
        }
        if (StringUtils.isBlank(fieldType)) {
            throw new SchemaException("字段类型不能为空");
        }
    }

}
