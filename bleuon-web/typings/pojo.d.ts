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
