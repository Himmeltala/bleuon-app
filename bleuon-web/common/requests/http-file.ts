/**
 * @description 文件 API
 * @author zheng
 * @since 2023/10/12
 */

import { http } from "@common/requests/use-axios";

/**
 * 上传图片
 *
 * @param formData
 * @returns
 */
export function uploadImageFile(formData: FormData) {
  return http.post<R>("/public/file/upload/image", formData);
}

/**
 * 删除图片
 *
 * @param str
 */
export function deleteImageFile(str: string, success?: Function) {
  const params = new URL(str).searchParams;
  http
    .delete<R>("/public/file/delete/image", {
      params: { filename: params.get("filename"), filepath: params.get("filepath") }
    })
    .then(() => {
      success && success();
    });
}
