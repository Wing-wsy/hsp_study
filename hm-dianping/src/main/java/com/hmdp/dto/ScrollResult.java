package com.hmdp.dto;

import lombok.Data;

import java.util.List;

@Data
// 这里命名为 ScrollDTO 好点
public class ScrollResult {
    private List<?> list;
    private Long minTime;
    private Integer offset;
}
