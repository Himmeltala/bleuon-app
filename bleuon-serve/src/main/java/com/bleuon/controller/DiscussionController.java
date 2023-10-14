package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.PostModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import com.bleuon.service.impl.DiscussionService;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/14
 */
@Tag(name = "社区")
@RequestMappingPrefix("/discussion")
@RequiredArgsConstructor
public class DiscussionController {

    private final DiscussionService discussionService;

    @Operation(summary = "根据条件查询所有帖子")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @PostMapping("/find/all/by/criteria")
    public R<List<PostModel>> findAllByCriteria(@RequestBody DiscussionCriteria criteria) {
        List<PostModel> list = discussionService.findAllByCriteria(criteria);
        return R.success(list);
    }

    @Operation(summary = "根据条件查询所有帖子，但不查询所属评论列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @PostMapping("/find/all/by/criteria/not-comments")
    public R<List<PostModel>> findAllByCriteriaNotComments(@RequestBody DiscussionCriteria criteria) {
        List<PostModel> list = discussionService.findAllByCriteriaNotComments(criteria);
        return R.success(list);
    }

}
