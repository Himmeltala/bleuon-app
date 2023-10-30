/**
 * @description 社区 API
 * @author zheng
 * @since 2023/10/14
 */

import { http } from "@common/requests/use-axios";

/**
 * 根据条件查询所有帖子
 *
 * @param model
 * @returns
 */
export async function findAllByCriteria(model: {
  consumerId?: string;
  articleId?: string;
  title?: string;
  rankingType?: string;
  type?: string;
  pageSize?: number;
  currPage?: number;
  sequences: { isAsc: boolean; col: string }[];
}) {
  const { data } = await http.post<R<PageInfo<ArticleModel>>>(
    "/discussion/find/all/by/criteria",
    model
  );
  return data.data;
}

/**
 * 根据条件查询帖子详细
 *
 * @param model
 */
export async function findDetailByCriteria(model: {
  articleId: string;
  consumerId?: string;
  title?: string;
  rankingType?: string;
  type?: string;
}) {
  const { data } = await http.post<R<ArticleModel>>("/discussion/find/detail/by/criteria", model);
  return data.data;
}

/**
 * 更新帖子数据
 *
 * @param model
 */
export function upgradeArticle(
  req: ReqConfig<
    ArticleModel,
    Criteria<Partial<{ isBury: boolean; isDigg: boolean; isViews: boolean }>>
  >,
  success?: Function
) {
  http.put<R>("/discussion/upgrade/article", req.model, req.config).then(() => {
    success && success();
  });
}

/**
 * 新增一个帖子
 *
 * @param model
 * @param success
 */
export function addArticle(model: ArticleModel, success?: Function) {
  http.post<R>("/discussion/add/article", model).then(() => {
    success && success();
  });
}

/**
 * 条件查询评论
 *
 * @param model
 * @returns
 */
export async function findCommentsByCriteria(model: {
  articleId: string;
  pageSize?: number;
  currPage?: number;
  sequences: { isAsc: boolean; col: string }[];
}) {
  const { data } = await http.post<R<PageInfo<ArticleCommentModel>>>(
    "/discussion/find/comments/by/criteria",
    model
  );
  return data.data;
}

/**
 * 发表评论
 *
 * @param model
 * @param success
 */
export function addComment(
  model: { articleId: string; consumerId: string; content: string },
  success?: Function
) {
  http.post<R>("/discussion/add/comment", model).then(() => {
    success && success();
  });
}

/**
 * 删除评论
 *
 * @param model
 * @param success
 */
export function deleteComment(
  model: { id: string; articleId: string; consumerId: string },
  success?: Function
) {
  http.delete<R>("/discussion/delete/comment", { params: model }).then(() => {
    success && success();
  });
}

/**
 * 更新评论数据
 *
 * @param model
 * @param success
 */
export function upgradeComment(model: ArticleCommentModel, success?: Function) {
  http.put<R>("/discussion/upgrade/comment", model).then(() => {
    success && success();
  });
}
