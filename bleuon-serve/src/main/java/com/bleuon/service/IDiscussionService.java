package com.bleuon.service;

import com.bleuon.entity.PostCommentModel;
import com.bleuon.entity.PostModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import com.github.pagehelper.PageInfo;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/14
 */
public interface IDiscussionService {

    PageInfo<PostModel> findAllByCriteria(DiscussionCriteria criteria);

    PostModel findDetailByCriteria(DiscussionCriteria criteria);

    PageInfo<PostCommentModel> findCommentsByCriteria(DiscussionCriteria criteria);

    boolean addComment(PostCommentModel model);

    boolean deleteComment(PostCommentModel model);

    boolean upgradeComment(PostCommentModel model);

}
