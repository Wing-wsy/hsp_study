package top.jacktgq.config;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * 自定义的一个读取配置类
 */
//@Component
@ConfigurationProperties(prefix = "server-config")
@Data
// 2.开启对当前bean的属性注入校验
@Validated
public class ServerConfig {
    private String ipAddress;
    // 设置具体的规则
    @Max(value = 8888, message = "最大值不能超过8888")
    @Min(value = 1000, message = "最小值不能低于1000")
    private int port;
    @DurationUnit(ChronoUnit.MINUTES)
    private Duration timeout;

    @DataSizeUnit(DataUnit.MEGABYTES)
    private DataSize dataSize;
}
