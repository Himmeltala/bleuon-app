import * as FlowchartModule from "./http-flowchart";
import * as BlueprintModule from "./http-blueprint";
import * as CellModule from "./http-cells";
import * as ConsumerModule from "./http-consumer";
import * as FileModule from "./http-file";
import * as DiscussionModule from "./http-discussion";
import * as AdminModule from "./http-admin";
import * as PermissionModule from "./http-permission";

export namespace Requests {
  export const Flowchart: typeof FlowchartModule = FlowchartModule;
  export const Blueprint: typeof BlueprintModule = BlueprintModule;
  export const Cell: typeof CellModule = CellModule;
  export const File: typeof FileModule = FileModule;
  export const Consumer: typeof ConsumerModule = ConsumerModule;
  export const Discussion: typeof DiscussionModule = DiscussionModule;
  export const Admin: typeof AdminModule = AdminModule;
  export const Permission: typeof PermissionModule = PermissionModule;
}
