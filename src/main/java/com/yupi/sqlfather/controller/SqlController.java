package com.yupi.sqlfather.controller;

import com.yupi.sqlfather.common.BaseResponse;
import com.yupi.sqlfather.common.ErrorCode;
import com.yupi.sqlfather.common.ResultUtils;
import com.yupi.sqlfather.core.GeneratorFacade;
import com.yupi.sqlfather.core.model.vo.GenerateVO;
import com.yupi.sqlfather.core.schema.TableSchema;
import com.yupi.sqlfather.core.schema.TableSchemaBuilder;
import com.yupi.sqlfather.exception.BusinessException;
import com.yupi.sqlfather.model.dto.GenerateByAutoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * SQL 相关接口
 *
 * @author https://github.com/liyupi
 */
@RestController
@RequestMapping("/sql")
@Slf4j
public class SqlController {

    /**
     * 对应按钮 "一键生成"
     */
    @PostMapping("/generate/schema")
    public BaseResponse<GenerateVO> generateBySchema(@RequestBody TableSchema tableSchema) {
        return ResultUtils.success(GeneratorFacade.generateAll(tableSchema));
    }

    /**
     * 智能导入
     */
    @PostMapping("/get/schema/auto")
    public BaseResponse<TableSchema> getSchemaByAuto(@RequestBody GenerateByAutoRequest autoRequest) {
        if (autoRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(TableSchemaBuilder.buildFromAuto(autoRequest.getContent()));
    }

    /**
     * 对应按钮 "导入Excel"
     *
     * @param file excel 文件
     */
    @PostMapping("/get/schema/excel")
    public BaseResponse<TableSchema> getSchemaByExcel(MultipartFile file) {
        return ResultUtils.success(TableSchemaBuilder.buildFromExcel(file));
    }

}
