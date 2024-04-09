/**
 * @description 业务
 * @author zheng
 * @since 2023/9/24
 */
import * as CellModule from "./cell-service";
import * as ListenerModule from "./listener-service";
import * as UtilsModule from "./utils";

export namespace Services {
  export const Cell: typeof CellModule = CellModule;
  export const Listener: typeof ListenerModule = ListenerModule;
  export const Utils: typeof UtilsModule = UtilsModule;
}
