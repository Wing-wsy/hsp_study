package com.yz.model.res.mis_bff;

import com.yz.model.vo.cst.SearchUserBriefInfoVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "查询用户基础信息Res")
public class SearchUserBriefInfoRes {

    @Schema(description = "用户名称")
    private SearchUserBriefInfoVO searchUserBriefInfoVO;

}
