/**
 * @description jointjs 事件监听、以及页面上的一些 change 事件
 * @author zheng
 * @since 2023/9/24
 */

import { dia } from "jointjs";

type LastView = Ref<{ model: any; view: dia.LinkView | dia.ElementView }>;
type CurrView = Ref<dia.ElementView | dia.LinkView>;

/**
 * jointjs 事件业务
 */
export class JointJsEventService {
  private _lastView: LastView;
  private _currView: CurrView;
  private _activeLink: Ref<boolean>;
  private _activeElem: Ref<boolean>;
  private _scrollFactor: number;
  private _scaleFactor: number;
  private _scale: Ref<number>;
  private _offsetX: Ref<number>;
  private _offsetY: Ref<number>;

  set lastView(view: { model: any; view: dia.LinkView | dia.ElementView }) {
    this._lastView.value = view;
  }

  get lastView() {
    return this._lastView.value;
  }

  set currView(view: dia.ElementView | dia.LinkView) {
    this._currView.value = view;
  }

  get currView() {
    return this._currView.value;
  }

  set activeLink(value: boolean) {
    this._activeLink.value = value;
  }

  get activeLink() {
    return this._activeLink.value;
  }

  set activeElem(value: boolean) {
    this._activeElem.value = value;
  }

  get activeElem() {
    return this._activeElem.value;
  }

  set scrollFactor(value: number) {
    this._scrollFactor = value;
  }

  get scrollFactor() {
    return this._scrollFactor || 30;
  }

  set scaleFactor(value: number) {
    this._scaleFactor = value;
  }

  get scaleFactor() {
    return this._scaleFactor || 0.1;
  }

  set scale(value: number) {
    this._scale.value = value;
  }

  get scale() {
    return this._scale.value;
  }

  set offsetX(value: number) {
    this._offsetX.value = value;
  }

  get offsetX() {
    return this._offsetX.value;
  }

  set offsetY(value: number) {
    this._offsetY.value = value;
  }

  get offsetY() {
    return this._offsetY.value;
  }

  constructor(
    lastView: LastView,
    currView: CurrView,
    activeLink: Ref<boolean>,
    activeElem: Ref<boolean>,
    scale: Ref<number>,
    offsetX: Ref<number>,
    offsetY: Ref<number>
  ) {
    this._lastView = lastView;
    this._currView = currView;
    this._activeLink = activeLink;
    this._activeElem = activeElem;
    this._scale = scale;
    this._offsetX = offsetX;
    this._offsetY = offsetY;
  }

  private loadLinkTools() {
    this.lastView.model?.removeClickedTools(this.lastView.view);
    // @ts-ignore
    this.currView.model.addClickedTools(this.currView);
    this.lastView = {
      // @ts-ignore
      model: this.currView.model,
      view: this.currView as dia.LinkView
    };
  }

  private unloadLinkTools() {
    this.lastView.model?.removeClickedTools(this.lastView.view);
    this.lastView = {
      model: null,
      view: null
    };
  }

  private loadElementTools() {
    this.lastView.model?.removeClickedTools(this.lastView.view);
    // @ts-ignore
    this.currView.model.addClickedTools(this.currView);
    this.lastView = {
      // @ts-ignore
      model: this.currView.model,
      view: this.currView as dia.ElementView
    };
  }

  private unloadElementTools() {
    this.lastView.model?.removeClickedTools(this.lastView.view);
    this.lastView = {
      model: null,
      view: null
    };
  }

  /**
   * 当鼠标点击 Link 时
   *
   * @param view
   */
  public onPointerClickLink(view: dia.LinkView) {
    this.currView = view;
    this.activeLink = true;
    this.activeElem = false;
    this.loadLinkTools();
  }

  /**
   * 当鼠标点击元素时
   *
   * @param view
   */
  public onPointerClickElement(view: dia.ElementView) {
    this.currView = view;
    this.activeLink = false;
    this.activeElem = true;
    this.loadElementTools();
  }

  /**
   * 点击空白页时
   */
  public onPointerClickBlank() {
    this.activeElem = false;
    this.activeLink = false;
    this.unloadLinkTools();
    this.unloadElementTools();
  }

  /**
   * 当鼠标轮滚时
   *
   * @param event
   * @param paper
   */
  public onMousewheelBlank(event: any, paper: dia.Paper) {
    event.preventDefault(); // 防止浏览器默认滚动行为
    const delta = event.originalEvent.deltaY;

    if (event.ctrlKey) {
      if (delta > 0) {
        // 向下滚动，缩小画布
        this.scale -= this.scaleFactor;
        if (this.scale < 0.2) {
          this.scale = 0.2;
        }
      } else {
        this.scale += this.scaleFactor;
        if (this.scale > 2) {
          this.scale = 2;
        }
      }

      paper.scale(this.scale, this.scale);
    } else if (event.shiftKey) {
      if (delta > 0) {
        // 向下滚动，向右移动画布
        this.offsetX += this.scrollFactor;
      } else {
        this.offsetX -= this.scrollFactor;
      }
    } else {
      if (delta > 0) {
        // 向上滚动，向上移动画布
        this.offsetY -= this.scrollFactor;
      } else {
        this.offsetY += this.scrollFactor;
      }
    }

    paper.setOrigin(this.offsetX, this.offsetY); // 重置画布的原点
  }

  /**
   * 双击时元素时
   *
   * @param view
   * @param input
   */
  public onPointerDoubleClickElement(
    view: dia.LinkView | dia.ElementView,
    input: HTMLInputElement
  ) {
    // @ts-ignore
    const { model } = view;
    if (model?.upgradeLabelText) {
      model.upgradeLabelText(view, input);
    }
  }

  /**
   * 快捷键组合事件
   *
   * @param shortcuts
   */
  public onKeydownWithShortcutKey(shortcuts: { ctrlWithSKey: Function }) {
    document.addEventListener("keydown", event => {
      if (event.ctrlKey) {
        if (event.key === "s") {
          event.preventDefault();
          shortcuts.ctrlWithSKey();
        }
      }

      if (event.key === "Backspace" || event.key === "Delete") {
        // @ts-ignore
        if (this._currView.value?.model) {
          this._activeElem.value = false;
          this._activeLink.value = false;
          // @ts-ignore
          this._currView.value.model.remove();
        }
      }
    });
  }
}
