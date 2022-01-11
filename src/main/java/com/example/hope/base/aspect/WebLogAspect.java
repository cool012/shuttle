package com.example.hope.base.aspect;

import com.example.hope.annotation.WebLog;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description 日志切面类
 * @Author duhongyu
 * @Data 2022/1/11 11:30
 **/
@Aspect
@Component
@Log4j2
public class WebLogAspect {

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.example.hope.annotation.WebLog)")
    public void point() {
    }

    /**
     * 在切点之后切入
     *
     * @param joinPoint joinPoint
     */
    @AfterReturning(value = "@annotation(webLog)")
    public void doAfter(JoinPoint joinPoint, WebLog webLog) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Object[] args = joinPoint.getArgs();
            String result = parseExpression(webLog.description(), method, args);
            log.info(result);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * 获取切面注解的描述
     *
     * @param joinPoint 切点
     * @return 描述信息
     * @throws Exception
     */
    private String getAspectLogDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazz = method.getParameterTypes();
                if (clazz.length == arguments.length) {
                    description.append(method.getAnnotation(WebLog.class).description());
                    break;
                }
            }
        }
        return description.toString();
    }

    /**
     * SPEL 解析
     *
     * @param expressionString expressionString
     * @param method           method
     * @param args             args
     * @return String
     */
    private String parseExpression(String expressionString, Method method, Object[] args) {
        //获取被拦截方法参数名列表
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNameArr = discoverer.getParameterNames(method);
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        assert paramNameArr != null;
        for (int i = 0; i < paramNameArr.length; i++) {
            context.setVariable(paramNameArr[i], args[i]);
        }
        return parser.parseExpression(expressionString).getValue(context, String.class);
    }
}
