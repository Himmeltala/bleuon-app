package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.PostCommentModel;
import com.bleuon.entity.PostModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import com.bleuon.service.impl.DiscussionService;
import com.bleuon.utils.http.R;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/14
 */
@Tag(name = "社区")
@RequestMappingPrefix("/discussion")
@RequiredArgsConstructor
public class DiscussionController implements Serializable {

    private final DiscussionService discussionService;

    @Operation(summary = "根据条件查询帖子列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:post-all')")
    @PostMapping("/find/all/by/criteria")
    public R<PageInfo<PostModel>> findAllByCriteria(@RequestBody @Validated DiscussionCriteria criteria) {
        PageInfo<PostModel> pages = discussionService.findAllByCriteria(criteria);
        return R.success(pages);
    }

    @Operation(summary = "根据条件查询帖子详情")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:post-detail')")
    @PostMapping("/find/detail/by/criteria")
    public R<PostModel> findDetailByCriteria(@RequestBody @Validated DiscussionCriteria criteria) {
        return R.success(discussionService.findDetailByCriteria(criteria));
    }

    @Operation(summary = "根据条件查询帖子评论")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:post-comments')")
    @PostMapping("/find/comments/by/criteria")
    public R<PageInfo<PostCommentModel>> findCommentsByCriteria(@RequestBody @Validated DiscussionCriteria criteria) {
        return R.success(discussionService.findCommentsByCriteria(criteria));
    }

    @Operation(summary = "增加一条帖子评论")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:post-comment')")
    @PostMapping("/add/comment")
    public R<Object> addComment(@RequestBody @Validated PostCommentModel model) {
        boolean added = discussionService.addComment(model);
        return added ? R.success("评论发表成功！") : R.error("评论发表失败！");
    }

    @Operation(summary = "删除一条帖子评论")
    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:delete:post-comment')")
    @DeleteMapping("/delete/comment")
    public R<Object> deleteComment(PostCommentModel model) {
        boolean removed = discussionService.deleteComment(model);
        return removed ? R.success("删除评论成功！") : R.error("删除评论失败！");
    }

    @Operation(summary = "更新帖子数据")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:upgrade:post-comment')")
    @PutMapping("/upgrade/comment")
    public R<Object> upgradeComment(@RequestBody @Validated PostCommentModel model) {
        boolean upgraded = discussionService.upgradeComment(model);
        return upgraded ? R.success("更新成功！") : R.error("更新失败！");
    }

}
