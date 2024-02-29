package org.example;

/**
 * @author wing
 * @create 2024/2/23
 */
public class TestClass {
    public static void main(String[] args) {
        System.out.println("Hello World");
        int aa = TestClass.getAA(1);
        int wing = TestClass.getAA("Wing");
        System.out.println(aa);
        System.out.println(wing);

    }

    final static int nationNum = 56;
    static public String country = "China";
    public String name;
    private int age;

    static public int getStaticNum(){
        int a = 10;
        int b = 20;
        int c = a + b;
        {
            int str = 8;
            int d = 1;
        }
        int e = 2;
        return c;
    }

    public int inc() {
        int x;
        try {
            x = 1;
            return x;
        } catch(Exception e) {
            x = 2;
            return x;
        } finally {
            x = 3;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    static public int getAA(String a){
        return 1;
    }
    static public int getAA(int a){
        return 2;
    }
}
