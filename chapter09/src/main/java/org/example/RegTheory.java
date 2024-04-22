package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wing
 * @create 2024/3/30
 */
public class RegTheory {
    public static void main(String[] args) {
        String content = "1997年是发展过程中最重要的一个环节，1998标志着应用开始普遍。最后应用于移动、无线及有限资源的环境";
        // 1. \\d表示任意一个数字
        String regStr = "(\\d\\d)(\\d\\d)";
        // 2. 创建模式对象
        Pattern pattern = Pattern.compile(regStr);
        // 3. 创建匹配器
        Matcher matcher = pattern.matcher(content);
        // 4. 开始匹配
        while (matcher.find()) {
            System.out.println("找到：" + matcher.group(0));
            System.out.println("找到1组：" + matcher.group(1));
            System.out.println("找到2组：" + matcher.group(2));
            System.out.println("---------------------------");
        }

    }
}
