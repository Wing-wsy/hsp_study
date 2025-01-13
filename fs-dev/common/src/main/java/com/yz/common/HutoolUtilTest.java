package com.yz.common;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.yz.common.result.GraceResult;
import com.yz.common.util.JSONUtils;
import com.yz.common.util.StrUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HutoolUtilTest {
    public static void main(String[] args) {

//        Object[] arguments = new Object[5];
//
//        String[] excludeProperties = {};
//        PropertyPreFilters filters = new PropertyPreFilters();
//        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
//        excludefilter.addExcludes(excludeProperties);
//        String jsonString = JSONUtils.toJSONString(arguments, excludefilter);
//        System.out.println(jsonString);

//        String str = "This is a test string";
//        String str = "Thi i";
//        char charToFind = 'i';
//        int n = 2; // 第2次出现的位置
//
//        int position = StrUtils.findNthOccurrence(str, charToFind, n);
//        System.out.println("第" + n + "次出现的位置: " + position);

        String str = "/";
        char c = str.charAt(0);
        System.out.println(c);




    }
}
