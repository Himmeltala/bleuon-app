declare interface Element {
  innerText?: string;
  offsetHeight?: number;
  offsetWidth?: number;
  offsetTop?: number;
}

type ResponseBody = {
  token: string;
  expire: number;
  message: string;
  username: string;
  authorities: string[];
};

declare interface ResponseEntity {
  code: number;
  message: string;
  data: any;
  expire: number; // 毫秒数
  token: string;
}
