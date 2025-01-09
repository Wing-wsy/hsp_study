package com.yz.common.constant;

/**
 * 基础常量集
 */
public class Basic {

    private Basic() {
    }

    /**
     * 英文字母大写
     */
    public static final String A = "A";
    public static final String B = "B";
    public static final String C = "C";
    public static final String D = "D";
    public static final String E = "E";
    public static final String F = "F";
    public static final String G = "G";
    public static final String H = "H";
    public static final String I = "I";
    public static final String J = "J";
    public static final String K = "K";
    public static final String L = "L";
    public static final String M = "M";
    public static final String N = "N";
    public static final String O = "O";
    public static final String P = "P";
    public static final String Q = "Q";
    public static final String R = "R";
    public static final String S = "S";
    public static final String T = "T";
    public static final String U = "U";
    public static final String V = "V";
    public static final String W = "W";
    public static final String X = "X";
    public static final String Y = "Y";
    public static final String Z = "Z";

    /**
     * 英文字母小写
     */
    public static final String a = "a";
    public static final String b = "b";
    public static final String c = "c";
    public static final String d = "d";
    public static final String e = "e";
    public static final String f = "f";
    public static final String g = "g";
    public static final String h = "h";
    public static final String i = "i";
    public static final String j = "j";
    public static final String k = "k";
    public static final String l = "l";
    public static final String m = "m";
    public static final String n = "n";
    public static final String o = "o";
    public static final String p = "p";
    public static final String q = "q";
    public static final String r = "r";
    public static final String s = "s";
    public static final String t = "t";
    public static final String u = "u";
    public static final String v = "v";
    public static final String w = "w";
    public static final String x = "x";
    public static final String y = "y";
    public static final String z = "z";

    /**
     * 数字
     */
    public static final int zero = 0;
    public static final int one = 1;
    public static final int two = 2;
    public static final int three = 3;
    public static final int four = 4;
    public static final int five = 5;
    public static final int six = 6;
    public static final int seven = 7;
    public static final int eight = 8;
    public static final int nine = 9;

    /**
     * 拼接字符串
     * 例如：str A ;num 4
     * 结果：AAAA
     * @param str 需要拼接的目标字符串
     * @param num 拼接数量
     * @return
     */
    public static String strJointByNum(String str, int num) {
        String result = "";
        for (int i = 0; i < num; i++) {
            result = result + str;
        }
        return result;
    }


}
