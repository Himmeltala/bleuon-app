import { dia, shapes, connectors, elementTools } from "jointjs";
import { BASE_SHAPE } from "./constants/key-vals";
import { PrimaryLink } from "./shapes/link";
import { addTools, removeTools } from "./tools";

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
    }
  });

  return {
    paper,
    graph
  };
}

export const BaseShape = dia.Element.define(
  BASE_SHAPE,
  {},
  {
    getCurveDirection() {
      return connectors.curve.TangentDirections.AUTO;
    },
    getTools() {
      return [
        new elementTools.Connect({
          focusOpacity: 0,
          markup: [
            {
              tagName: "rect",
              attributes: {}
            }
          ]
        })
      ];
    }
  }
);
