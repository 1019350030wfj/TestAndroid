package com.jayden.testandroid.api.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 注解处理器
 * Created by Jayden on 2017/3/25.
 */

public class AnnoInjection {
    public static Object getBean(Object obj){
        try {
            //get Field
            Field f[] = obj.getClass().getDeclaredFields();
            //traverse field
            for (Field ff:f){
                //get annotation of field
                Seven s = ff.getAnnotation(Seven.class);
                if (s != null){
                    System.out.println("注入 " + ff.getName() + " 属性="+s.value());
                    //反射调用public set方法，如果为访问级别，那么可以直接使用属性的set(obj,value)
                    obj.getClass().getMethod("set" + ff.getName().substring(0,1).toUpperCase()+ff.getName().substring(1),
                            new Class[]{String.class})
                            .invoke(obj,s.value());
                }
            }

            //get all method
            Method m[] = obj.getClass().getDeclaredMethods();
            for (Method mm:m){
                //get annotation of method
                Seven s = mm.getAnnotation(Seven.class);
                if (s != null){
                    System.out.println("注入 = "+ mm.getName() + "方法="+s.Property());
                    mm.invoke(obj,s.Property());
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
