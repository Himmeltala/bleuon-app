export function getShpeType(type: string, attrKey: string) {
  const lowerCaseType = type.toLowerCase();

  if (lowerCaseType.includes("link")) {
    attrKey = "line/" + attrKey;
  } else if (lowerCaseType.includes("element")) {
    attrKey = "body/" + attrKey;
  }

  return attrKey;
}
