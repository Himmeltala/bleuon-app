package com.bleuon.mapper;

import com.bleuon.entity.PostCommentModel;
import com.bleuon.entity.PostModel;
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

    List<PostModel> findAllByCriteria(DiscussionCriteria criteria);

    PostModel findDetailByCriteria(DiscussionCriteria criteria);

    List<PostCommentModel> findCommentsByCriteria(DiscussionCriteria criteria);

    Integer addComment(PostCommentModel model);

    Integer deleteComment(PostCommentModel model);

    Integer upgradeComment(PostCommentModel model);

}
