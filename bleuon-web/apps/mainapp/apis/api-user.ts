import request from "./use-axios";

/**
 * 邮箱、用户名或手机号登录
 *
 * @param entity 用户实体类
 */
export async function accountLogin(entity: IUser, success: Function, error?: Function) {
  try {
    const body = {
      username: entity.username,
      password: entity.password
    };

    const headers = {
      "Content-Type": "application/x-www-form-urlencoded"
    };

    const { data } = await request.post<R<TokenR>>("/auth/login", body, { headers });

    localStorage.setStorageWithAge(KeyVals.MAINAPP_TOKEN_KEY, data.data.token, data.data.expire);
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
export async function accountRegister(entity: IUser, success?: Function, error?: Function) {
  try {
    const body = {
      username: entity.username,
      password: entity.password
    };

    const { data } = await request.post<R>("/auth/account-register", body);

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
    const params = { type, email };

    const { data } = await request.get<R>("/auth/aks-mail-verify-code", { params });
    success && success(data);
  } catch (err) {
    error && error();
  }
}

/**
 * 校验邮箱验证码
 *
 * @param entity 用户实体类
 * @param code 验证码
 * @param type login（登录）、reset（重置）、register（注册）
 */
export async function verifyMailCode(
  entity: IUser,
  code: string,
  type: "login" | "register" | "reset",
  success: Function,
  error?: Function
) {
  try {
    const body = {
      email: entity.email,
      password: entity.password
    };

    const params = { type, code };

    const { data } = await request.post<R<TokenR>>("/auth/verify-mail-code", body, { params });

    if (type === "login") {
      localStorage.setStorageWithAge(KeyVals.MAINAPP_TOKEN_KEY, data.data.token, data.data.expire);
    }
    success(data);
  } catch (err) {
    error && error();
  }
}

/**
 * 重置密码
 *
 * @param entity 用户实体类
 */
export async function resetPassword(entity: IUser, success: Function, error?: Function) {
  try {
    const body = {
      email: entity.email,
      password: entity.password
    };

    const { data } = await request.post<R>("/auth/reset-password", body);
    success(data);
  } catch (err) {
    error && error();
  }
}

/**
 * 请求退出登录
 *
 * @param success 退出登录成功回调函数
 * @param error 退出登录失败回调函数
 */
export async function logout(success: Function, error?: Function) {
  try {
    const { data } = await request.post<R>("/auth/logout");
    if (data.code === 200) {
      localStorage.removeItem(KeyVals.MAINAPP_TOKEN_KEY);
      success && success(data);
    } else error && error();
  } catch (err) {
    error && error();
  }
}
