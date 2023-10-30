/**
 * @description 修改 localStorage 原型对象
 * @author zheng
 * @since 2023/10/6
 */

Storage.prototype.setStorageWithAge = function <T extends { expire: number }>(
  key: string,
  value: T,
  expire: number
) {
  function isTimestamp(timestamp: number) {
    const regex = /^\d{10}$/;
    if (regex.test(timestamp.toString())) {
      const date = new Date(timestamp * 1000);
      return !isNaN(date.getTime());
    }
    return false;
  }

  if (isTimestamp(expire)) {
    throw new Error("不是一个合法的时间戳");
  }

  localStorage.setItem(key, JSON.stringify(value));
};

Storage.prototype.getStorageWithAge = function <T>(key: string, value: T, expire: number) {
  if (value) {
    if (expire < Date.now()) {
      localStorage.removeItem(key);
      return undefined;
    }
    return value;
  } else return undefined;
};

Storage.prototype.getToken = function <T extends Token>(key: string) {
  const parsed = JSON.parse(localStorage.getItem(key));
  if (parsed) {
    return localStorage.getStorageWithAge<T>(key, parsed, parsed.expire);
  } else {
    return undefined;
  }
};

Storage.prototype.setToken = function <T extends { expire: number }>(key: string, value: T) {
  localStorage.setStorageWithAge(key, value, value.expire);
};
