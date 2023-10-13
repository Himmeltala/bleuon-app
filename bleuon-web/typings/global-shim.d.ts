declare interface Element {
  innerText?: string;
  offsetHeight?: number;
  offsetWidth?: number;
  offsetTop?: number;
}

declare type R<T = any> = {
  code: 200 | 403 | 500;
  data: T;
  message: string;
};

declare type Token = {
  expire: number;
  value: string;
  username: string;
  id: string;
};
