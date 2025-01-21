package com.yz.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("t_response_error_enums")
public class TResponseErrorEnums extends BaseEntity implements Serializable {
    /**主键*/
    @TableId
    private Long id;

    /**编码code*/
    private String code;

    /**状态码*/
    private Integer status;

    /**msg描述*/
    private String msg;

    /**语言编码*/
    private String language;

    /**排序*/
    private Integer sort;

}