package com.ouroboros.toolutils.ratelimiter;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QpsLimit {
    /**
     * key
     * @return
     */
    String key();

    /**
     * 限制的api
     * @return
     */
    ApiEnum api();
}
