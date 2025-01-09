package com.yz.common;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.yz.common.result.GraceResult;
import com.yz.common.util.JSONUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HutoolUtilTest {
    public static void main(String[] args) {

//        JSONObject jsonObject = JSONUtils.createJSONObject();
//        jsonObject.put("a","1");
//        jsonObject.put("b","2");
//
//        JSONObject jsonObject1 = JSONUtils.createJSONObject();
//        jsonObject1.put("c","3");
//        jsonObject1.put("d",4);
//        jsonObject.put("obj",jsonObject1);
//
        JSONArray jsonArray = JSONUtils.createJSONArray();
        jsonArray.add("1");
        jsonArray.add("2");
        jsonArray.add("3");
//        System.out.println(jsonArray);
//        jsonObject.put("arr",jsonArray);
//
//        System.out.println(jsonObject);
//
        String jsonStr = JSONUtils.toJsonStr(jsonArray);
        System.out.println(jsonStr);

        JSONArray objects = JSONUtils.parseArray(jsonStr);
        System.out.println(objects);



//        Stu stu = new Stu();
//        stu.setName("wing");
//        stu.setAge(11);
//
//        Teacher teacher = new Teacher();
//        teacher.setName("li");
//        teacher.setAge(18);
//        teacher.setStu(stu);
////
//        String jsonStr = JSONUtils.toJsonStr(stu);
//        System.out.println(jsonStr);
////
////        Stu bean = JSONUtils.toBean(jsonStr, Stu.class);
////        System.out.println(bean);
////
//        String jsonStr1 = JSONUtils.toJsonStr(teacher);
//        System.out.println(jsonStr1);
////
////        Teacher bean1 = JSONUtils.toBean(jsonStr1, Teacher.class);
////        System.out.println(bean1);
//
//        JSONObject entries = JSONUtils.parseObj(jsonStr1);
//        System.out.println(entries);



    }
}
