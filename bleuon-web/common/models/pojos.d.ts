declare type UserData = Partial<{
  id: number;
  username: string;
  password: string;
  phone: string;
  email: string;
  company: string;
  degree: string;
  profession: string;
  position: string;
  avatar: string;
  signature: string;
  sex: string;
  registerDate: string;
  modifyDate: string;
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
  deadShareDate: string;
  createDate: string;
  modifyDate: string;
  userId: string;
  user: UserData;
}>;

declare type TemplateFlowchartData = Partial<{
  id: string;
  views: number;
  copies: number;
  stars: number;
  tags: string;
  scene: string;
  price: string;
  description: string;
  ranking: string;
  createDate: string;
  flowchartId: string;
  flowchart: FlowchartData;
  // not table file
  fileName: string;
}>;
