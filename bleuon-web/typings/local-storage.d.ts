declare interface Storage {
  setStorageWithAge: <T>(key: string, value: T, expire: number) => void;
  getStorageWithAge: <T>(key: string, value: T, expire: number) => T | undefined;
  getToken: <T extends Token>(key: string) => T | undefined;
  setToken: <T extends { expire: number }>(key: string, value: T) => void;
}
