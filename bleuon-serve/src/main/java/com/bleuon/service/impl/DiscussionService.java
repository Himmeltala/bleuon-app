package com.bleuon.service.impl;

import com.bleuon.entity.PostCommentModel;
import com.bleuon.entity.PostModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import com.bleuon.entity.dto.PostDTO;
import com.bleuon.mapper.DiscussionMapper;
import com.bleuon.service.IDiscussionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/14
 */
@Service
@RequiredArgsConstructor
public class DiscussionService implements IDiscussionService {

    private final DiscussionMapper discussionMapper;

    @Override
    public PageInfo<PostModel> findAllByCriteria(DiscussionCriteria criteria) {
        // 如果 page size 为空，返回 5 默认值
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(5);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> discussionMapper.findAllByCriteria(criteria));
    }

    @Override
    public PostDTO findDetailByCriteria(DiscussionCriteria criteria) {
        PostModel postModel = discussionMapper.findDetailByCriteria(criteria);
        PageInfo<PostCommentModel> comments = findCommentsByCriteria(criteria);
        System.out.println(postModel);
        System.out.println(comments);
        return new PostDTO(postModel, comments);
    }

    @Override
    public PageInfo<PostCommentModel> findCommentsByCriteria(DiscussionCriteria criteria) {
        // 如果 page size 为空，返回 5 默认值
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> discussionMapper.findCommentsByCriteria(criteria));
    }

}
