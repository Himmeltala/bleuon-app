import type { AxiosRequestConfig } from "axios";

declare global {
  interface Element {
    innerText?: string;
    offsetHeight?: number;
    offsetWidth?: number;
    offsetTop?: number;
  }

  type R<T = any> = {
    code: 200 | 403 | 500;
    data: T;
    message: string;
  };

  type Token = {
    expire: number;
    value: string;
    username: string;
    id: string;
  };

  /**
   * 分页数据
   */
  type PageInfo<T> = {
    list: T[];
    total: number;
    pageSize: number;
    pageNum: number;
    hasNextPage: boolean;
    isFirstPage: boolean;
    isLastPage: boolean;
  };

  /**
   * 排序
   */
  type Sequence = { isAsc: boolean; col: string };

  /**
   * 基本条件
   */
  type BasicCriteria = {
    pageSize?: number;
    currPage?: number;
    sequences?: Sequence[];
  };

  /**
   * 条件，传递 T 与基本条件交叉扩展类型
   */
  type Criteria<T = undefined | null> = T extends undefined | null
    ? BasicCriteria
    : T & BasicCriteria;

  /**
   * 请求配置
   */
  type ReqConfig<M = undefined | null, T = undefined | null> = {
    model?: M extends undefined | null ? any : M;
    config?: Omit<AxiosRequestConfig, "params"> & {
      params?: T extends undefined | null ? any : T;
    };
  };
}
