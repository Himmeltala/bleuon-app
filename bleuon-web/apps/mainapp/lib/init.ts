import { PrimaryLink } from "./shapes/link";
import { dia, shapes } from "jointjs";

function addTools(view: any) {
  // @ts-ignore
  const { model } = view;
  if (model?.addTools) {
    model.addTools(view);
  }
}

function removeTools(view: any) {
  // @ts-ignore
  const { model } = view;
  if (model?.removeTools) {
    model.removeTools(view);
  }
}

function addJointPaperEvents(paper: dia.Paper) {
  paper.on({
    "cell:mouseenter": view => {
      addTools(view);
    },
    "cell:mouseleave": view => {
      removeTools(view);
    },
    "link:mouseenter": view => {
      addTools(view);
    },
    "link:mouseleave": view => {
      removeTools(view);
    }
  });
}

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
    el: document.getElementById("bleu__diagraming-content"),
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
    efaultConnectionPoint: { name: "anchor" }
  });

  addJointPaperEvents(paper);

  return {
    paper,
    graph
  };
}
