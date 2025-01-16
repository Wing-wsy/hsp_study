package com.yz.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_order")
public class TOrder extends BaseEntity implements Serializable {
    /**主键*/
    @TableId
    private Long orderId;

    /**客户ID*/
    private Long userId;

    /**1等待机审，2审核通过待放款，3已放款，4放款失败，5待还款，6已销账，7已关闭，10其他*/
    private Integer status;

    /**到手金额*/
    private BigDecimal actualAmounts;

    /**应还金额*/
    private BigDecimal sureRepayAmounts;

    /**实还金额*/
    private BigDecimal realRepayAmounts;

    /**到期日期*/
    private LocalDateTime repayDate;

    /**销账日期*/
    private LocalDateTime sellDate;

}