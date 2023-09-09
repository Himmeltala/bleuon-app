import { shapes, dia } from "jointjs";

function generatePortsConfig() {
  const port = {
    attrs: {
      portBody: {
        magnet: true,
        r: 4,
        fill: "black",
        stroke: "black"
      }
    },
    markup: [
      {
        tagName: "circle",
        selector: "portBody"
      }
    ]
  };

  const topPort = Object.assign({ position: { name: "top" } }, port);
  const bottomPort = Object.assign({ position: { name: "bottom" } }, port);
  const leftPort = Object.assign({ position: { name: "left" } }, port);
  const rightPort = Object.assign({ position: { name: "right" } }, port);

  return { topPort, bottomPort, leftPort, rightPort };
}

function addRectPointermoveEvent(rect: any) {
  rect.on("cell:pointermove", function () {
    // 当鼠标指针悬停在rect元素上时触发此事件
    console.log("鼠标悬停在rect元素上");
  });
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

  const rect = new shapes.standard.Rectangle({
    position: { x: config?.x || 30, y: config?.y || 30 },
    size: { width: config?.width || 90, height: config?.height || 90 },
    attrs: {
      root: {
        magnet: false
      },
      body: {
        event: "element:rect:pointer",
        fill: "#8ECAE6"
      },
      label: {
        text: "",
        fontSize: 16,
        y: -10
      }
    },
    ports: {
      groups: {
        top: topPort,
        bottom: bottomPort,
        left: leftPort,
        right: rightPort
      }
    }
  });

  rect.addPorts([
    {
      group: "top"
    },
    {
      group: "bottom"
    },
    {
      group: "left"
    },
    {
      group: "right"
    }
  ]);

  rect.addTo(graph);
}
