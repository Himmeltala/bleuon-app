package com.bleuon.mapper;

import com.bleuon.entity.ArticleCommentModel;
import com.bleuon.entity.ArticleModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/10/14
 */
@Mapper
public interface DiscussionMapper {

    List<ArticleModel> findAllArticleBy(DiscussionCriteria criteria);

    ArticleModel findArticleBy(DiscussionCriteria criteria);

    List<ArticleCommentModel> findArticleCommentListBy(DiscussionCriteria criteria);

    Integer addArticleComment(ArticleCommentModel model);

    Integer deleteArticleComment(ArticleCommentModel model);

    Integer upgradeArticleComment(ArticleCommentModel model);

    Integer upgradeArticle(ArticleModel model);

    Integer addArticle(ArticleModel model);

}
