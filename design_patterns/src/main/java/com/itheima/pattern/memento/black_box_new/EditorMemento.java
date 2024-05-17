package com.itheima.pattern.memento.black_box_new;

/**
 * @author wing
 * @create 2024/5/17
 */
public class EditorMemento implements Memento{
    //文本内容 （可以是属性、也可以是对象）
    private String content;
    public EditorMemento(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

}
