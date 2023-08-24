import request from "./use-axios";

export async function login(entity: IUser, success: Function, error?: Function) {
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
    success();
  } catch (err) {
    error && error();
  }
}
