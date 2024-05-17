package com.itheima.pattern.memento.black_box_new;

/**
 * @author wing
 * @create 2024/5/17
 * 源发器（Originator）：文本编辑器
 * 定义：需要保存和恢复状态的对象。它创建一个备忘录对象，用于存储当前对象的状态，也可以使用备忘录对象恢复自身的状态。
 */
public class Editor {
    //内容（可以是属性、也可以是对象）
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    // 创建备忘录对象，保存当前状态
    public Memento createMemento() {
        return new EditorMemento(content);
    }

    // 恢复备忘录对象保存的状态
    public void restoreMemento(EditorMemento memento) {
        content = memento.getContent();
    }

}
