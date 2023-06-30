package com.ouroboros.toolutils.ratelimiter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class QpsLimitAspect {

    @Autowired
    private TokenBucketRateLimiter rateLimiter;

    @Around("@annotation(com.ouroboros.toolutils.ratelimiter.QpsLimit)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        QpsLimit qpsLimit = method.getAnnotation(QpsLimit.class);

        Object[] args = pjp.getArgs();
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer localVariableTable = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = localVariableTable.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        if (paraNameArr != null) {
            for(int i = 0;i < paraNameArr.length;i++) {
                context.setVariable(paraNameArr[i], args[i]);
            }
        }


        String keySpel = qpsLimit.key();
        String key = keySpel;
        // 使用变量方式传入业务动态数据
        if(keySpel.matches("^#.*.$")) {
            key = parser.parseExpression(keySpel).getValue(context, String.class);
        }

        ApiEnum apiEnum = qpsLimit.api();
        String apiKey = apiEnum.getKey() + ":" + key;
        int requestedTokens = 1;
        RateLimiterRule rule = RateLimiterRule.builder()
                .key(key)
                .rate(apiEnum.getRatePerSecond())
                .bucketCapacity(apiEnum.getBucketCapacity())
                .requestedTokens(requestedTokens)
                .build();

        // 获取令牌
        if (rateLimiter.acquireToken(rule)) {
            return pjp.proceed();
        } else {
            // 这里可以自定义一个QPS异常
            throw new Exception("获取令牌失败，QPS限制");
        }
    }
}
