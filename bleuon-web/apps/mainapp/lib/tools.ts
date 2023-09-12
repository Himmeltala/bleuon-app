import { dia, elementTools } from "jointjs";

// @ts-ignore
export const ResizeTool = elementTools.Control.extend({
  children: [
    {
      tagName: "rect",
      selector: "handle",
      attributes: {
        cursor: "pointer",
        width: 10,
        height: 10
      }
    },
    {
      tagName: "rect",
      selector: "extras",
      attributes: {
        "pointer-events": "none",
        fill: "none",
        stroke: "#33334F",
        "stroke-dasharray": "2,4",
        rx: 5,
        ry: 5
      }
    }
  ],
  getPosition: function (view: any) {
    const model = view.model;
    const { width, height } = model.size();
    return { x: width, y: height };
  },
  setPosition: function (view: any, coordinates: any) {
    const model = view.model;
    model.resize(Math.max(coordinates.x - 10, 1), Math.max(coordinates.y - 10, 1));
  }
});

export function addTools(view: any) {
  const { paper, model } = view;

  view.el.classList.add("active");

  view.addTools(
    new dia.ToolsView({
      name: "primary-tools",
      tools: [...model.getTools()]
    })
  );
}

export function removeTools(view: any) {
  view.el.classList.remove("active");
}

export function addClickedTools(view: any) {
  const { paper, model } = view;

  view.addTools(
    new dia.ToolsView({
      name: "resize-tools",
      tools: [
        new ResizeTool({
          selector: "body"
        })
      ]
    })
  );
}
