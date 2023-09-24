/**
 * @description 定义 link 图形
 * @author 郑人滏 42020306
 * @since 2023/9/16
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { linkTools, dia, shapes } from "jointjs";
import { BASE_LINK, PRIMARY_LINK, SECONDARY_LINK } from "../constants/key-vals";

/**
 * 所有 Link 的基础
 */
export const BaseLink = shapes.standard.Link.define(
  BASE_LINK,
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
    updateText(linkView: dia.LinkView, textInput: HTMLInputElement) {
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
            attrs: {
              text: {
                text: newCellText
              }
            }
          });

          textInput.style.display = "none";
          textInput.removeEventListener("keydown", handleKeydownEvent);
        }
      }

      textInput.value = "";
      textInput.addEventListener("keydown", handleKeydownEvent);
      textInput.focus();
    },
    addTools(linkView: any) {
      const tools = [
        // new linkTools.Vertices(),
        new linkTools.SourceArrowhead(),
        new linkTools.TargetArrowhead()
      ];

      linkView.addTools(
        new dia.ToolsView({
          name: "link-tools",
          tools: tools
        })
      );
    },
    removeTools(linkView: any) {
      if (linkView.hasTools("link-tools")) {
        linkView.removeTools();
      }
    }
  }
);

/**
 * Primary Link
 */
export const PrimaryLink = BaseLink.define(PRIMARY_LINK, {}, {});

/**
 * Secondary Link
 */
export const SecondaryLink = BaseLink.define(
  SECONDARY_LINK,
  {},
  {
    addTools(linkView: dia.LinkView) {
      const tools = [
        new linkTools.Vertices({ stopPropagation: false }),
        new linkTools.Segments({ stopPropagation: false })
      ];
      linkView.addTools(
        new dia.ToolsView({
          name: "link-tools",
          tools: tools
        })
      );
    },
    removeTools(linkView: dia.LinkView) {
      if (linkView.hasTools("link-tools")) {
        linkView.removeTools();
      }
    }
  }
);
