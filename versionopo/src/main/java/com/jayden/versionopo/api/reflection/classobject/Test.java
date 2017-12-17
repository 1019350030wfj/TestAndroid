package com.jayden.versionopo.api.reflection.classobject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jayden.versionopo.api.reflection.ConcreteClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jayden on 2017/7/12.
 */

public class Test extends Activity {
    /**
     * 获取对象的类
     * we can get Class of an object using three methods -
     * 1、through static variable .class
     * 2、using getClass() method of object
     * 3、and java.lang.Class.forName(String fullyClassifiedClassName)
     */

    //Get Class using reflection
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("============1、获取类==============="); // prints
        Class<?> concreteClass = ConcreteClass.class;
        //concreteClass = new ConcreteClass(5).getClass();
//        try {
//            concreteClass = Class.forName("com.jayden.versionopo.api.reflection.ConcreteClass");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        System.out.println(concreteClass.getCanonicalName()); // prints
        System.out.println("============1、获取类==============="); // prints

        System.out.println("============2、获取父类==============="); // prints

        try {
            Class<?> superClass = Class.forName("com.jayden.versionopo.api.reflection.ConcreteClass").getSuperclass();
            System.out.println(superClass); // prints "class com.journaldev.reflection.BaseClass"
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(Object.class.getSuperclass()); // prints "null"
        System.out.println(String[][].class.getSuperclass());// prints "class java.lang.Object"

        System.out.println("============2、获取父类==============="); // prints
        System.out.println("============3、获取公共的成员类==============="); // prints
        System.out.println("============返回类的所有成员类，接口，枚举类，" +
                "还有他的父类的所有成员类、接口、枚举类==============="); // prints
        Class<?>[] classes = concreteClass.getClasses();
//[class com.journaldev.reflection.ConcreteClass$ConcreteClassPublicClass,
//class com.journaldev.reflection.ConcreteClass$ConcreteClassPublicEnum,
//interface com.journaldev.reflection.ConcreteClass$ConcreteClassPublicInterface,
//class com.journaldev.reflection.BaseClass$BaseClassInnerClass,
//class com.journaldev.reflection.BaseClass$BaseClassMemberEnum]
        System.out.println(Arrays.toString(classes));
        System.out.println("============3、获取公共的成员类==============="); // prints
        System.out.println("============4、获取已经声明的成员类==============="); // prints
        //getting all of the classes, interfaces, and enums that are explicitly declared in ConcreteClass

        try {
            Class<?>[] explicitClasses = Class.forName("com.jayden.versionopo.api.reflection.ConcreteClass").getDeclaredClasses();
            Arrays.toString(explicitClasses); // prints "class com.journaldev.reflection.BaseClass"
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//prints [class com.journaldev.reflection.ConcreteClass$ConcreteClassDefaultClass,
//class com.journaldev.reflection.ConcreteClass$ConcreteClassDefaultEnum,
//class com.journaldev.reflection.ConcreteClass$ConcreteClassPrivateClass,
//class com.journaldev.reflection.ConcreteClass$ConcreteClassProtectedClass,
//class com.journaldev.reflection.ConcreteClass$ConcreteClassPublicClass,
//class com.journaldev.reflection.ConcreteClass$ConcreteClassPublicEnum,
//interface com.journaldev.reflection.ConcreteClass$ConcreteClassPublicInterface]
        System.out.println("============4、获取已经声明的成员类==============="); // prints
        System.out.println("============5、获取包名==============="); // prints
        //prints "com.journaldev.reflection"
        try {
            System.out.println(Class.forName("com.jayden.versionopo.api.reflection.ConcreteClass").getPackage().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("============5、获取包名==============="); // prints
        System.out.println("============6、获取方法修饰==============="); // prints
        System.out.println(Modifier.toString(concreteClass.getModifiers())); //prints "public"
        //prints "public abstract interface"
        try {
            System.out.println(Modifier.toString(Class.forName("com.jayden.versionopo.api.reflection.ConcreteClass").getModifiers()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("============6、获取方法修饰==============="); // prints
        System.out.println("============7、获取类型参数(如：HashMap<K,V>是获取K，V)==============="); // prints
        //Get Type parameters (generics)
        TypeVariable<?>[] typeParameters = new TypeVariable<?>[0];
        try {
            typeParameters = Class.forName("java.util.HashMap").getTypeParameters();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (TypeVariable<?> t : typeParameters) {
            System.out.print(t.getName() + ",");
        }
        System.out.println("============7、获取类型参数==============="); // prints
        System.out.println("============8、获取类实现的接口===============");
        Type[] interfaces = new Type[0];
        try {
            interfaces = Class.forName("java.util.HashMap").getGenericInterfaces();
            //prints "[java.util.Map<K, V>, interface java.lang.Cloneable, interface java.io.Serializable]"
            System.out.println("getGenericInterfaces() = " + Arrays.toString(interfaces));

            //prints "[interface java.util.Map, interface java.lang.Cloneable, interface java.io.Serializable]"
            System.out.println("getInterfaces() = " + Arrays.toString(Class.forName("java.util.HashMap").getInterfaces()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("============8、获取类实现的接口===============");
        System.out.println("============9、获取类的所有公共方法===============");
        Method[] publicMethods = new Method[0];
        try {
            publicMethods = Class.forName("com.jayden.versionopo.api.reflection.ConcreteClass").getMethods();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //prints public methods of ConcreteClass, BaseClass, Object
        System.out.println(Arrays.toString(publicMethods));
        System.out.println("============9、获取类的所有公共方法===============");
        System.out.println("============10、获取类的所有公共构造函数===============");
        //Get All public constructors
        try {
            Constructor<?>[] publicConstructors = Class.forName("com.jayden.versionopo.api.reflection.ConcreteClass").getConstructors();
            //prints public constructors of ConcreteClass
            System.out.println(Arrays.toString(publicConstructors));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("============10、获取类的所有公共构造函数===============");
        System.out.println("============11、获取类的所有公共参数===============");
        try {
            //Get All public fields
            Field[] publicFields = Class.forName("com.jayden.versionopo.api.reflection.ConcreteClass").getFields();
            //prints public fields of ConcreteClass, it's superclass and super interfaces
            System.out.println(Arrays.toString(publicFields));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("============11、获取类的所有公共参数===============");
        System.out.println("============12、获取指定参数的类型===============");
        try {
            Field field = Class.forName("com.jayden.versionopo.api.reflection.ConcreteClass").getField("publicInt");
            Class<?> fieldType = field.getType();
            System.out.println(fieldType.getCanonicalName()); //prints int
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException"); //prints int
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException"); //prints int
            e.printStackTrace();
        }
        System.out.println("============12、获取指定参数的类型===============");
        System.out.println("============13、获取/设置指定参数的值===============");
        try {
            Field field = Class.forName("com.jayden.versionopo.api.reflection.ConcreteClass").getField("publicInt");
            ConcreteClass obj = new ConcreteClass(5);
            System.out.println(field.get(obj)); //prints 5
            field.setInt(obj, 10); //setting field value to 10 in object
            System.out.println(field.get(obj)); //prints 10
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException"); //prints int
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException"); //prints int
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("============13、获取/设置指定参数的值===============");
        System.out.println("============14、获取/设置指定私有参数的值===============");
        try {
            Field privateField = Class.forName("com.jayden.versionopo.api.reflection.ConcreteClass").getDeclaredField("privateString");
//turning off access check with below method call
            privateField.setAccessible(true);
            ConcreteClass objTest = new ConcreteClass(1);
            System.out.println(privateField.get(objTest)); // prints "private string"
            privateField.set(objTest, "private string updated");
            System.out.println(privateField.get(objTest)); //prints "private string updated"
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("============14、获取/设置指定私有参数的值===============");
        System.out.println("============15、获取类的方法===============");
        try {
            Method method = Class.forName("java.util.HashMap").getMethod("put", Object.class, Object.class);
//get method parameter types, prints "[class java.lang.Object, class java.lang.Object]"
            System.out.println("获取方法的参数类型=" + Arrays.toString(method.getParameterTypes()));
//get method return type, return "class java.lang.Object", class reference for void
            System.out.println("获取方法的返回值类型=" + method.getReturnType());
//get method modifiers
            System.out.println("获取方法的访问权限=" + Modifier.toString(method.getModifiers())); //prints "public"
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("============15、获取类的方法===============");
        System.out.println("============16、调用类的公共方法===============");
        try {
            Method method = Class.forName("java.util.HashMap").getMethod("put", Object.class, Object.class);
            Map<String, String> hm = new HashMap<>();
            method.invoke(hm, "name", "jayden");
            System.out.println(hm); // prints {key=value}
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("============16、调用类的公共方法===============");
//        System.out.println("============17、调用类的私有方法===============");
//        try {
//            //invoking private method
//            Method method = Class.forName("com.jayden.versionopo.api.reflection.BaseClass").getDeclaredMethod("method3", null);
//            method.setAccessible(true);
//            method.invoke(null, null); //prints "Method3", 因为Method3是静态方法，所以不用实例
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.println("============17、调用类的私有方法===============");
//        System.out.println("============18、调用类的公共构造函数===============");
//        try {
//            Constructor<?> constructors = Class.forName("com.jayden.versionopo.api.reflection.ConcreteClass").getConstructor(int.class);
//            System.out.println("构造函数的参数类型 = " + Arrays.toString(constructors.getParameterTypes()));
//            Constructor<?> hashMapConstructor = Class.forName("java.util.HashMap").getConstructor(null);
//            System.out.println(Arrays.toString(hashMapConstructor.getParameterTypes())); // prints "[]"
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        System.out.println("============18、调用类的公共构造函数===============");
//        System.out.println("============19、通过构造函数实例化对象===============");
//        try {
//            Constructor<?> constructor = Class.forName("com.jayden.versionopo.api.reflection.ConcreteClass").getConstructor(int.class);
//            Object myObj = constructor.newInstance(10);
//
//            Method method = myObj.getClass().getMethod("method1");
//            method.invoke(myObj, null);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        System.out.println("============19、通过构造函数实例化对象===============");
    }
}
