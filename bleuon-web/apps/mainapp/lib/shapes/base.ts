import { dia, connectors, elementTools } from "jointjs";
import { BASE_SHAPE } from "../constants/key-vals";

export const BaseShape = dia.Element.define(
  BASE_SHAPE,
  {},
  {
    getCurveDirection() {
      return connectors.curve.TangentDirections.AUTO;
    },
    getTools() {
      return [
        new elementTools.Connect({
          focusOpacity: 0,
          markup: [{ tagName: "rect", attributes: {} }]
        })
      ];
    }
  }
);
