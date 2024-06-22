package org.example;

/**
 * @author wing
 * @create 2024/5/22
 */// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Person person = new Person();
        if (person instanceof Person) {
            System.out.println("person类型");
        }
        Teacher teacher = new Teacher();
        if (teacher instanceof Person) {
            System.out.println("Teacher也属于person类型");
        }

    }
}