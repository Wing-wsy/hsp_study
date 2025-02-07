package com.yz.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通用实体
 */
@Data
public class BaseEntity {

    /**创建日期*/
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**更新日期*/
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**是否删除*/
    @TableField(value = "is_delete",fill = FieldFill.INSERT)
    @TableLogic // 逻辑删除字段 查询语句自动添加 is_delete = 0
    private Integer isDelete;
}
