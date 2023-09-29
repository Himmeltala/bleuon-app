declare interface Element {
  innerText?: string;
  offsetHeight?: number;
  offsetWidth?: number;
  offsetTop?: number;
}

declare type R<T = any> = {
  code: number;
  data: T;
  message: string;
};

declare type TokenR = {
  expire: number;
  token: string;
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
  isPublic: boolean;
  createDate: Date;
  modifyDate: Date;
  userId: string;
}>;
