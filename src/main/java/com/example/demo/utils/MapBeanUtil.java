package com.example.demo.utils;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapBeanUtil {

    /**
     * 实体对象转成Map
     */
    public static Map<String, String> object2Map(Object obj) {
        Map<String, String> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
