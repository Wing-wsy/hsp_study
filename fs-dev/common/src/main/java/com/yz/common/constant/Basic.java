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
    public static final int ZERO_INT = 0;
    public static final int ONE_INT = 1;
    public static final int TWO_INT = 2;
    public static final int THREE_INT = 3;
    public static final int FOUR_INT = 4;
    public static final int FIVE_INT = 5;
    public static final int SIX_INT = 6;
    public static final int SEVEN_INT = 7;
    public static final int EIGHT_INT = 8;
    public static final int NINE_INT = 9;

    /**
     * 数字
     */
    public static final long ZERO_LONG = 0L;
    public static final long ONE_LONG = 1L;
    public static final long TWO_LONG = 2L;
    public static final long THREE_LONG = 3L;
    public static final long FOUR_LONG = 4L;
    public static final long FIVE_LONG = 5L;
    public static final long SIX_LONG = 6L;
    public static final long SEVEN_LONG = 7L;
    public static final long EIGHT_LONG = 8L;
    public static final long NINE_LONG = 9L;

    /**
     * 数字字符串
     */
    public static final String ZERO_STR = "0";
    public static final String ONE_STR = "1";
    public static final String TWO_STR = "2";
    public static final String THREE_STR = "3";
    public static final String FOUR_STR = "4";
    public static final String FIVE_STR = "5";
    public static final String SIX_STR = "6";
    public static final String SEVEN_STR = "7";
    public static final String EIGHT_STR = "8";
    public static final String NINE_STR = "9";


    /**
     * 开关状态
     */
    public static final int ON = 1;
    public static final int OFF = 0;

    /**
     * 禁用状态
     */
    public static final int NORMAL = 1;
    public static final int DISABLE = 0;

    /**
     * 删除状态
     */
    public static final int DELETE = 1;
    public static final int VAILD = 0;


    /**
     * 菜单等级
     */
    public static final int MENU_PARENT = 1;
    public static final int MENU_SON = 2;



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
