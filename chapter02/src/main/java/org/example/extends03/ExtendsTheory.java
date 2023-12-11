package org.example.extends03;

/**
 * @author wing
 * @create 2023/12/10
 */
public class ExtendsTheory {
    public static void main(String[] args) {
        Person[] p = new Person[5];
        p[0] = new Person("li",18);
        p[1] = new Student("qin",19,80);
        p[2] = new Student("liu",20,90);
        p[3] = new Teacher("wang",21,10000);
        p[4] = new Teacher("yang",22,20000);

        for (Person person : p) {
            System.out.println(person.say());
            if(person instanceof Student){
                System.out.println(((Student) person).getScore());
            }
            if(person instanceof Teacher){
                System.out.println(((Teacher) person).getSalary());
            }
        }
    }
}


class Person{
    String name;
    int age;
    Person(String name,int age){
        this.name = name;
        this.age = age;
    }
    public String say(){
        return name + "," + age;
    }
}

class Student extends Person{
    double score;
    Student(String name, int age, double score) {
        super(name, age);
        this.score = score;
    }
    public String say(){
        return "学生：" + super.say() + "," + score;
    }

    // 学生特有方法
    public String getScore(){
        return "学生成绩：" + score;
    }
}
class Teacher extends Person{
    double salary;
    Teacher(String name, int age, double salary) {
        super(name, age);
        this.salary = salary;
    }
    public String say(){
        return "老师：" + super.say() + "," + salary;
    }

    // 老师特有方法
    public String getSalary(){
        return "老师薪水：" + salary;
    }
}

//class Base{
//    int count = 10;
//    public void display(){
//        System.out.println(this.count);
//    }
//}
//class Sub extends Base{
//    int count = 20;
//    public void display(){
//        System.out.println(this.count);
//    }
//}


//class Animal {
//    public void cry(){
//        System.out.println("动物在 叫唤");
//    }
//}
//class Cat extends Animal {
//    public void cry() {
//        System.out.println("猫在 叫唤");
//    }
//    // 猫特有的方法
//    public void catchMouse(){
//        System.out.println("猫捉老鼠");
//    }
//}
//class Dog extends Animal {
//    public void cry(){
//        System.out.println("狗在 叫唤");
//    }
//}