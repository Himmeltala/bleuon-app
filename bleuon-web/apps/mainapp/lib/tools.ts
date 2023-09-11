import { dia } from "jointjs";

export function addTools(view: any) {
  const { paper, model } = view;
  paper.removeTools();
  const tools = new dia.ToolsView({ tools: model.getTools() });
  view.el.classList.add("active");
  view.addTools(tools);
}

export function removeTools(view: any) {
  view.el.classList.remove("active");
  view.removeTools();
}
