package top.jacktgq.config.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 在服务监控平台展示自定义服务描述信息
 */
@Component
public class InfoConfig implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("runTime", System.currentTimeMillis());
        Map infoMap = new HashMap();
        infoMap.put("buildTime", "2006");
        infoMap.put("systemInfo", "这是xxx服务");
        builder.withDetails(infoMap);
    }
}
