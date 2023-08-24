import request from "./use-axios";

/**
 * 邮箱、用户名或手机号登录
 *
 * @param entity 用户实体类
 */
export async function AccountLogin(entity: IUser, success: Function, error?: Function) {
  try {
    const { data } = await request.post<ResponseEntity>(
      "/auth/login",
      {
        username: entity.username,
        password: entity.password
      },
      {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }
    );

    localStorage.setStorageWithAge("BleuOn-Token", data.token, data.expire);
    success(data);
  } catch (err) {
    error && error();
  }
}

/**
 * 获取验证码
 *
 * @param mail 邮箱地址
 * @param type login（登录）、reset（重置）、register（注册）
 */
export async function askMailCode(
  mail: string,
  type: string,
  success?: Function,
  error?: Function
) {
  try {
    const { data } = await request.get<ResponseEntity>("/auth/aks-mail-verify-code", {
      params: {
        type,
        mail
      }
    });
    success && success(data);
  } catch (err) {
    error && error();
  }
}

export async function mailCodeLogin(
  mail: string,
  code: string,
  type: string,
  success?: Function,
  error?: Function
) {
  try {
    const { data } = await request.post<ResponseEntity>(
      "/auth/verify-mail-code",
      {
        mail,
        type,
        code
      },
      {
        headers: {
          "Content-Type": "application/multipart/form-data"
        }
      }
    );

    localStorage.setStorageWithAge("BleuOn-Token", data.token, data.expire);
    success && success(data);
  } catch (err) {
    error && error();
  }
}
