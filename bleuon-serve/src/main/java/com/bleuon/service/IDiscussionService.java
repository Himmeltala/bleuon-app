package com.bleuon.service;

import com.bleuon.entity.ArticleCommentModel;
import com.bleuon.entity.ArticleModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import com.github.pagehelper.PageInfo;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/14
 */
public interface IDiscussionService {

    PageInfo<ArticleModel> findAllByCriteria(DiscussionCriteria criteria);

    ArticleModel findDetailByCriteria(DiscussionCriteria criteria);

    PageInfo<ArticleCommentModel> findCommentsByCriteria(DiscussionCriteria criteria);

    boolean addComment(ArticleCommentModel model);

    boolean deleteComment(ArticleCommentModel model);

    boolean upgradeComment(ArticleCommentModel model);

    boolean upgradeDetail(ArticleModel model);

    boolean addArticle(ArticleModel model);

}
