Storage.prototype.setStorageWithAge = (
  key: string,
  token: string,
  maxAge: number,
  username: string,
  id: string
) => {
  if (isNaN(maxAge) || maxAge < 1) throw new Error("maxAge must be a number.");
  localStorage.setItem(
    key,
    JSON.stringify({
      token, // token
      expire: Date.now(), // 当前时间戳
      maxAge: maxAge, // 过期时间戳
      username,
      id
    })
  );
};

Storage.prototype.getStorageWithAge = (key: string) => {
  const parsed = JSON.parse(localStorage.getItem(key));
  if (parsed) {
    const { token, expire, maxAge } = parsed;
    if (expire + maxAge < Date.now()) {
      localStorage.removeItem(key);
      return undefined;
    }
    return token;
  } else return undefined;
};
