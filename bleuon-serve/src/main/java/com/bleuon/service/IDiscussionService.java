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

    PageInfo<ArticleModel> findAllArticleBy(DiscussionCriteria criteria);

    ArticleModel findArticleBy(DiscussionCriteria criteria);

    PageInfo<ArticleCommentModel> findArticleCommentListBy(DiscussionCriteria criteria);

    boolean addArticleComment(ArticleCommentModel model);

    boolean deleteArticleComment(ArticleCommentModel model);

    boolean upgradeArticleComment(ArticleCommentModel model);

    int upgradeArticle(ArticleModel model, DiscussionCriteria criteria);

    boolean addArticle(ArticleModel model);

}
