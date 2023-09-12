import { PrimaryLink } from "./shapes/link";
import { dia, shapes, connectors } from "jointjs";
import { addTools, removeTools, addClickedTools } from "./tools";

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
      color: "#F3F7F6"
    },
    cellViewNamespace: namespace,
    defaultLink: () => new PrimaryLink(),
    defaultConnectionPoint: { name: "boundary" },
    validateConnection: function (cellViewS, magnetS, cellViewT, magnetT, end, linkView) {
      if (config?.isPreventLinkFromInputPorts) {
        if (magnetS && magnetS.getAttribute("port-group") === "in") return false;
      }

      if (config?.isPreventLinkFromOutputToInputWithinOneElement) {
        if (cellViewS === cellViewT) return false;
      }

      if (config?.isPreventLinkToOutputPorts) {
        return magnetT && magnetT.getAttribute("port-group") === "in";
      }

      return true;
    },
    markAvailable: true,
    snapLinks: { radius: 5 },
    efaultConnectionPoint: { name: "anchor" },
    defaultConnector: function (sourcePoint, targetPoint, route, _, linkView) {
      // @ts-ignore
      const { model } = linkView;
      const targetElement = model.getTargetElement();
      const sourceElement = model.getSourceElement();
      const options = {
        targetDirection: targetElement
          ? targetElement.getCurveDirection(targetPoint)
          : connectors.curve.TangentDirections.AUTO,
        sourceDirection: sourceElement
          ? sourceElement.getCurveDirection(sourcePoint)
          : connectors.curve.TangentDirections.AUTO
      };
      return connectors.curve(sourcePoint, targetPoint, route, options, linkView);
    }
  });

  paper.on({
    "cell:mouseenter": view => {
      addTools(view);
    },
    "cell:mouseleave": view => {
      removeTools(view);
    },
    "cell:pointerclick": view => {
      addClickedTools(view);
    }
  });

  return {
    paper,
    graph
  };
}
