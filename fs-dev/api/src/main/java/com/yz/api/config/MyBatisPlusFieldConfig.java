package com.yz.api.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yz.common.constant.FieldConstants;
import com.yz.common.enums.YesOrNo;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * 通用字段填充
 */
@Component
public class MyBatisPlusFieldConfig implements MetaObjectHandler {
    /**
     * 使用mp做添加操作时候，这个方法执行
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime localDateTime = LocalDateTime.now();
        //设置属性值
        this.setFieldValByName(FieldConstants.CREATE_TIME,localDateTime,metaObject);
        this.setFieldValByName(FieldConstants.UPDATE_TIME,localDateTime,metaObject);
        // 0:正常，1已删除
        this.setFieldValByName(FieldConstants.IS_DELETE, YesOrNo.NO.type,metaObject);

    }

    /**
     * 使用mp做修改操作时候，这个方法执行
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(FieldConstants.UPDATE_TIME,LocalDateTime.now(),metaObject);

    }
}
