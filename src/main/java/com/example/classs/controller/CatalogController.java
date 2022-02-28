package com.example.classs.controller;

import com.example.classs.common.ResponseCode;
import com.example.classs.common.ResponseEntity;
import com.example.classs.service.CatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Map;


@Api(value = "/catalog", tags = "目录接口")
@RestController
@RequestMapping("/catalog")
@Validated
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @ApiOperation("获取所有目录")
    @GetMapping("/getCatalogs/{pageNo}/{pageSize}")
    public ResponseEntity getCatalogs(@Min(value = 1, message = "页码至少为1") @PathVariable Integer pageNo, @Min(value = 1, message = "每也展示至少为1") @PathVariable Integer pageSize) {
        Map<String, Object> catalogs = catalogService.getCatalogs(pageNo, pageSize);
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), catalogs, "查询成功");
    }

    @ApiOperation("获取子项目")
    @GetMapping("/getSubjects/{id}/{pageNo}/{pageSize}")
    public ResponseEntity getSubjects(@PathVariable Integer id, @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        Map<String, Object> subjects = catalogService.getSubject(id, pageNo, pageSize);
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), subjects, "查询成功");
    }

}
