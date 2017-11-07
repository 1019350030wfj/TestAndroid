package com.jayden.versionopo.api.reflection;

/**
 * Created by Jayden on 2017/7/12.
 */

public class BaseClass {
    public int baseInt;

    private static void method3(){
        System.out.println("Method3");
    }

    public int method4(){
        System.out.println("Method4");
        return 0;
    }

    public static int method5(){
        System.out.println("method5");
        return 0;
    }

    void method6(){
        System.out.println("Method6");
    }

    //inner public class
    public class BaseClassInnerClass{}

    //member public enum
    public enum BaseClassMemberEnum{}
}
