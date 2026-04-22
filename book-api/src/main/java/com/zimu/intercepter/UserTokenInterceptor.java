package com.zimu.intercepter;

import com.zimu.controller.BaseInfoProperties;
import com.zimu.exceptions.GraceException;
import com.zimu.grace.result.ResponseStatusEnum;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserTokenInterceptor extends BaseInfoProperties implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从header中获得用户id和token
        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");
        // 判断header中用户id和token不能为空
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)) {
            String redisToken = redis.get(REDIS_USER_TOKEN + ":" + userId);
            if (StringUtils.isBlank(redisToken)) {
                GraceException.display(ResponseStatusEnum.UN_LOGIN);
                return false;
            } else {
                // 比较token是否一致，如果不一致，表示用户在别的手机端登录
                if (!redisToken.equalsIgnoreCase(userToken)) {
                    GraceException.display(ResponseStatusEnum.TICKET_INVALID);
                    return false;
                }
            }
        } else {
            GraceException.display(ResponseStatusEnum.UN_LOGIN);
            return false;
        }

        /**
         * true: 请求放行
         * false: 请求拦截
         */
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
