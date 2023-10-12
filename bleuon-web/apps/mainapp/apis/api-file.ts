/**
 * @description 文件 API
 * @author zheng
 * @since 2023/10/12
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "./use-axios";

/**
 * 上传 ckeditor 图片
 *
 * @param formData
 * @returns
 */
export function uploadCkEditorImage(formData: FormData) {
  return request.post<R>("/public/file/upload/image", formData);
}
