package org.example.enum_;

/**
 * @author wing
 * @create 2023/12/16
 */
public class Enumeration01 {
    public static void main(String[] args) {
        System.out.println(Season.SUMMER.getName());
        System.out.println(Season.SUMMER.getDesc());
    }
}

class Season{

    // 定义的固定四个对象
    public static final Season SPRING = new Season("春天","温暖");
    public static final Season WINTER = new Season("冬天","寒冷");
    public static final Season AUTUMN = new Season("秋天","凉爽");
    public static final Season SUMMER = new Season("夏天","炎热");

    private String name;
    private String desc;
    // 构造方法私有，不允许创建
    private Season(String name, String desc){
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

}
