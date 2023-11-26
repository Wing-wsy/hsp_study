package com.hspedu.test;

/**
 * @author wing
 * @create 2023/11/26
 */
public class Test_ {
    public static void main(String[] args) {


        BufferedReader_ bufferedReader_ = new BufferedReader_(new FileReader_());
//        bufferedReader_.read();
        bufferedReader_.readFile(10);
        //bufferedReader_.readFile();
        //Serializable
        //Externalizable
        //ObjectInputStream
        //ObjectOutputStream
        //这次希望通过 BufferedReader_ 多次读取字符串
        BufferedReader_ bufferedReader_2 = new BufferedReader_(new StringReader_());
        bufferedReader_2.readFile(5);
    }
}
