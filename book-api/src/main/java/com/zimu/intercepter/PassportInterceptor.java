package com.zimu.intercepter;

import com.zimu.controller.BaseInfoProperties;
import com.zimu.exceptions.GraceException;
import com.zimu.grace.result.ResponseStatusEnum;
import com.zimu.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class PassportInterceptor extends BaseInfoProperties implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userIp = IPUtil.getRequestIp(request);
        boolean b = redis.keyIsExist(MOBILE_SMSCODE + ":" + userIp);
        if (b) {
            GraceException.display(ResponseStatusEnum.SMS_NEED_WAIT_ERROR);
            log.error("短信发送频率太快");
            // 拦截请求
            return false;
        }

//        return HandlerInterceptor.super.preHandle(request, response, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
