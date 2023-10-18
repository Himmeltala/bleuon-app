package com.bleuon.service.impl;

import com.bleuon.entity.PostModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import com.bleuon.mapper.DiscussionMapper;
import com.bleuon.service.IDiscussionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<PostModel> findAllByCriteria(DiscussionCriteria criteria) {
        return discussionMapper.findAllByCriteria(criteria);
    }

    @Override
    public PageInfo<PostModel> findAllByCriteriaNotComments(DiscussionCriteria criteria) {
        // 如果 page size 为空，返回 5 默认值
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(5);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> discussionMapper.findAllByCriteriaNotComments(criteria));
    }

}
