/**
 * @description Diagraming 工具
 * @author zheng
 * @since 2023/9/22
 * @link https://gitee.com/himmelbleu/bleuon-app
 */

export function getLinkOrElementAttr(model: any, attrKey: string) {
  if (model.isLink()) {
    attrKey = "line/" + attrKey;
  } else {
    attrKey = "body/" + attrKey;
  }
  return attrKey;
}
