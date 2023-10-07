/**
 * @description jointjs 事件监听、以及页面上的一些 change 事件
 * @author zheng
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia } from "@mainapp/lib";
import * as Data from "@mainapp/data/diagraming/flowchart";

/**
 * 安装 Link 工具
 *
 * @param currView 当前点击的 Link
 */
export function mountToolsLink(
  currView: dia.LinkView,
  clickedLastView: Ref<{ model: any; view: dia.LinkView }>
) {
  clickedLastView.value.model?.removeClickedTools(clickedLastView.value.view);

  // @ts-ignore
  currView.model.addClickedTools(currView);

  clickedLastView.value = {
    // @ts-ignore
    model: currView.model,
    view: currView
  };
}

/**
 * 卸载 Link 工具
 *
 * @param clickedLastView 上一次点击的 Link
 */
export function unmountToolsLink(clickedLastView: Ref<{ model: any; view: dia.ElementView }>) {
  clickedLastView.value.model?.removeClickedTools(clickedLastView.value.view);

  clickedLastView.value = {
    model: null,
    view: null
  };
}

/**
 * 安装元素工具
 *
 * @param currView 当前点击的图形
 * @param clickedLastView 保存当前点击的图形作为上一次点击的图形
 */
export function mountToolsElement(
  currView: dia.ElementView,
  clickedLastView: Ref<{ model: any; view: dia.ElementView }>
) {
  clickedLastView.value.model?.removeClickedTools(clickedLastView.value.view);

  // @ts-ignore
  currView.model.addClickedTools(currView);

  clickedLastView.value = {
    // @ts-ignore
    model: currView.model,
    view: currView
  };
}

/**
 * 卸载元素工具
 *
 * @param clickedLastView 上一次点击的图形
 */
export function unmountToolsElement(clickedLastView: Ref<{ model: any; view: dia.ElementView }>) {
  clickedLastView.value.model?.removeClickedTools(clickedLastView.value.view);

  clickedLastView.value = {
    model: null,
    view: null
  };
}

/**
 * 当鼠标点击了 paper 时，卸载元素和 Link 上的工具
 */
export function onPointerClickBlank() {
  // 改变布尔变量，让头部工具根据这些变量设置 disabled
  Data.isClickedElement.value = false;
  Data.isClickedLink.value = false;

  unmountToolsLink(Data.clickedLastView);
  unmountToolsElement(Data.clickedLastView);
}

/**
 * 当鼠标点击了元素时，不是 Link 元素
 *
 * @param view
 */
export function onPointerClickElement(view: dia.ElementView) {
  // 改变布尔变量，让头部工具根据这些变量设置 disabled
  Data.isClickedElement.value = true;
  Data.isClickedLink.value = false;

  // 存储当前点击的元素对象
  Data.clickedCurrView.value = view;

  // 安装元素定义的工具
  mountToolsElement(view, Data.clickedLastView);
}

/**
 * 当鼠标点击了 Link 时
 *
 * @param view
 */
export function onPointerClickLink(view: dia.LinkView) {
  // 改变布尔变量，让头部工具根据这些变量设置 disabled
  Data.isClickedLink.value = true;
  Data.isClickedElement.value = false;

  // 存储当前点击的元素对象
  Data.clickedCurrView.value = view;
  // 安装 link 定义的工具
  mountToolsLink(view, Data.clickedLastView);
}

/**
 * 当鼠标在 paper 上滚动鼠标滚轮时
 *
 * @param evt
 * @param paper
 */
export function onMousewheelBlank(evt: any, paper: dia.Paper) {
  evt.preventDefault(); // 防止浏览器默认滚动行为
  const delta = evt.originalEvent.deltaY; // 获取滚动方向
  const scrollFactor = 30; // 滚动因子
  const scaleFactor = 0.1; // 缩放因子

  if (evt.ctrlKey) {
    if (delta > 0) {
      // 向下滚动，缩小画布
      Data.currentScale.value -= scaleFactor;
      if (Data.currentScale.value < 0.2) {
        Data.currentScale.value = 0.2;
      }
    } else {
      // 向上滚动，放大画布
      Data.currentScale.value += scaleFactor;
      if (Data.currentScale.value > 2) {
        Data.currentScale.value = 2;
      }
    }

    paper.scale(Data.currentScale.value, Data.currentScale.value);
  } else if (evt.shiftKey) {
    if (delta > 0) {
      // 向下滚动，向右移动画布
      Data.currentOffsetX.value += scrollFactor;
    } else {
      // 向上滚动，向左移动画布
      Data.currentOffsetX.value -= scrollFactor;
    }
  } else {
    if (delta > 0) {
      // 向上滚动，向上移动画布
      Data.currentOffsetY.value -= scrollFactor;
    } else {
      // 向下滚动，向下移动画布
      Data.currentOffsetY.value += scrollFactor;
    }
  }

  paper.setOrigin(Data.currentOffsetX.value, Data.currentOffsetY.value); // 重置画布的原点
}

/**
 * 监听键盘按下的事件监听。
 *
 * 一：当按下键盘 delete 或回退键时删除选中的元素
 * 二：当按下 Ctrl+S 保存数据到数据库中
 */
export function onKeydown(shortcutKey: { ctrlS: Function }) {
  document.addEventListener("keydown", event => {
    if (event.ctrlKey) {
      if (event.key === "s") {
        event.preventDefault();
        shortcutKey.ctrlS();
      }
    }

    if (event.key === "Backspace" || event.key === "Delete") {
      if (Data.clickedCurrView.value?.model) {
        Data.isClickedElement.value = false;
        Data.isClickedLink.value = false;
        Data.clickedCurrView.value.model.remove();
      }
    }
  });
}

/**
 * 双击时
 *
 * @param currView
 * @param input
 */
export function onPointerDbclickElement(
  currView: dia.ElementView | dia.LinkView,
  input: HTMLInputElement
) {
  Data.clickedCurrView.value = null;

  // @ts-ignore
  const { model } = currView;
  if (model?.updateLabelText) {
    model.updateLabelText(currView, input);
  }
}
