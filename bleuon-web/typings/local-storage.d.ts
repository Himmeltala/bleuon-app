declare interface Storage {
  setStorageWithAge: (key: string, token: string, maxAge: number, username: string, id: string) => void;
  getStorageWithAge: (key: string) => string | undefined;
}
