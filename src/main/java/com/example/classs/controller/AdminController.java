package com.example.classs.controller;

import com.example.classs.common.ResponseCode;
import com.example.classs.common.ResponseEntity;
import com.example.classs.entity.Catalog;
import com.example.classs.entity.Item;
import com.example.classs.entity.Protocol;
import com.example.classs.service.AdminService;
import com.example.classs.service.ProtocolService;
import com.example.classs.util.DownloadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.nio.file.Paths;
import java.util.Map;

@Api(value = "/admin", tags = "管理页面接口")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProtocolService protocolService;

    @Autowired
    private AdminService adminService;

    @ApiOperation("测试")
    @PostMapping("/createProtocol")
    public ResponseEntity createProtocol(@RequestBody Protocol protocol, HttpServletResponse response) {
        String regPath = protocolService.createProtocol(protocol);
        DownloadUtil.download(response, Paths.get(regPath).toFile(), true);
        return ResponseEntity.ok();
    }

    @ApiOperation("获取tag页")
    @GetMapping("/getTags/{pageNo}/{pageSize}")
    public ResponseEntity getTags(@Min(value = 1, message = "页码至少为1") @PathVariable Integer pageNo, @Min(value = 1, message = "每也展示至少为1") @PathVariable Integer pageSize) {
        Map<String, Object> tags = adminService.getTags(pageNo, pageSize);
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), tags, "查询成功");
    }

    @ApiOperation("获取subject")
    @GetMapping("/getSubjects/{id}/{pageNo}/{pageSize}")
    public ResponseEntity getSubjects(@PathVariable Integer id, @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        Map<String, Object> subjects = adminService.getSubject(id, pageNo, pageSize);
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), subjects, "查询成功");
    }

    @ApiOperation("更新目录状态")
    @PutMapping("/updateCatalogState")
    public ResponseEntity updateCatalogState(@RequestBody Catalog catalog) {
        adminService.updateCatalogState(catalog);
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), null, "更新成功");
    }

    @ApiOperation("更新目录")
    @PutMapping("/updateCatalog")
    public ResponseEntity updateCatalog(@RequestBody Catalog catalog) {
        Catalog newCatalog = adminService.saveOrUpdateCatalog(catalog);
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), newCatalog, "更新成功");
    }


    @ApiOperation("新增目录")
    @PostMapping("/saveCatalog")
    public ResponseEntity saveCatalog(@RequestBody Catalog catalog) {
        Catalog newCatalog = adminService.saveOrUpdateCatalog(catalog);
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), newCatalog, "新增成功");
    }


    @ApiOperation("删除目录")
    @DeleteMapping("/deleteCatalog/{id}")
    public ResponseEntity deleteCatalog(@PathVariable Integer id) {
        adminService.deleteCatalog(id);
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), null, "删除成功");
    }

    @ApiOperation("更新子项目状态")
    @PutMapping("/updateItemState")
    public ResponseEntity updateItemState(@RequestBody Item item) {
        adminService.updateItemState(item);
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), null, "更新成功");
    }

    @ApiOperation("更新子项目")
    @PutMapping("/updateItem")
    public ResponseEntity updateItem(@RequestBody Item item) {
        Item newItem = adminService.saveOrUpdateItem(item);
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), newItem, "更新成功");
    }


    @ApiOperation("新增子项目")
    @PostMapping("/saveItem")
    public ResponseEntity saveItem(@RequestBody Item item) {
        Item newItem = adminService.saveOrUpdateItem(item);
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), newItem, "新增成功");
    }


    @ApiOperation("删除子项目")
    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity deleteItem(@PathVariable Integer id) {
        adminService.deleteItem(id);
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), null, "删除成功");
    }


}
