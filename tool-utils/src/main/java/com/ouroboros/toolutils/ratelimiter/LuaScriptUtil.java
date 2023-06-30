package com.ouroboros.toolutils.ratelimiter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class LuaScriptUtil {

    private static final Map<LuaScriptEnum, String> luaScriptMap = new HashMap<>();

    static {
        for (LuaScriptEnum luaScriptEnum : LuaScriptEnum.values()) {
            luaScriptMap.put(luaScriptEnum, getRateLimiterScript(luaScriptEnum.getScriptRef()));
        }
    }

    private static String getRateLimiterScript(String scriptFileName) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(scriptFileName);
        try {
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("{} Initialization Lua failure", scriptFileName, e);
            throw new RuntimeException(e);
        }
    }

    public static String getLuaScript(LuaScriptEnum scriptEnum) {
        return luaScriptMap.get(scriptEnum);
    }
}