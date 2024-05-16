package com.itheima.pattern.bridge;

/**
 * @version v1.0
 * @ClassName: Windows
 * @Description: 扩展抽象化角色(windows操作系统)
 * @Author: 黑马程序员
 */
public class Linux extends OpratingSystem {

    public Linux(VideoFile videoFile) {
        super(videoFile);
    }

    public void play(String fileName) {
        System.out.println("Linux");
        videoFile.decode(fileName);
    }
}
