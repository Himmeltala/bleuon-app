import{d as v,r as n,o as b,c as f,a as t,n as i,i as d,t as c,p as m,g as u,e as _}from"./mainapp-3c0275ba.js";import{_ as p}from"./_plugin-vue_export-helper-96a6bebf.js";const r=e=>(m("data-v-0ecabf98"),e=e(),u(),e),h={class:"file relative"},x=r(()=>t("div",{class:"i-tabler-dots text-white"},null,-1)),g=[x],y=_('<div class="text-0.9rem f-c-s" data-v-0ecabf98><div class="i-tabler-eye mr-2" data-v-0ecabf98></div> 预览 </div><div class="text-0.9rem f-c-s" data-v-0ecabf98><div class="i-tabler-edit mr-2" data-v-0ecabf98></div> 重命名 </div><div class="text-0.9rem f-c-s" data-v-0ecabf98><div class="i-tabler-share mr-2" data-v-0ecabf98></div> 分享 </div><div class="text-0.9rem f-c-s" data-v-0ecabf98><div class="i-tabler-send mr-2" data-v-0ecabf98></div> 发布和公开 </div><div class="text-0.9rem f-c-s" data-v-0ecabf98><div class="i-tabler-download mr-2" data-v-0ecabf98></div> 下载 </div><div class="text-0.9rem f-c-s" data-v-0ecabf98><div class="i-tabler-copy mr-2" data-v-0ecabf98></div> 复制 </div><div class="text-0.9rem f-c-s" data-v-0ecabf98><div class="i-tabler-arrows-move mr-2" data-v-0ecabf98></div> 移动 </div><div class="text-0.9rem f-c-s" data-v-0ecabf98><div class="i-tabler-trash-x mr-2" data-v-0ecabf98></div> 删除 </div>',8),w=[y],I={class:"image h-50 rd-2"},S=["src"],k={class:"f-c-s flex-nowrap mt-4 w-100%"},q=r(()=>t("div",{class:"mr-2 i-tabler-chart-bubble text-primary"},null,-1)),N={class:"text-0.9rem text-ellipsis line-clamp-1"},B={class:"text-b text-0.9rem mt-2"},C=v({__name:"File",props:{disabled:{type:Boolean,required:!0},index:{type:Number,required:!0},lastIndex:{type:Number,required:!0},fileImage:{type:String,required:!0},fileName:{type:String,required:!0},updateDate:{type:String,required:!0}},emits:["update:lastIndex"],setup(e,{emit:l}){const s=e,a=n(1);function o(){s.disabled||(a.value=1),a.value>0?a.value=0:a.value++,l("update:lastIndex",s.index)}return(F,D)=>(b(),f("div",h,[t("div",{class:i(["options",e.disabled&&d(a)<=0?"block":"hidden"])},[t("div",{onClick:o,class:"options__icon f-c-c absolute top-2 right-2 cursor-pointer w-10 h-6 rd-2 bg-#383838cc"},g)],2),t("div",{class:i(["options__panel select-none p-1 z-2 absolute top-10 right-2 w-80% bg-white rd-2",e.disabled&&d(a)<=0?"block":"hidden"])},w,2),t("div",I,[t("img",{class:"w-100% h-100% rd-2 object-cover cursor-pointer",src:e.fileImage},null,8,S)]),t("div",k,[q,t("div",N,c(e.fileName),1)]),t("div",B,"更新于 "+c(e.updateDate),1)]))}});const j=p(C,[["__scopeId","data-v-0ecabf98"]]);export{j as F};
//# sourceMappingURL=File-f8f380b2.js.map
