package com.yz.model.res.mis_bff.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
@Schema(description = "查询菜单树Res")
public class MenuTreeRes {

    @Schema(description = "菜单树")
    private ArrayList<HashMap> map;
}
