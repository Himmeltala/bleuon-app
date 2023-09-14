import { shapes, linkTools, dia } from "jointjs";
import { PRIMARY_LINK, SECONDARY_LINK } from "../constants/key-vals";

/**
 * 基础 Link
 */
export const PrimaryLink = shapes.standard.Link.define(
  PRIMARY_LINK,
  {
    router: { name: "manhattan" },
    attrs: {
      line: {
        stroke: "#333333",
        strokeWidth: 2
      }
    }
  },
  {
    addTools(linkView: any) {
      const tools = [
        new linkTools.Vertices(),
        new linkTools.Remove(),
        new linkTools.SourceArrowhead(),
        new linkTools.TargetArrowhead()
      ];
      linkView.addTools(
        new dia.ToolsView({
          name: "primary-tools",
          tools: tools
        })
      );
    },
    removeTools(linkView: any) {
      if (linkView.hasTools("primary-tools")) {
        linkView.removeTools();
      }
    }
  }
);

export const SecondaryLink = shapes.standard.Link.define(
  SECONDARY_LINK,
  {
    attrs: {
      line: {
        stroke: "#3c4260",
        strokeWidth: 2
      }
    }
  },
  {
    addTools(linkView: any) {
      const tools = [
        new linkTools.Vertices({ stopPropagation: false }),
        new linkTools.Segments({ stopPropagation: false })
      ];
      linkView.addTools(
        new dia.ToolsView({
          name: "secondary-link",
          tools: tools
        })
      );
    },
    removeTools(linkView: any) {
      if (linkView.hasTools("secondary-link")) {
        linkView.removeTools();
      }
    }
  }
);
