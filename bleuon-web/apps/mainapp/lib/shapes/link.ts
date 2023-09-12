import { shapes, linkTools } from "jointjs";
import { PRIMARY_LINK } from "../constants/key-vals";

/**
 * 基础 Link
 */
export const PrimaryLink = shapes.standard.Link.define(
  PRIMARY_LINK,
  {
    attrs: {
      line: {
        stroke: "#333333",
        strokeWidth: 2
      }
    }
  },
  {
    getTools() {
      return [
        new linkTools.Vertices(),
        new linkTools.Remove(),
        new linkTools.SourceArrowhead(),
        new linkTools.TargetArrowhead()
      ];
    }
  }
);
