package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wing
 * @create 2024/3/30
 * 演示转义字符的使用
 */
public class RegExp01 {

    public static void main(String[] args) {
        String content = "519156253345";
        boolean matches = content.matches("[0-9]{12}");
//        boolean matches = content.matches("^[0-9+-(]{4,6}$");
//        boolean matches = content.matches("[0-9,+,-,]{6}");
        if (matches) {
            System.out.println("验证成功");
        }else {
            System.out.println("验证失败");
        }

    }
//    public static void main(String[] args) {
//        String content = "标志着应用开始普遍 。最开始后应用";
//        // 请在字符串中检素商品编号，，形式如：12321-333999111 这样的号码，要求满足前面是一个五位数，然后一个-号，然后是一个九位数，连续的每三位要相同
//        String regStr = "开始";
//        Pattern pattern = Pattern.compile(regStr);
//        Matcher matcher = pattern.matcher(content);
//        String s = matcher.replaceAll("");
//        System.out.println(s);
//
////        while (matcher.find()) {
////            System.out.println("找到：" + matcher.group(0));
////        }
//    }

}
