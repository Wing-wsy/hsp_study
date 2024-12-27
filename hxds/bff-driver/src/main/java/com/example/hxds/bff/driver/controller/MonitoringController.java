package com.example.hxds.bff.driver.controller;

import com.example.hxds.bff.driver.feign.NebulaServiceApi;
import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.common.util.R;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/monitoring")
@Tag(name = "MonitoringController", description = "订单监控服务的Web接口")
@Slf4j
public class MonitoringController {
    @Resource
    private NebulaServiceApi nebulaServiceApi;

    /**
     * APP端使用同声传译插件，代驾过程中会记录语音，并且提取到语音里的中文对话文本，前端就能拿到两种数据，1录音mp3文件 2对话text文本
     * 最后音频文件保存在 Minio 中，对话文本内容保存在 HBase 数据库中
     * @param file
     * @param name
     * @param text
     * @return
     */
    @PostMapping(value = "/uploadRecordFile")
    public R uploadRecordFile(@RequestPart("file") MultipartFile file,
                              @RequestPart("name") String name,
                              @RequestPart(value = "text", required = false) String text) {
        if (file.isEmpty()) {
            throw new HxdsException("上传文件不能为空");
        }
        nebulaServiceApi.uploadRecordFile(file, name, text);

        return R.ok();
    }
}
