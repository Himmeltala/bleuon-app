package com.bleuon.service;

import com.bleuon.entity.PostModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/14
 */
public interface IDiscussionService {

    List<PostModel> findAllByCriteria(DiscussionCriteria criteria);

    PageInfo<PostModel> findAllByCriteriaNotComments(DiscussionCriteria criteria);

}
