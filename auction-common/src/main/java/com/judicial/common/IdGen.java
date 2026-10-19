package com.judicial.common;

public class IdGen {
    public static String next(String prefix){ return prefix + System.currentTimeMillis() + "-" + (int)(Math.random()*1000); }
}
