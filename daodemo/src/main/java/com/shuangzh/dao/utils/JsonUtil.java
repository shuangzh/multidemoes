package com.shuangzh.dao.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuangzh.dao.mybatis.domain.Student;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/3/17.
 */
public class JsonUtil {

    public static String toJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    public static Map<String, Object> toJsonMap(Object obj) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(toJsonString(obj), Map.class);
    }

    public static Object toObject(String json, Class clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    public static Map<String, Object> plusElements(Object target, Map<String, Object> plusmap) throws IOException {
        Map targmap = toJsonMap(toJsonMap(target));
        targmap.putAll(plusmap);
        return targmap;
    }

    public static String plusElementsAsString(Object target, Map<String, Object> plusmap) throws IOException {
        return toJsonString(plusElements(target, plusmap));
    }

    public Map<String, Object> removeElements(Object target, String[] elemnames) throws IOException {
        Map<String, Object> tarmap = toJsonMap(target);
        for (String name : elemnames) {
            String[] strs = name.split("\\.");
            int l = strs.length;
            if (l == 1) {
                tarmap.remove(strs[0]);
                continue;
            } else {
                Map<String, Object> tmpmap = tarmap;
                for (int i = 0; i < l - 1; i++) {
                    Object val = tmpmap.get(strs[i]);
                    if (val == null) {
                        tmpmap = null;
                        break;
                    }
                    if (val instanceof Map) {
                        tmpmap = (Map<String, Object>) val;
                    } else {
                        throw new IOException("cant remove element, " + name + " at " + strs[i] + " is not map");
                    }
                }
                if (tarmap != null) {
                    tmpmap.remove(strs[l - 1]);
                }
            }

        }
        return tarmap;
    }

    public String removeElementsAsString(Object target, String[] elemnames) throws IOException {
        return toJsonString(removeElements(target, elemnames));
    }

    public Object pickElement(Map<String, Object> jsonmap, String elemname) {
        String[] strs = elemname.split("\\.");
        Map<String, Object> tmpmap = jsonmap;
        Object val = null;
        for (int i = 0; i < strs.length; i++) {
            if (tmpmap.containsKey(strs[i])) {
                val = tmpmap.get(strs[i]);
                if (i == strs.length - 1) {
                    break;
                }
                if (val instanceof Map) {
                    tmpmap = (Map<String, Object>) val;
                } else {
                    return null;
                }
            } else
                return null;
        }
        return val;
    }


    public static String addElement(String json, String elemName, Object element, boolean force) throws IOException {
        Map map = (Map) toObject(json, Map.class);
        String[] names = elemName.split("\\.");
        int i = names.length;
        if (i == 1) {
            map.put(names[0], element);
        } else {
            Map tmap = map;
            for (int k = 0; k < i - 1; k++) {
                Object obj = tmap.get(names[k]);
                if (obj == null) {
                    Map nmap = new HashMap<String, Object>();
                    tmap.put(names[k], nmap);
                    tmap = nmap;
                    continue;
                } else if (obj instanceof Map) {
                    tmap = (Map) obj;
                    continue;
                } else {
                    if (force) {
                        Map nmap = new HashMap<String, Object>();
                        tmap.put(names[k], nmap);
                        tmap = nmap;
                        continue;
                    } else {
                        throw new IOException("Element " + k + " [" + names[k] + "] is not a Map");
                    }
                }
            }
            tmap.put(names[i - 1], element);
        }
        return toJsonString(map);
    }


    public static void main(String[] args) throws IOException {
        Student student = new Student();
        student.setStudId(10);
        student.setName("zhoushuang");
        student.setEmail("shuang@qq.com");

        System.out.println(JsonUtil.toJsonString(student));

        Map map = JsonUtil.toJsonMap(student);

        map.put("newField", "hello");
        System.out.println(JsonUtil.toJsonString(map));

        int[] ints = {1, 2, 3, 5, 6};
        map.put("ints", ints);
        System.out.println(JsonUtil.toJsonString(map));

        Map nmap = (Map) JsonUtil.toObject(JsonUtil.toJsonString(map), Map.class);

        nmap.put("third", "thred json");
        System.out.println(JsonUtil.toJsonString(nmap));

        String json = JsonUtil.toJsonString(nmap);
        System.out.println(addElement(json, "bubu.names", new String[]{"n1", "n2"}, true));

    }

}
