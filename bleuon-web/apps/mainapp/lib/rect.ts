import { dia } from "jointjs";

const BleuonRect = dia.Element.define(
  "standard.bleuon.Rectangle",
  {
    attrs: {
      body: {
        width: "calc(w)",
        height: "calc(h)",
        strokeWidth: 2,
        stroke: "#000000",
        fill: "#FFFFFF"
      },
      label: {
        textVerticalAnchor: "middle",
        textAnchor: "middle",
        x: "calc(0.5*w)",
        y: "calc(0.5*h)",
        fontSize: 12,
        fill: "#333333"
      }
    }
  },
  {
    markup: [
      {
        tagName: "rect",
        selector: "body"
      },
      {
        tagName: "text",
        selector: "label"
      }
    ]
  }
);

function generatePortsConfig() {
  const port = {
    attrs: {
      body: {
        magnet: true,
        r: 5
      }
    },
    markup: [
      {
        tagName: "circle",
        selector: "body"
      }
    ]
  };

  const topPort = Object.assign({ position: { name: "top" } }, port);
  const bottomPort = Object.assign({ position: { name: "bottom" } }, port);
  const leftPort = Object.assign({ position: { name: "left" } }, port);
  const rightPort = Object.assign({ position: { name: "right" } }, port);

  return { topPort, bottomPort, leftPort, rightPort };
}

export function createRect(
  graph: dia.Graph,
  config?: {
    x?: number;
    y?: number;
    width?: number;
    height?: number;
  }
) {
  const { topPort, bottomPort, leftPort, rightPort } = generatePortsConfig();

  const rect = new BleuonRect({
    position: { x: config?.x || 30, y: config?.y || 30 },
    size: { width: config?.width || 90, height: config?.height || 90 },
    ports: {
      groups: {
        top: topPort,
        bottom: bottomPort,
        left: leftPort,
        right: rightPort
      }
    }
  });

  rect.addPorts([{ group: "top" }, { group: "bottom" }, { group: "left" }, { group: "right" }]);

  rect.addTo(graph);
}
