package com.company.command;

import java.util.HashMap;
import java.util.Map;

public class Config {
    private static Map<String, Integer>  registerPointers;

    static {
        registerPointers = new HashMap<>();
        registerPointers.put("SP",0);
        registerPointers.put("local",1);
        registerPointers.put("argument",2);
        registerPointers.put("this",3);
        registerPointers.put("that",4);
    }

    static Integer getRegisterPointersKey(String key) {
        return registerPointers.get(key);
    }
}
