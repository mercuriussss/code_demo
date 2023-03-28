package com.ouroboros.springbootpractice.component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TransactionProxyFactory {
    //目标对象
    private static Object target;
    //关注点业务类
    private static TransactionAOP transitionAOP;
    //新建一个代理对象方法
    public static Object getProxyInstance(Object target_,TransactionAOP transitionAOP_) {
        target = target_;
        transitionAOP = transitionAOP_;
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        transitionAOP.openTransition();
                        Object ret = method.invoke(target,args);
                        transitionAOP.closeTransition();
                        return ret;
                    }
                }
        );
    }
}