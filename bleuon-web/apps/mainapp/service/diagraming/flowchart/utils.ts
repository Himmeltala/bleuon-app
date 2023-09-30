/**
 * @description Diagraming 工具
 * @author 郑人滏 42020306
 * @since 2023/9/22
 * @link https://github.com/himmelbleu/bleuon-app
 */

export function getLinkOrElementAttr(model: any, attrKey: string) {
  if (model.isLink()) {
    attrKey = "line/" + attrKey;
  } else {
    attrKey = "body/" + attrKey;
  }
  return attrKey;
}
