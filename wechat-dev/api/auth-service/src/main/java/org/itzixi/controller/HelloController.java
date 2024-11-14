package org.itzixi.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("a")
//@Slf4j
public class HelloController {

    // 127.0.0.1:88/a/hello

//    @Resource
//    private SMSUtils smsUtils;
//
//    @Resource
//    private SMSTask smsTask;

    @GetMapping("hello")
    public Object hello() {
        return "Hello world~ auth-service";
    }

//    @GetMapping("sms")
//    public Object sms() throws Exception {
//
//        smsUtils.sendSMS(MyInfo.getMobile(), "9875");
//
//        return "Send SMS OK~";
//    }
//
//    @GetMapping("smsTask")
//    public Object smsTask() throws Exception {
//
//        smsTask.sendSMSInTask(MyInfo.getMobile(), "8111");
//
//        return "Send SMS In Task OK~";
//    }

}
