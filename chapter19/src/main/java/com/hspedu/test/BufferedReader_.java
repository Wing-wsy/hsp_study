package com.hspedu.test;

/**
 * @author wing
 * @create 2023/11/26
 * 做成处理流/包装流
 */
public class BufferedReader_ extends Reader_ {

    private Reader_ reader_; //属性是 Reader_类型

    //接收Reader_ 子类对象
    public BufferedReader_(Reader_ reader_) {
        this.reader_ = reader_;
    }

    public void readFile(int num) { //封装一层
        for (int i = 0; i < num; i++) {
            reader_.read();
        }
    }

    @Override
    public void read() {
        reader_.read();
    }
}
