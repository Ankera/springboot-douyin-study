package com.zimu.controller;

import com.zimu.grace.result.GraceJSONResult;
import com.zimu.utils.IPUtil;
import com.zimu.utils.SMSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.net.IPv6Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(tags = "PassportController 通信验证接口")
@RequestMapping("passport")
@RestController
public class PassportController {

    @Autowired
    private SMSUtils smsUtils;

    @PostMapping("getSMSCode")
    @ApiOperation("发送验证码")
    public Object getSMSCode(@RequestParam String mobile, HttpServletRequest request) throws Exception {
        if(StringUtils.isBlank(mobile)) {
            return GraceJSONResult.error();
        }

//        String code = "123456";
//        smsUtils.sendSMS(mobile, code);

        // 根据用户ip进行限制，限制用户在60秒之内只能获得一次验证码
        String userIp = IPUtil.getRequestIp(request);

        String code = (int)((Math.random() * 9 + 1) * 100000) + "";
        smsUtils.sendSMS(mobile, code);
//        smsUtils.sendSMS(mobile, code);
        log.info("正在使用的验证码=>" + code);

        // 把验证码放入到redis中，用于后续的验证

        return GraceJSONResult.ok("验证码发送成功");
    }
}
