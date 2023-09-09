declare interface Element {
  innerText?: string;
  offsetHeight?: number;
  offsetWidth?: number;
  offsetTop?: number;
}

type R<T = any> = {
  code: number;
  data: T;
  message: string;
};

type TokenR = {
  expire: number;
  token: string;
};
