declare interface Storage {
  setStorageWithAge: (key: string, token: string, maxAge: number) => void;
  getStorageWithAge: (key: string) => string | undefined;
}
