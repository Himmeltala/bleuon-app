declare type UserData = Partial<{
  id?: number;
  username?: string;
  password?: string;
  phone?: string;
  email?: string;
  profession?: string;
  company?: string;
  position?: string;
  avatar?: string;
  signature?: string;
  sex?: string;
  registerDate?: string;
}>;

declare type FlowchartData = Partial<{
  id: string;
  fileName: string;
  json: string;
  dataUri: string;
  width: number;
  height: number;
  bgColor: string;
  drawGrid: string;
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
  user: UserData;
}>;

declare type TemplateFlowchartData = Partial<{
  id: number;
  views: number;
  copies: number;
  digg: number;
  tags: string;
  scene: string;
  price: string;
  ranking: string;
  createDate: Date;
  flowchartId: string;
  flowchart: FlowchartData;

  // not exist
  fileName: string;
}>;
