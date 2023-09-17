import { dia } from "jointjs";

export function editCellViewText(elementView: dia.ElementView, textInput: HTMLInputElement) {
  // @ts-ignore
  const { model } = elementView;
  const { position, size } = model.attributes;

  const cellText = model.attr("label/text");
  textInput.value = cellText;

  textInput.style.top = position.y + "px";
  textInput.style.left = position.x + "px";
  textInput.style.width = size.width + "px";
  textInput.style.height = size.height + "px";
  textInput.style.display = "block";

  function handleKeydownEvent(event: any) {
    if (event.key === "Enter") {
      let newCellText = textInput.value;
      model.attr("label/text", newCellText);
      textInput.style.display = "none";
      textInput.removeEventListener("keydown", handleKeydownEvent);
    }
  }

  textInput.addEventListener("keydown", handleKeydownEvent);

  textInput.focus();
}
