package com.yz.cst.controller;

import cn.hutool.core.bean.BeanUtil;
import com.yz.api.controller.cst.TUserControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.cst.service.TUserService;
import com.yz.model.bo.cst.SearchUserBriefInfoBO;
import com.yz.model.dto.cst.UserBriefInfoDTO;
import com.yz.model.vo.cst.UserBriefInfoVO;
import com.yz.service.base.controller.BaseController;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制层2
 */
@Slf4j
@RestController
public class TUserController extends BaseController implements TUserControllerApi {

    @Resource
    private TUserService tUserService;

    public GraceResult searchUserBriefInfo(@RequestBody @Valid SearchUserBriefInfoBO bo){
        UserBriefInfoDTO userBriefInfoDTO = tUserService.searchCustomerBriefInfo(bo.getUserId());
        UserBriefInfoVO vo = BeanUtil.toBean(userBriefInfoDTO, UserBriefInfoVO.class);
        return GraceResult.ok(vo);
    }

}
