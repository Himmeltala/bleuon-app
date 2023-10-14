package com.bleuon.service.impl;

import com.bleuon.entity.PostModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import com.bleuon.mapper.DiscussionMapper;
import com.bleuon.service.IDiscussionService;
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
    public List<PostModel> findAllByCriteriaNotComments(DiscussionCriteria criteria) {
        return discussionMapper.findAllByCriteriaNotComments(criteria);
    }

}
