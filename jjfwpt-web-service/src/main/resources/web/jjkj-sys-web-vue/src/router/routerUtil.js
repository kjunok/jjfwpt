import Vue from 'vue'
import Router from 'vue-router'
import ReqUtil from "../assets/script/utils/ReqUtil";
import API from "../assets/script/API";
Vue.use(Router);
const sysDynamicRouters =    {
  path: '/',
  name: 'MainLayout',
  component: (resolve) => require(['../components/layout/MainLayout'], resolve),
  children:[{
      path: '/401',
      meta: {title: '登录超时'},
      component: (resolve) => require(['../views/error/401'], resolve)
    },
    {
      path: '/404',
      meta: {title: '无此页面'},
      component: (resolve) => require(['../views/error/404'], resolve)
    },
    {
      path: '/504',
      meta: {title: '服务器连接超时'},
      component: (resolve) => require(['../views/error/504'], resolve)
    }]
};
const pushRouters = function (routerData) {
  for (let i in routerData) {
    if (routerData[i].viewPath) {
      sysDynamicRouters.children.push({
        path: routerData[i].viewPath,
        meta: {title: routerData[i].name, requireAuth: true},
        component: (resolve) => require(['../views' + routerData[i].viewPath], resolve)
      })
    }
  }
  return sysDynamicRouters;
};
const DynamicRouters = function (callBack) {
  ReqUtil.doPost(API.menuService.routes.path)
    .setSuccessFunction(function (result) {
      if(result.success){
        let routerData = result.data;
        pushRouters(routerData);
        sessionStorage.setItem("router",JSON.stringify(result.data));
        callBack([sysDynamicRouters]);
      }
    })
    .setCompleteFunction(function (result) {
      if((result.status||result.code)===401){
        //window.location.href='/login'
      }
    })
    .execute();
};

export default {DynamicRouters,pushRouters}
