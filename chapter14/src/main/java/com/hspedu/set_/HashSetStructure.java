package com.hspedu.set_;

import org.junit.Test;

/**
 * @author wing
 * @create 2023/11/29
 * 模拟HashSet
 */
public class HashSetStructure {

    @Test
    public void getHashSetStr(){
        // 模拟一个HashSet的底层(其实就是HashMap)
        // 创建一个Node数组,有些人称为table
        Node[] table = new Node[16];
        //1.把john放在2的位置
        Node jhon = new Node("Jhon",null);
        table[2] = jhon;
        //2.将jack挂载到johj后边
        Node jack = new Node("Jack", null);
        jhon.next = jack;
        //3.继续把Rose挂载到Jack后面
        Node rose = new Node("Rose", null);
        jack.next = rose;
        //4.把Lucy放到table表索引为3的位置
        Node lucy = new Node("Lucy", null);
        table[3] = lucy;
        System.out.println("完成");
    }
}

/* 结点，存储数据，可以指向下一个结点 */
class Node{
    Object item;// 存放数据
    Node next; // 指向下一个结点
    public Node(Object item, Node next) {
        this.item = item;
        this.next = next;
    }
}
