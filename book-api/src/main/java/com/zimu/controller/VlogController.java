package com.zimu.controller;

import com.zimu.bo.VlogBO;
import com.zimu.grace.result.GraceJSONResult;
import com.zimu.service.VlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "VlogController 用户信息接口模块")
@RequestMapping("vlog")
@RestController
public class VlogController {

    @Autowired
    private VlogService vlogService;

    @PostMapping("publish")
    @ApiOperation("创建vlog")
    public GraceJSONResult publish(@RequestBody VlogBO vlogBO) {
        vlogService.createVlog(vlogBO);
        return GraceJSONResult.ok();
    }
}
