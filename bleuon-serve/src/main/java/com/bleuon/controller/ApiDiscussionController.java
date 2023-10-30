package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.ArticleCommentModel;
import com.bleuon.entity.ArticleModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import com.bleuon.service.IDiscussionService;
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
 * @description: API 控制器
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/14
 */
@Tag(name = "社区")
@RequestMappingPrefix("/discussion")
@RequiredArgsConstructor
public class ApiDiscussionController implements Serializable {

    private final IDiscussionService discussionService;

    @Operation(summary = "根据条件查询帖子列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:article:AllArticleBy')")
    @PostMapping("/find/all/by/criteria")
    public R<PageInfo<ArticleModel>> findAllArticleBy(@RequestBody @Validated DiscussionCriteria criteria) {
        PageInfo<ArticleModel> pages = discussionService.findAllArticleBy(criteria);
        return R.success(pages);
    }

    @Operation(summary = "根据条件查询帖子详情")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:article:detail')")
    @PostMapping("/find/detail/by/criteria")
    public R<ArticleModel> findArticleBy(@RequestBody @Validated DiscussionCriteria criteria) {
        return R.success(discussionService.findArticleBy(criteria));
    }

    @Operation(summary = "更新帖子数据")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:upgrade:article')")
    @PutMapping("/upgrade/article")
    public R<Object> upgradeArticle(@RequestBody @Validated ArticleModel model, DiscussionCriteria criteria) {
        int status = discussionService.upgradeArticle(model, criteria);
        if (status == -1) return R.error("帖子不存在！");
        else if (status == 0) return R.error("更新帖子数据失败！");
        else return R.success("更新成功！");
    }

    @Operation(summary = "根据条件查询帖子评论")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:article:ArticleCommentListBy')")
    @PostMapping("/find/comments/by/criteria")
    public R<PageInfo<ArticleCommentModel>> findArticleCommentListBy(@RequestBody @Validated DiscussionCriteria criteria) {
        return R.success(discussionService.findArticleCommentListBy(criteria));
    }

    @Operation(summary = "增加一条帖子评论")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:article:comment')")
    @PostMapping("/add/comment")
    public R<Object> addArticleComment(@RequestBody @Validated ArticleCommentModel model) {
        boolean added = discussionService.addArticleComment(model);
        return added ? R.success("评论发表成功！") : R.error("评论发表失败！");
    }

    @Operation(summary = "删除一条帖子评论")
    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:delete:article:comment')")
    @DeleteMapping("/delete/comment")
    public R<Object> deleteArticleComment(ArticleCommentModel model) {
        boolean removed = discussionService.deleteArticleComment(model);
        return removed ? R.success("删除评论成功！") : R.error("删除评论失败！");
    }

    @Operation(summary = "更新一条帖子评论数据")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:upgrade:article:comment')")
    @PutMapping("/upgrade/comment")
    public R<Object> upgradeArticleComment(@RequestBody @Validated ArticleCommentModel model) {
        boolean upgraded = discussionService.upgradeArticleComment(model);
        return upgraded ? R.success("更新成功！") : R.error("更新失败！");
    }

    @Operation(summary = "创建一条帖子")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:article')")
    @PostMapping("/add/article")
    public R<Object> addArticle(@RequestBody @Validated ArticleModel model) {
        boolean added = discussionService.addArticle(model);
        return added ? R.success("创建成功！") : R.error("创建失败！");
    }

}
