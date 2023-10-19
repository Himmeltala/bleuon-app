/**
 * @description 社区 API
 * @author zheng
 * @since 2023/10/14
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "@common/apis/use-axios";

/**
 * 根据条件查询所有帖子
 *
 * @param model
 * @returns
 */
export async function findAllByCriteria(model: {
  consumerId?: string;
  postId?: string;
  title?: string;
  rankingType?: string;
  type?: string;
  pageSize?: number;
  currPage?: number;
  sequences: { isAsc: boolean; col: string }[];
}) {
  const { data } = await request.post<R<{ list: PostModel[]; total: number }>>(
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
  postId: string;
  consumerId?: string;
  title?: string;
  rankingType?: string;
  type?: string;
  pageSize?: number;
  currPage?: number;
  sequences: { isAsc: boolean; col: string }[];
}) {
  const { data } = await request.post<R<PostModel>>("/discussion/find/detail/by/criteria", model);
  return data.data;
}

/**
 * 更新帖子数据
 *
 * @param model
 */
export function upgradeDetail(model: PostModel, nomessage?: boolean, success?: Function) {
  request.put<R>("/discussion/upgrade/detail", model, { nomessage }).then(() => {
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
  postId: string;
  pageSize?: number;
  currPage?: number;
  sequences: { isAsc: boolean; col: string }[];
}) {
  const { data } = await request.post<R<{ list: PostCommentModel[]; total: number }>>(
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
  model: { postId: string; consumerId: string; content: string },
  success?: Function
) {
  request.post<R>("/discussion/add/comment", model).then(() => {
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
  model: { id: string; postId: string; consumerId: string },
  success?: Function
) {
  request.delete<R>("/discussion/delete/comment", { params: model }).then(() => {
    success && success();
  });
}

/**
 * 更新评论数据
 *
 * @param model
 * @param success
 */
export function upgradeComment(model: PostCommentModel, success?: Function) {
  request.put<R>("/discussion/upgrade/comment", model).then(() => {
    success && success();
  });
}
