/**
 * @description 管理员 API
 * @author zheng
 * @since 2023/10/22
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { http } from "@common/requests/use-axios";

/**
 * 邮箱、用户名或手机号登录
 *
 * @param model
 * @param success
 */
export function authLogin(model: ConsumerModel, success: (res: Token) => void) {
  http.post<R<Token>>("/public/entrance/admin-login", model).then(({ data }) => {
    success(data.data);
  });
}
