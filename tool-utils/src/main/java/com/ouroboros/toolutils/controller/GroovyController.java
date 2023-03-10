package com.ouroboros.toolutils.controller;

import com.ouroboros.toolutils.service.GroovyScriptService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GroovyController {

    private final GroovyScriptService groovyScriptService;

    public GroovyController(GroovyScriptService groovyScriptService) {
        this.groovyScriptService = groovyScriptService;
    }

    @PostMapping("/runScript")
    public void runScript(@RequestParam String methodName, @RequestBody String groovyScript) throws Exception {
        groovyScriptService.runScript(groovyScript, methodName);
    }
}