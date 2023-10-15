import { shapes } from "jointjs";
import { Path, Link } from "./shapes";

export function defineNamespace() {
  return Object.assign(shapes, {
    bleuon: {
      Path,
      Link
    }
  });
}
