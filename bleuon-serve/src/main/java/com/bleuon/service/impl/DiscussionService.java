package com.bleuon.service.impl;

import com.bleuon.entity.ArticleCommentModel;
import com.bleuon.entity.ArticleModel;
import com.bleuon.entity.criterias.DiscussionCriteria;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.DiscussionMapper;
import com.bleuon.service.IDiscussionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/14
 */
@Service
public class DiscussionService implements IDiscussionService {

    private final DiscussionMapper discussionMapper;
    private final TransactionalWrapper transactionalWrapper;

    public DiscussionService(DiscussionMapper discussionMapper) {
        this.discussionMapper = discussionMapper;
        transactionalWrapper = new TransactionalWrapper(discussionMapper);
    }

    @Override
    public PageInfo<ArticleModel> findAllArticleBy(DiscussionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(5);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> discussionMapper.findAllArticleBy(criteria));
    }

    @Override
    public ArticleModel findArticleBy(DiscussionCriteria criteria) {
        return discussionMapper.findArticleBy(criteria);
    }

    @Override
    public PageInfo<ArticleCommentModel> findArticleCommentListBy(DiscussionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> discussionMapper.findArticleCommentListBy(criteria));
    }

    @Transactional
    @Override
    public boolean addArticleComment(ArticleCommentModel model) {
        try {
            String uuid = UUID.randomUUID().toString();
            model.setId(uuid);
            Integer row = discussionMapper.addArticleComment(model);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean deleteArticleComment(ArticleCommentModel model) {
        try {
            Integer row = discussionMapper.deleteArticleComment(model);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean upgradeArticleComment(ArticleCommentModel model) {
        try {
            Integer row = discussionMapper.upgradeArticleComment(model);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Override
    public synchronized int upgradeArticle(ArticleModel model, DiscussionCriteria criteria) {
        return transactionalWrapper.doUpgradeArticle(model, criteria);
    }

    @Transactional
    @Override
    public boolean addArticle(ArticleModel model) {
        try {
            String uuid = UUID.randomUUID().toString();
            model.setId(uuid);
            Integer row = discussionMapper.addArticle(model);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

}

@AllArgsConstructor
class TransactionalWrapper {

    private DiscussionMapper discussionMapper;

    @Transactional
    public int doUpgradeArticle(ArticleModel model, DiscussionCriteria criteria) {
        try {
            if (criteria != null) {
                criteria.setArticleId(model.getId());
                ArticleModel article = discussionMapper.findArticleBy(criteria);

                if (article == null) {
                    return -1;
                }

                if (criteria.getIsDigg() != null && criteria.getIsDigg()) {
                    model.setDigg(article.getDigg() + 1);
                }

                if (criteria.getIsBury() != null && criteria.getIsBury()) {
                    model.setBury(article.getBury() + 1);
                }

                if (criteria.getIsViews() != null && criteria.getIsViews()) {
                    model.setViews(article.getViews() + 1);
                }
            }

            Integer affectRows = discussionMapper.upgradeArticle(model);
            return affectRows > 0 ? 1 : 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

}