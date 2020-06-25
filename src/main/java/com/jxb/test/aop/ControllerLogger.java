package com.jxb.test.aop;

import com.alibaba.fastjson.JSON;
import com.jxb.test.utils.SuperClassReflectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * aop 日志输出类
 * @author zhaohf
 * @date 2020/6/25 13:55
 */
@SuppressWarnings("ALL")
public class ControllerLogger {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLogger.class);

    @Autowired
    private HttpServletRequest request;

    public void before(JoinPoint jp) throws IllegalAccessException {
        // 此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
        Object[] parameterObj = jp.getArgs();
        List<Object> resultList = new ArrayList<Object>();
        if (ArrayUtils.isNotEmpty(parameterObj)) {
            for (Object object : parameterObj) {
                if (!(object instanceof HttpServletRequest) && !(object instanceof HttpServletResponse)&& !(object instanceof HttpSession)) {
                    resultList.add(SuperClassReflectionUtils.getDeclaredFields(object));
                }
            }
        }
        logger.info("被拦截方法调用之前调用此方法，输出此语句" + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + " 参数==" + JSON.toJSONString(resultList));
    }

    public void after(JoinPoint jp) {
        logger.info("被拦截方法调用之后调用此方法，输出此语句.." + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
    }

    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        logger.info(pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName() + " 返回数据==" + JSON.toJSONString(retVal) + " process time: " + time + " ms");
        return retVal;
    }

    public void throwing(JoinPoint jp, Throwable ex) {
        logger.info("method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + " throw exception");
        logger.info(ex.getMessage());
    }
}
