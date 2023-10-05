package com.bleuon.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @description:
 * @package: com.bleuon.entity.dto
 * @author: zheng
 * @date: 2023/10/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectFlowchartDto {

    private String id;
    private String fileName;
    private String json;
    private String dataUri;
    private Double width;
    private Double height;
    private String bgColor;
    private Double gridSize;
    private String connectorDefault;
    private String routerDefault;
    private Integer isPublic;
    private Integer isLegal;
    private Integer isShare;
    private Timestamp createDate;
    private Timestamp modifyDate;
    private Timestamp deadShareDate;
    private User belongUser;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String id;
        private String username;
        private String avatar;
    }
}