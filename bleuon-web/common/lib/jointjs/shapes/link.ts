/**
 * @description 定义 Link
 * @author zheng
 * @since 2023/9/24
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

      input.value = label?.attrs?.text?.text || "";
      input.style.top = "0px";
      input.style.left = "0px";
      input.style.width = "200px";
      input.style.height = "100px";
      input.style.display = "block";

      function handleKeydownEvent(event: any) {
        if (event.key === "Enter") {
          model.label(0, {
            position: 0.5,
            attrs: { text: { text: input.value } }
          });

          input.style.display = "none";
          input.removeEventListener("keydown", handleKeydownEvent);
        } else if (event.key === "Escape") {
          input.style.display = "none";
          input.removeEventListener("keydown", handleKeydownEvent);
        }
      }

      input.value = "";
      input.focus();
      input.addEventListener("keydown", handleKeydownEvent);
    },
    addClickedTools(linkView: any) {
      const boundaryTool = new linkTools.Boundary();

      linkView.addTools(
        new dia.ToolsView({
          name: "link-tools",
          tools: [boundaryTool]
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
