/**
 * @description 社区 API
 * @author zheng
 * @since 2023/10/14
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "./use-axios";

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
}) {
  const { data } = await request.post<R<PostModel[]>>("/discussion/find/all/by/criteria", model);
  return data.data;
}

/**
 * 根据条件查询所有帖子，但是不查询所属评论列表
 *
 * @param model
 * @returns
 */
export async function findAllByCriteriaNotComments(model: {
  consumerId?: string;
  postId?: string;
  title?: string;
}) {
  const { data } = await request.post<R<PostModel[]>>(
    "/discussion/find/all/by/criteria/not-comments",
    model
  );
  return data.data;
}
