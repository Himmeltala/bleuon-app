declare type ConsumerModel = Partial<{
  id: string;
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
  createDate: string;
  modifyDate: string;
  authorities: AuthorityModel[];
  role: RoleModel;
}>;

declare type FlowchartModel = Partial<{
  id: string;
  filename: string;
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
  isBlueprint: number;
  deadShareDate: string;
  createDate: string;
  modifyDate: string;
  consumerId: string;
  consumer: ConsumerModel;
}>;

declare type BlueprintFlowchartModel = Partial<{
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
  modifyDate: string;
  flowchartId: string;
  flowchart: FlowchartModel;
  // not table file
  filename: string;
}>;

declare type DynamicModel = Partial<{
  id: string;
  content: string;
  digg: number;
  bury: number;
  createDate: string;
  modifyDate: string;
  consumerId: string;
}>;

declare type CollectingConsumerModel = Partial<{
  id: string;
  remark: string;
  createDate: string;
  modifyDate: string;
  collectorId: string;
  consumerId: string;
  consumer: ConsumerModel;
}>;

declare type ArticleCommentModel = Partial<{
  id: string;
  content: string;
  digg: number;
  bury: number;
  createDate: string;
  modifyDate: string;
  articleId: string;
  consumerId: string;
  consumer: ConsumerModel;
}>;

declare type ArticleModel = Partial<{
  id: string;
  title: string;
  titleTag: string;
  digg: number;
  bury: number;
  views: number;
  desc: string;
  descTag: string;
  descImgs: string;
  type: string;
  rankingType: string;
  content: string;
  consumerId: string;
  createDate: string;
  modifyDate: string;
  consumer: ConsumerModel;
}>;

declare type AdminModel = Partial<{
  id: string;
  username: string;
  password: string;
  phone: string;
  avatar: string;
  createDate: string;
  modifyDate: string;
  authorities: AuthorityModel[];
  roles: RoleModel[];
}>;

declare type AuthorityModel = Partial<{
  id: number;
  name: string;
  value: string;
  createDate: string;
  modifyDate: string;
}>;

declare type RoleModel = Partial<{
  id: number;
  remark: string;
  name: string;
  createDate: string;
  modifyDate: string;
  authorities: AuthorityModel[];
}>;
