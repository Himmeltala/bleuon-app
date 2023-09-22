import { linkTools, dia } from "jointjs";
import { BaseLink } from "./base";
import { PRIMARY_LINK, SECONDARY_LINK } from "../constants/key-vals";

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
