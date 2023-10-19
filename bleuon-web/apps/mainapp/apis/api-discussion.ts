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

export async function uploadImage() {}
