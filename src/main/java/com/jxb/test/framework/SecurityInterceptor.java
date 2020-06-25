package com.jxb.test.framework;

import com.jxb.test.utils.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 拦截器自定义类
 * @author zhaohf
 * @date 2020/6/25 14:00
 */
public class SecurityInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

    /**
     * 不需要拦截的资源
     */
    private List<String> excludeUrls;

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    /**
     * 完成页面的render后调用
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
                                Exception exception) throws Exception {

    }

    /**
     * 在调用controller具体方法后拦截
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
                           ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在调用controller具体方法前拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        //logger.info(String.format("拦截器getRequestURI:<%s>",request.getRequestURI()));
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        if (checkExcludeUrls(url)) {
            return true;
        }
        ModelAndView mv = new ModelAndView();
        String userName = (String) WebUtils.getSessionAttribute(request,"userName");
        //logger.info(String.format("拦截器session中获取用户信息:<%s>",userName));
        //String checkCode = (String)WebUtils.getSessionAttribute(request,"checkCode");
        //logger.info(String.format("拦截器session中获取验证码信息:<%s>",checkCode));
        if(StringUtils.isEmpty(userName)){
            response.sendRedirect(request.getContextPath()+"/manager/login/");
            return Boolean.parseBoolean(null);
        }
        return true;
    }

    /**
     * 验证url是否是不需要拦截的
     *
     * @param url
     * @return
     */
    public boolean checkExcludeUrls(String url) {
        if(CollectionUtils.isNotEmpty(excludeUrls)){
            for (String strUrl : excludeUrls) {
                if (url.contains(strUrl)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkAppUserLogin(){

        return false;
    }
}
