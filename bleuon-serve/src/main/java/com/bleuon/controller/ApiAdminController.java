package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @description: API 控制器
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/22
 */
@Tag(name = "管理员")
@RequiredArgsConstructor
@RequestMappingPrefix("/admin")
public class ApiAdminController implements Serializable {


}
