package com.bleuon.service.impl;

import com.bleuon.entity.PostModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import com.bleuon.exception.JdbcFailedException;
import com.bleuon.mapper.DiscussionMapper;
import com.bleuon.service.IDiscussionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        try {

            if (criteria.getPageSize() == null) {
                criteria.setPageSize(5);
            }

            if (criteria.getCurrPage() == null) {
                criteria.setCurrPage(1);
            }

            PageHelper.startPage(criteria.getCurrPage(), criteria.getPageSize());
            List<PostModel> result = discussionMapper.findAllByCriteriaNotComments(criteria);
            return new PageInfo<>(result, criteria.getPageSize());
        } catch (Exception e) {
            throw new JdbcFailedException(e.getCause());
        } finally {
            PageHelper.clearPage();
        }
    }

}
