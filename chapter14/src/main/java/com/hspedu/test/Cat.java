package com.hspedu.test;

/**
 * @author wing
 * @create 2023/12/24
 */
public class Cat {

    public static void main(String[] args) {
        cry();
    }

    public static void cry(){
        System.out.println(new SpecialInfo("跑步",12, "wing").toString());
//        System.out.println(new BasicInfo(12, "wing").toString());
    }

    static class SpecialInfo extends Animal.BasicInfo{
        String hobby;
        SpecialInfo(String hobby, int age, String name) {
            super(age, name);
            this.hobby = hobby;
        }

        @Override
        public String toString() {
            return "SpecialInfo{" +
                    "hobby='" + hobby + '\'' +
                    ", age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


}
