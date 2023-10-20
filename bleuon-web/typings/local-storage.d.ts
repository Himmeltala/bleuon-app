declare interface Storage {
  /**
   * 设置 storage 带有过期日期
   *
   * 存储一个值到 storage 中，数据格式必须带有一个 { expire: number }
   *
   * @param key 存储 key
   * @param value 存储值
   * @param expire 过期日期，时间戳
   * @returns
   */
  setStorageWithAge: <T extends { expire: number }>(key: string, value: T, expire: number) => void;
  /**
   * 获取带有过期日期的 storage。
   *
   * 校验 value 是否过期，如果过期，需要借助 key 移除在浏览器存储的 storage item。
   *
   * @param key 存储 key
   * @param value 存储值
   * @param expire 过期日期，时间戳
   * @returns
   */
  getStorageWithAge: <T>(key: string, value: T, expire: number) => T | undefined;
  /**
   * 获取 token
   *
   * @param key 存储的 key
   * @returns
   */
  getToken: <T extends Token>(key: string) => T | undefined;
  /**
   * 设置 token
   *
   * @param key 存储的 key
   * @param value 存储的值
   * @returns
   */
  setToken: <T extends { expire: number }>(key: string, value: T) => void;
}
