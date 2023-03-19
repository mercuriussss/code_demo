package com.ouroboros.toolutils.service;

import com.ouroboros.toolutils.utils.SpringContextUtils;
import groovy.lang.GroovyClassLoader;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
public class GroovyScriptService {

    /**
     * 功能：通过接口传过来的代码入参，动态生成Bean并执行指定方法
     * 技术点：groovy类加载器，spring自动注入bean
     * @param groovyScript
     * @throws Exception
     */
    public void runScript(String groovyScript, String methodName) throws Exception {
        if (groovyScript != null && methodName != null) {
            // 使用groovy的api动态去加载并生成一个Class，再利用反射生成对象，最后执行指定方法
            Class clazz = new GroovyClassLoader().parseClass(groovyScript);
            Method run = clazz.getMethod(methodName);
            Object o = clazz.getDeclaredConstructor().newInstance();
            // 通过SpringContextUtils.autowireBean，实现自动注入bean
            SpringContextUtils.autowireBean(o);
            run.invoke(o);
        } else {
            System.out.println("no script");
        }
    }
}
