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

    List<ArticleModel> findAllByCriteria(DiscussionCriteria criteria);

    ArticleModel findDetailByCriteria(DiscussionCriteria criteria);

    List<ArticleCommentModel> findCommentsByCriteria(DiscussionCriteria criteria);

    Integer addComment(ArticleCommentModel model);

    Integer deleteComment(ArticleCommentModel model);

    Integer upgradeComment(ArticleCommentModel model);

    Integer upgradeDetail(ArticleModel model);

    Integer addArticle(ArticleModel model);

}
