/**
 * @description 文件 API
 * @author zheng
 * @since 2023/10/12
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "@common/apis/use-axios";

/**
 * 上传图片
 *
 * @param formData
 * @returns
 */
export function uploadImageFile(formData: FormData) {
  return request.post<R>("/public/file/upload/image", formData);
}

/**
 * 删除图片
 *
 * @param str
 */
export function deleteImageFile(str: string, success?: Function) {
  const params = new URL(str).searchParams;
  request
    .delete<R>("/public/file/delete/image", {
      params: { filename: params.get("filename"), filepath: params.get("filepath") }
    })
    .then(() => {
      success && success();
    });
}
