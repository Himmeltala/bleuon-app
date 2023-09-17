import { dia } from "jointjs";

export function editCellViewText(cellView: dia.CellView, eleInput: Ref<HTMLInputElement>) {
  // @ts-ignore
  const { model } = cellView;
  const { position, size } = model.attributes;

  const cellText = model.attr("label/text");
  eleInput.value.value = cellText;

  eleInput.value.style.top = position.y + "px";
  eleInput.value.style.left = position.x + "px";
  eleInput.value.style.width = size.width + "px";
  eleInput.value.style.height = size.height + "px";
  eleInput.value.style.display = "block";

  function handleKeydownEvent(event: any) {
    if (event.key === "Enter") {
      let newCellText = eleInput.value.value;
      model.attr("label/text", newCellText);
      eleInput.value.style.display = "none";
      eleInput.value.removeEventListener("keydown", handleKeydownEvent);
    }
  }

  eleInput.value.addEventListener("keydown", handleKeydownEvent);

  eleInput.value.focus();
}
