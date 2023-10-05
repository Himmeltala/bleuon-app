import { dia, elementTools, linkTools, shapes } from "jointjs";
import { NormalResizeTool, RotateTool, updateLabelText } from "./tools";

export function defineNamespace() {
  const Path = dia.Element.define(
    "bleuon.Path",
    {
      attrs: {
        body: {
          width: "calc(w)",
          height: "calc(h)",
          fill: "#fcfcfc",
          stroke: "#333333",
          strokeWidth: 1.5,
          cursor: "grab",
          "stroke-dasharray": "none"
        },
        label: {
          text: "",
          textVerticalAnchor: "middle",
          textAnchor: "middle",
          fill: "#333333",
          fontWeight: "normal",
          fontSize: 18,
          fontFamily: "微软雅黑",
          refX: "50%",
          refY: "50%"
        }
      }
    },
    {
      updateLabelText,
      addClickedTools(elementView: dia.ElementView) {
        const boundaryTool = new elementTools.Boundary();
        const resizeTool = new NormalResizeTool();
        const rotateTool = new RotateTool();

        elementView.addTools(
          new dia.ToolsView({
            name: "path-clicked-tools",
            tools: [boundaryTool, resizeTool, rotateTool]
          })
        );
      },
      removeClickedTools(elementView: dia.ElementView) {
        if (elementView.hasTools("path-clicked-tools")) {
          elementView.removeTools();
        }
      }
    }
  );

  const Link = shapes.standard.Link.define(
    "bleuon.Link",
    {
      router: { name: "manhattan" },
      attrs: {
        line: {
          fill: "none",
          stroke: "#333333",
          strokeWidth: 2
        }
      }
    },
    {
      updateLabelText(linkView: dia.LinkView, textInput: HTMLInputElement) {
        // @ts-ignore
        const { model } = linkView;
        const label = model.label(0);
        const cellText = label?.attrs?.text?.text || "";

        const bbox = linkView.getBBox();
        const centerX = bbox.x + bbox.width / 2;
        const centerY = bbox.y + bbox.height / 2;
        const oneThirdWidth = bbox.width / 3;
        textInput.value = cellText;

        textInput.style.top = centerY - 10 + "px";
        textInput.style.left = centerX - oneThirdWidth / 2 + "px";
        textInput.style.width = oneThirdWidth + "px";
        textInput.style.height = 20 + "px";
        textInput.style.display = "block";

        function handleKeydownEvent(event: any) {
          if (event.key === "Enter") {
            let newCellText = textInput.value;

            model.label(0, {
              position: 0.5,
              attrs: { text: { text: newCellText } }
            });

            textInput.style.display = "none";
            textInput.removeEventListener("keydown", handleKeydownEvent);
          }
        }

        textInput.value = "";
        textInput.addEventListener("keydown", handleKeydownEvent);
        textInput.focus();
      },
      addClickedTools(linkView: any) {
        // const verticesTool = new linkTools.Vertices();
        // const segmentsTool = new linkTools.Segments();
        // const sourceArrowheadTool = new linkTools.SourceArrowhead();
        // const targetArrowheadTool = new linkTools.TargetArrowhead();
        // const sourceAnchorTool = new linkTools.SourceAnchor();
        // const targetAnchorTool = new linkTools.TargetAnchor();
        const boundaryTool = new linkTools.Boundary();

        linkView.addTools(
          new dia.ToolsView({
            name: "link-tools",
            tools: [
              // verticesTool,
              // segmentsTool,
              boundaryTool
              // sourceArrowheadTool,
              // targetArrowheadTool,
              // sourceAnchorTool,
              // targetAnchorTool
            ]
          })
        );
      },
      removeClickedTools(linkView: any) {
        if (linkView.hasTools("link-tools")) {
          linkView.removeTools();
        }
      }
    }
  );

  return Object.assign(shapes, {
    bleuon: {
      Path,
      Link
    }
  });
}
