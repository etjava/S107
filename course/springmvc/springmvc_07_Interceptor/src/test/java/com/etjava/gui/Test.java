package com.etjava.gui;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Hashtable<String,Object> map = new Hashtable<>();
        for(int i=1;i<=12;i++){
            map.put(""+i,"i="+i);
        }

        Class c  = Class.forName("java.util.Hashtable");
        Field field = c.getDeclaredField("table");// threshold 12 可以hold多少个元素
        field.setAccessible(true);
        Map.Entry[] en = (Map.Entry[]) field.get(map);
        System.out.println(en.length);
    }
}
