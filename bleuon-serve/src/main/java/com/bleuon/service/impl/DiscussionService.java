package com.bleuon.service.impl;

import com.bleuon.entity.PostCommentModel;
import com.bleuon.entity.PostModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.DiscussionMapper;
import com.bleuon.service.IDiscussionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

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
    public PostModel findDetailByCriteria(DiscussionCriteria criteria) {
        return discussionMapper.findDetailByCriteria(criteria);
    }

    @Override
    public PageInfo<PostCommentModel> findCommentsByCriteria(DiscussionCriteria criteria) {
        // 如果 page size 为空，返回 5 默认值
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> discussionMapper.findCommentsByCriteria(criteria));
    }

    @Transactional
    @Override
    public boolean addComment(PostCommentModel model) {
        try {
            String uuid = UUID.randomUUID().toString();
            model.setId(uuid);
            Integer row = discussionMapper.addComment(model);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean deleteComment(PostCommentModel model) {
        try {
            Integer row = discussionMapper.deleteComment(model);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean upgradeComment(PostCommentModel model) {
        try {
            Integer row = discussionMapper.upgradeComment(model);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

}
