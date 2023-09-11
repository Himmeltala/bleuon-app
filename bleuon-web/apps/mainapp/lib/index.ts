import { dia, shapes, linkTools } from "jointjs";

export function initJointJs(
  config: {
    isPreventLinkFromInputPorts: boolean;
    isPreventLinkFromOutputToInputWithinOneElement: boolean;
    isPreventLinkToOutputPorts: boolean;
  } = {
    isPreventLinkFromInputPorts: false,
    isPreventLinkFromOutputToInputWithinOneElement: true,
    isPreventLinkToOutputPorts: false
  }
) {
  const namespace = shapes;
  const graph = new dia.Graph({}, { cellNamespace: namespace });

  const paper = new dia.Paper({
    el: document.getElementById("diagraming"),
    model: graph,
    height: "85vh",
    width: "85vw",
    gridSize: 10,
    drawGrid: true,
    background: {
      color: "rgba(0, 255, 0, 0.3)"
    },
    cellViewNamespace: namespace,
    linkPinning: false, // Prevent link being dropped in blank paper area
    defaultLink: () => new shapes.standard.Link(),
    defaultConnectionPoint: { name: "boundary" },
    validateConnection: function (cellViewS, magnetS, cellViewT, magnetT, end, linkView) {
      if (config?.isPreventLinkFromInputPorts) {
        // Prevent linking from input ports
        if (magnetS && magnetS.getAttribute("port-group") === "in") return false;
      }

      if (config?.isPreventLinkFromOutputToInputWithinOneElement) {
        // Prevent linking from output ports to input ports within one element
        if (cellViewS === cellViewT) return false;
      }

      if (config?.isPreventLinkToOutputPorts) {
        // Prevent linking to output ports
        return magnetT && magnetT.getAttribute("port-group") === "in";
      }

      return true;
    },
    markAvailable: true,
    // Enable link snapping within 20px lookup radius
    snapLinks: { radius: 5 }
  });

  return {
    paper,
    graph
  };
}

export function showLinkTools(linkView: joint.dia.LinkView) {
  var tools = new dia.ToolsView({
    tools: [
      new linkTools.Remove({
        distance: "50%",
        markup: [
          {
            tagName: "circle",
            selector: "button",
            attributes: {
              r: 7,
              fill: "#f6f6f6",
              stroke: "#ff5148",
              "stroke-width": 2,
              cursor: "pointer"
            }
          },
          {
            tagName: "path",
            selector: "icon",
            attributes: {
              d: "M -3 -3 3 3 M -3 3 3 -3",
              fill: "none",
              stroke: "#ff5148",
              "stroke-width": 2,
              "pointer-events": "none"
            }
          }
        ]
      })
    ]
  });

  linkView.addTools(tools);
}

export * from "./rect";
export { dia, shapes };
