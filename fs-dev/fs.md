# 1 系统代码结构

```sh
# 项目名称
fs-dev
    # 网关服务(系统的统一入口)
    gateway
    # bff模块（与微服务进行交互，获取所需数据并整合返回给用户端）
    bff
        - bff-base # bff服务的基础依赖（被依赖使用，不能独立运行）
        - customer
        - mis
        - ...
    # 微服务模块（提供具体的服务，如用户服务、订单服务、文件服务等）
    service
        - service-base # 微服务的基础依赖（被依赖使用，不能独立运行）
        - cst
        - odr
        - pay
        - ...
    # 公共模块
    common
        - aspect
        - constant
        - enums
        - exception
        - pojo
        - result
        - util

    # 扩展模块（mybatis逆向工程、代码生成器等）
    eff
        - mybatis-generator
```