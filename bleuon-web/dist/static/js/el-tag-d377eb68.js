import{V as T,bB as w,d as v,Z as N,a6 as V,o as t,c as M,a as l,B as m,n as o,u as a,t as c,w as r,b as g,ab as k,au as b,a8 as C,z as y,a1 as h,aN as $,a2 as I,an as P}from"./mainapp-08e4e16e.js";import{a as F}from"./_plugin-vue_export-helper-5392fbfa.js";const K=T({type:{type:String,values:["success","info","warning","danger",""],default:""},closable:Boolean,disableTransitions:Boolean,hit:Boolean,color:{type:String,default:""},size:{type:String,values:w,default:""},effect:{type:String,values:["dark","light","plain"],default:"light"},round:Boolean}),Z={close:n=>n instanceof MouseEvent,click:n=>n instanceof MouseEvent},j=v({name:"ElTag"}),q=v({...j,props:K,emits:Z,setup(n,{emit:i}){const B=n,S=F(),s=N("tag"),u=V(()=>{const{type:e,hit:f,effect:_,closable:z,round:E}=B;return[s.b(),s.is("closable",z),s.m(e),s.m(S.value),s.m(_),s.is("hit",f),s.is("round",E)]}),p=e=>{i("close",e)},d=e=>{i("click",e)};return(e,f)=>e.disableTransitions?(t(),M("span",{key:0,class:o(a(u)),style:h({backgroundColor:e.color}),onClick:d},[l("span",{class:o(a(s).e("content"))},[m(e.$slots,"default")],2),e.closable?(t(),c(a(C),{key:0,class:o(a(s).e("close")),onClick:b(p,["stop"])},{default:r(()=>[g(a(k))]),_:1},8,["class","onClick"])):y("v-if",!0)],6)):(t(),c($,{key:1,name:`${a(s).namespace.value}-zoom-in-center`,appear:""},{default:r(()=>[l("span",{class:o(a(u)),style:h({backgroundColor:e.color}),onClick:d},[l("span",{class:o(a(s).e("content"))},[m(e.$slots,"default")],2),e.closable?(t(),c(a(C),{key:0,class:o(a(s).e("close")),onClick:b(p,["stop"])},{default:r(()=>[g(a(k))]),_:1},8,["class","onClick"])):y("v-if",!0)],6)]),_:3},8,["name"]))}});var A=I(q,[["__file","/home/runner/work/element-plus/element-plus/packages/components/tag/src/tag.vue"]]);const H=P(A);export{H as E,K as t};
//# sourceMappingURL=el-tag-d377eb68.js.map
