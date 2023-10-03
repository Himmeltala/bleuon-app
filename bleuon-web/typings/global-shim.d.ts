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

declare type TokenR = {
  expire: number;
  value: string;
  username: string;
  id: string;
};

declare type FlowchartData = Partial<{
  id: string;
  fileName: string;
  json: string;
  dataUri: string;
  width: number;
  height: number;
  bgColor: string;
  gridSize: number;
  connectorDefault: string;
  routerDefault: string;
  isPublic: number;
  isLegal: number;
  isShare: number;
  deadShareDate: Date;
  createDate: Date;
  modifyDate: Date;
  userId: string;
}>;
