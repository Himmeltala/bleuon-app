/**
 * @description 定义 Link
 * @author zheng
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */
import { shapes, dia, linkTools } from "jointjs";

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
    upgradeLabelText(linkView: dia.LinkView, input: HTMLInputElement) {
      // @ts-ignore
      const { model } = linkView;
      const label = model.label(0);
      const cellText = label?.attrs?.text?.text || "";

      const bbox = linkView.getBBox();
      const centerX = bbox.x + bbox.width / 2;
      const centerY = bbox.y + bbox.height / 2;
      const oneThirdWidth = bbox.width / 3;
      input.value = cellText;

      input.style.top = centerY - 10 + "px";
      input.style.left = centerX - oneThirdWidth / 2 + "px";
      input.style.width = oneThirdWidth + "px";
      input.style.height = 20 + "px";
      input.style.display = "block";

      function handleKeydownEvent(event: any) {
        if (event.key === "Enter") {
          let newCellText = input.value;

          model.label(0, {
            position: 0.5,
            attrs: { text: { text: newCellText } }
          });

          input.style.display = "none";
          input.removeEventListener("keydown", handleKeydownEvent);
        }
      }

      input.value = "";
      input.addEventListener("keydown", handleKeydownEvent);
      input.focus();
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

export default Link;
