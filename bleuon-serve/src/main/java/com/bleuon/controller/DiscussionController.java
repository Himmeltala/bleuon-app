package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.PostCommentModel;
import com.bleuon.entity.PostModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import com.bleuon.entity.dto.PostDTO;
import com.bleuon.service.impl.DiscussionService;
import com.bleuon.utils.http.R;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Operation(summary = "根据条件查询帖子列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:post-all')")
    @PostMapping("/find/all/by/criteria")
    public R<PageInfo<PostModel>> findAllByCriteria(@RequestBody DiscussionCriteria criteria) {
        PageInfo<PostModel> pages = discussionService.findAllByCriteria(criteria);
        return R.success(pages);
    }

    @Operation(summary = "根据条件查询帖子详情")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:post-detail')")
    @PostMapping("/find/detail/by/criteria")
    public R<PostDTO> findDetailByCriteria(@RequestBody DiscussionCriteria criteria) {
        return R.success(discussionService.findDetailByCriteria(criteria));
    }

    @Operation(summary = "根据条件查询帖子评论")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:post-comments')")
    @PostMapping("find/comments/by/criteria")
    public R<PageInfo<PostCommentModel>> findCommentsByCriteria(@RequestBody DiscussionCriteria criteria) {
        return R.success(discussionService.findCommentsByCriteria(criteria));
    }

}
