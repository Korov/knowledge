package com.korov.springboot.reflection;

import com.korov.springboot.entity.TestEntity;
import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Mytest {
    @Test
    public void test() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        TestEntity entity = new TestEntity();
        entity.setId(11L);
        entity.setUserName("nameaaa");
        Class class1 = entity.getClass();

        Class class2 = TestEntity.class;

        Class class3 = Class.forName("java.util.Random");
        // 通过构造器创建一个实例
        //Object object = class3.getDeclaredConstructor(String.class, String.class).newInstance("value1", "value2");
        List<Method> methods = Arrays.stream(class1.getMethods()).collect(Collectors.toList());
        methods.retainAll(new ArrayList<>());
        System.out.println(class1.getFields().toString());
        System.out.println(class1.getMethods().toString());
        System.out.println(class1.getConstructors().toString());

        Field f = class1.getDeclaredField("userName");
        f.setAccessible(true);
        Object v = f.get(entity);
        System.out.println(v.toString());
        f.set(entity, "my name");
        v = f.get(entity);
        System.out.println(v.toString());

        Method getUserName = class1.getDeclaredMethod("getUserName");
        // String.class是参数的class
        Method setUserName = class1.getDeclaredMethod("setUserName", String.class);
        setUserName.invoke(entity, "your name");
        String value = (String) getUserName.invoke(entity);
        System.out.println(value);
    }

    public Object goodCopyOf(Object a, int newLength) {
        Class class1 = a.getClass();
        if (!class1.isArray()) {
            return new Object();
        }
        Class componentType = class1.getComponentType();
        int length = Array.getLength(a);
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));
        return newArray;
    }
}
