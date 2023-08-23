import request from "./use-axios";

export async function login(entity: IUser, success: Function, error?: Function) {
  try {
    const { data: resp } = await request.post<ResponseEntity>(
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

    if (resp.statusCodeValue == 200) {
      localStorage.setStorageWithAge("BleuOn-Token", resp.body.token, resp.body.expire);
      ElMessage.success(resp.body.message);
      success();
    } else {
      ElMessage.error(resp.body.message);
      error && error();
    }
  } catch (err) {
    console.error(err);
    ElMessage.error("发生了错误，请查看控制台。");
    error && error();
  }
}

export async function test() {
  try {
    const { data } = await request.post<ResponseEntity>("/test/hello");
  } catch (err) {}
}
