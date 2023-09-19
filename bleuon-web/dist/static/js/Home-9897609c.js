import{g as w,u as g,h as k,r as h,o as I,c as $,a as e,b as c,w as v,e as r,i as p,n as m,p as y,f as C,_ as V,j as f,k as E}from"./mainapp-6c55996c.js";import{E as x}from"./el-button-768dad0d.js";import{_ as M}from"./bleuon-icon-ae86a734.js";import{E as R}from"./el-input-466863cc.js";import"./el-tag-9b913af1.js";import{E as B,a as S}from"./el-select-d647e59f.js";import"./el-popper-b9e8093e.js";import{M as N}from"./MenuAvatar-9025dc70.js";import"./event-e06a23af.js";import"./isNil-c75b1b34.js";import"./api-user-bd4feb95.js";const o=s=>(y("data-v-ca155c63"),s=s(),C(),s),U={class:"menu h-100vh flow-auto px-5 b-r-1 b-r-solid b-r-#e4e4e4 bg-white"},z={class:"menu__header"},F=o(()=>e("div",{class:"mt-5"},[e("img",{src:M,class:"h-15 w-40 object-cover"})],-1)),T={class:"mt-5 relative"},j=o(()=>e("div",null,null,-1)),H=o(()=>e("div",null,"流程图",-1)),q=[j,H],A=o(()=>e("div",{class:"item"},[e("div"),e("div",null,"思维导图")],-1)),D=o(()=>e("div",{class:"item"},[e("div"),e("div",null,"UML")],-1)),L={class:"menu__body mt-10 pb-5 b-b-1 b-b-solid b-b-#e4e4e4"},O=o(()=>e("div",{class:"mr-2 i-tabler-clock-hour-3"},null,-1)),G=o(()=>e("div",{class:"mr-2 i-tabler-folder"},null,-1)),J=o(()=>e("div",{class:"mr-2 i-tabler-share"},null,-1)),K=o(()=>e("div",{class:"mr-2 i-tabler-star"},null,-1)),P={class:"mt-5 pb-5 b-b-1 b-b-solid b-b-#e4e4e4"},Q=o(()=>e("div",{class:"mr-2 i-tabler-template"},null,-1)),W=o(()=>e("div",{class:"mr-2 i-tabler-friends"},null,-1)),X={class:"mt-5 pb-5"},Y=o(()=>e("div",{class:"mr-2 i-tabler-trash-x"},null,-1)),Z=w({__name:"Menu",props:{activeItem:{type:String,required:!0}},emits:["update:activeItem"],setup(s,{emit:l}){const d=g();function i(u){l("update:activeItem",u);const t=d.resolve({name:u});d.push({name:t.name,path:t.path})}k(()=>{const u=d.currentRoute.value.name;l("update:activeItem",u)});const n=h(!1);function _(){d.push("/diagraming/123")}return(u,t)=>{const b=x;return I(),$("div",U,[e("div",z,[F,e("div",T,[c(b,{onClick:t[0]||(t[0]=a=>n.value=!p(n)),class:"w-100%",type:"primary"},{default:v(()=>[r("＋新建")]),_:1}),e("div",{class:m(["options__panel select-none p-2 z-2 absolute top-12 left-0 w-65 bg-white rd-2",p(n)?"block":"hidden"])},[e("div",{class:"f-c-b flex-wrap flex-gap-5"},[e("div",{class:"item",onClick:_},q),A,D])],2)])]),e("div",L,[e("div",{class:m(["menu__item f-c-s",{active:s.activeItem=="auth-history"}]),onClick:t[1]||(t[1]=a=>i("auth-history"))},[O,r(" 最近文件 ")],2),e("div",{class:m(["menu__item f-c-s",{active:s.activeItem=="auth-diagrams"}]),onClick:t[2]||(t[2]=a=>i("auth-diagrams"))},[G,r(" 我的文件 ")],2),e("div",{class:m(["menu__item f-c-s",{active:s.activeItem=="auth-shares"}]),onClick:t[3]||(t[3]=a=>i("auth-shares"))},[J,r(" 我的分享 ")],2),e("div",{class:m(["menu__item f-c-s",{active:s.activeItem=="auth-stars"}]),onClick:t[4]||(t[4]=a=>i("auth-stars"))},[K,r(" 我的收藏 ")],2)]),e("div",P,[e("div",{class:m(["menu__item f-c-s",{active:s.activeItem=="auth-templates"}]),onClick:t[5]||(t[5]=a=>i("auth-templates"))},[Q,r(" 模板社区 ")],2),e("div",{class:m(["menu__item f-c-s",{active:s.activeItem=="public-discussion"}]),onClick:t[6]||(t[6]=a=>i("public-discussion"))},[W,r(" 讨论社区 ")],2)]),e("div",X,[e("div",{class:m(["menu__item f-c-s",{active:s.activeItem=="auth-recycle"}]),onClick:t[7]||(t[7]=a=>i("auth-recycle"))},[Y,r(" 回收站 ")],2)])])}}});const ee=V(Z,[["__scopeId","data-v-ca155c63"]]),te={class:"header f-c-b mt-5 mb-10"},se={class:"w-120"},oe=e("div",{class:"i-tabler-search"},null,-1),ie={class:"f-c-c"},ae={class:"mr-5"},le=e("div",{class:"i-tabler-bell"},null,-1),ne=w({__name:"Header",setup(s){const l=h(""),d=h("file");return(i,n)=>{const _=B,u=S,t=R,b=x;return I(),$("div",te,[e("div",se,[c(t,{modelValue:p(l),"onUpdate:modelValue":n[1]||(n[1]=a=>f(l)?l.value=a:null),placeholder:"搜索文件/文件夹"},{suffix:v(()=>[oe]),prepend:v(()=>[c(u,{modelValue:p(d),"onUpdate:modelValue":n[0]||(n[0]=a=>f(d)?d.value=a:null),placeholder:"搜索的类型",style:{width:"115px"}},{default:v(()=>[c(_,{label:"文件",value:"file"}),c(_,{label:"文件夹",value:"folder"})]),_:1},8,["modelValue"])]),_:1},8,["modelValue"])]),e("div",ie,[e("div",ae,[c(b,{text:"",bg:"",size:"small"},{icon:v(()=>[le]),_:1})]),c(N)])])}}}),ce={class:"home f-s-c bg-#F6F7F8"},de={class:"content h-100vh flow-auto px-10 pb-10 w-83%"},$e=w({__name:"Home",setup(s){const l=h("auth-history");return(d,i)=>{const n=E("RouterView");return I(),$("div",ce,[c(ee,{class:"w-17%","active-item":p(l),"onUpdate:activeItem":i[0]||(i[0]=_=>f(l)?l.value=_:null)},null,8,["active-item"]),e("div",de,[c(ne),c(n)])])}}});export{$e as default};
//# sourceMappingURL=Home-9897609c.js.map
