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
 * 邮箱、用户名或手机号注册
 *
 * @param entity 用户实体类
 */
export async function AccountRegister(entity: IUser, success?: Function, error?: Function) {
  try {
    const { data } = await request.post<ResponseEntity>("/auth/account-register", {
      username: entity.username,
      password: entity.password
    });

    success && success(data);
  } catch (err) {
    error && error();
  }
}

/**
 * 获取验证码
 *
 * @param email 电子邮箱地址
 * @param type login（登录）、reset（重置）、register（注册）
 */
export async function askMailVerifyCode(
  email: string,
  type: "login" | "register" | "reset",
  success?: Function,
  error?: Function
) {
  try {
    const { data } = await request.get<ResponseEntity>("/auth/aks-mail-verify-code", {
      params: {
        type,
        email
      }
    });
    success && success(data);
  } catch (err) {
    error && error();
  }
}

/**
 * 校验邮箱验证码
 *
 * @param user 用户实体类
 * @param code 验证码
 * @param type login（登录）、reset（重置）、register（注册）
 */
export async function verifyMailCode(
  user: IUser,
  code: string,
  type: "login" | "register" | "reset",
  success: Function,
  error?: Function
) {
  try {
    const { data } = await request.post<ResponseEntity>(
      "/auth/verify-mail-code",
      {
        email: user.email,
        password: user.password
      },
      {
        params: {
          type,
          code
        }
      }
    );
    if (type === "login") {
      localStorage.setStorageWithAge("BleuOn-Token", data.token, data.expire);
    }
    success(data);
  } catch (err) {
    error && error();
  }
}

export async function resetPassword(user: IUser, success: Function, error?: Function) {
  try {
    const { data } = await request.post<ResponseEntity>("/auth/reset-password", {
      email: user.email,
      password: user.password
    });
    success(data);
  } catch (err) {
    error && error();
  }
}
