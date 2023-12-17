package org.example.enum_;

/**
 * @author wing
 * @create 2023/12/16
 */
@Deprecated
public class Enumeration02 {
    public static void main(String[] args) {
//        Week[] weeks = Week.values();
//        for (Week week : weeks) {
//            System.out.println(week);
//        }

        System.out.println(Week.MONDAY);
    }
}

/**
 * 声明week权举类，基中包含星期一至星期日
 * MONDAY, TUESDAY WEDNESDAY, THURSDAY. FRIDAY, SATURDAY, SUNDAY:
 * 使用values 返回所有的枚举数组，井遍历输出 星期一～星期日
 *
 * */
enum Week{
    MONDAY("星期一"),
    TUESDAY("星期二"),
    WEDNESDAY("星期三"),
    THURSDAY("星期四"),
    FRIDAY("星期五"),
    SATURDAY("星期六"),
    SUNDAY("星期日");
    private String name;
    private Week(String name){
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
}