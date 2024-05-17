package com.itheima.pattern.memento.black_box_new;

/**
 * @author wing
 * @create 2024/5/17
 */
public class Client {
    public static void main(String[] args) {
        //创建文本编辑器（源发器）
        Editor editor = new Editor();
        //创建管理者
        History history = new History();
        // 编辑文本并保存状态
        editor.setContent("Hello");
        history.push(editor.createMemento());

        // 编辑更多文本并再次保存状态
        editor.setContent("Hello, Java!");
        history.push(editor.createMemento());

        editor.setContent("Hello，world!");
        System.out.println("当前内容: " + editor.getContent());

        // 恢复之前的状态
        editor.restoreMemento((EditorMemento)history.pop());
        System.out.println("恢复后上一次内容: " + editor.getContent());

        editor.restoreMemento((EditorMemento)history.pop());
        System.out.println("恢复后上二次内容: " + editor.getContent());
    }
}
