package com.zimu.controller;

import com.zimu.model.Stu;
import com.zimu.grace.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "用户管理")
public class HelloController {

    @GetMapping("/hello")
    @ApiOperation("根据ID查询用户")
    public Object hello(){

        Stu stu = new Stu("Tom", 10086);
        log.info(stu.toString());

        return GraceJSONResult.ok(stu);
    }
}
