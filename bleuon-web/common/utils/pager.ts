/**
 * @description 分页工具
 * @author zheng
 * @since 2023/10/25
 */

/**
 * 分页
 *
 * @param list 数组
 * @param currPage 当前页
 * @param pageSize 一页数量
 * @returns
 */
export function paginate(list: Ref<any[]>, currPage: Ref<number>, pageSize: Ref<number>) {
  return computed(() => {
    const start = (currPage.value - 1) * pageSize.value;
    const end = start + pageSize.value;
    return list.value.slice(start, end);
  });
}
