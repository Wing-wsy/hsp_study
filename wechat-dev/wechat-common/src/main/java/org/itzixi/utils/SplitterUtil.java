package org.itzixi.utils;

import com.google.common.base.Splitter;
import org.itzixi.constant.basic.Strings;

import java.util.List;

/**
 * @author wing
 * @create 2024/12/5
 */
public class SplitterUtil {

    /**
     * 按指定分隔符分割
     * @param separator 分隔符
     * @return
     */
    public static Splitter getSplitter(String separator) {
        Splitter splitter = Splitter.on(separator);
        return splitter;
    }

    public static void main(String[] args) {
        Splitter splitter = getSplitter(Strings.COMMA);
        List<String> strings = splitter.splitToList("1,2,3,a,b");
        for (String string : strings) {
            System.out.println(string);
        }
    }
}
