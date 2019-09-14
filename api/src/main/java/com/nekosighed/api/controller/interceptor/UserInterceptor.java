package com.nekosighed.api.controller.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.nekosighed.common.comonenum.PrefixEnum;
import com.nekosighed.common.utils.JsonResult;
import com.nekosighed.common.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 拦截器
 *
 * @author lyl
 */

@Component
public class UserInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(UserInterceptor.class);

    @Resource
    private RedisUtils redisUtils;

    /**
     * 进入 controller 之前，拦截请求进行校验
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 校验参数
        String userId = request.getHeader("userId");
        String uuidToken = request.getHeader("userToken");
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(uuidToken)) {
            fillResponseEntity(response, JsonResult.badRequestError("缺少参数"));
            return false;
        }
        // 2. 校验 userId 对应 键是否存在
        if (StringUtils.isEmpty((String) redisUtils.get(PrefixEnum.REDIS_SESSION_PREFIX.getName() + ":" + userId))) {
            fillResponseEntity(response, JsonResult.badRequestError("请登录"));
            return false;
        }

        // 3. 校验 userId 对应 键的值是否匹配
        if (!uuidToken.equals(redisUtils.get(PrefixEnum.REDIS_SESSION_PREFIX.getName() + ":" + userId))) {
            fillResponseEntity(response, JsonResult.badRequestError("账号被挤出"));
            return false;
        }

        // 返回值: false: 表示请求被拦截
        // 返回值: true: 表示请求成功
        return true;
    }

    /**
     * 将想要处理的返回内容对 HttpServletResponse 进行设置
     *
     * @param response
     * @param result
     */
    private void fillResponseEntity(HttpServletResponse response, JsonResult result){
        String headerKey;
        // 设置 header
        for (Map.Entry<String, List<String>> entry : result.getHeaders().entrySet()) {
            headerKey = entry.getKey();
            for (String str : entry.getValue()) {
                response.addHeader(headerKey, str);
            }
        }
        // 设置 status
        response.setStatus(result.getStatusCodeValue());
        // 设置 body
        try(OutputStream outputStream = response.getOutputStream()){
            outputStream.write((JSONObject.toJSONString(result.getBody())).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e){
            logger.warn("fillResponseEntity出现IOException: {}", e.toString() );
        }
        //  response.getWriter().write("适合写String和char[]");
    }

    /**
     * 请求controller之后，在视图渲染之前
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    /**
     * 请求controller之后，试图渲染之后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }


}
