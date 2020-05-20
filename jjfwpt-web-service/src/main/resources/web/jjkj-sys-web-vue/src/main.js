// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import 'babel-polyfill'
import App from './App'
import routerUtil from './router/routerUtil'
import router from "./router"
import Router from 'vue-router'
import ViewUI from 'view-design';
import JsonViewer from "vue-json-viewer"
import vuedraggable from "vuedraggable"
import FormMaker from './components/formMaker'
import CommonUtil from "./assets/script/utils/CommonUtil";
import { codemirror } from 'vue-codemirror'

Vue.use(codemirror)
//CSS
import 'view-design/dist/styles/iview.css';
// import '../my-theme/dist/dark/iview.css';
import './assets/css/custom.css'
import './assets/css/icon.css'
import './assets/css/font-awesome.css'
import 'codemirror/lib/codemirror.css'


Vue.use(ViewUI);
Vue.use(Router);
Vue.use(JsonViewer);
Vue.use(vuedraggable);
Vue.use(FormMaker);
Vue.config.productionTip = false;
Vue.prototype.clientHeight=CommonUtil.ClientHeight();
/* 窗体高度改变响应组件 */
Vue.prototype.heightChangeFun={};
if(sessionStorage.router){
 let routerData = JSON.parse(sessionStorage.router);
  router.addRoutes([routerUtil.pushRouters(routerData)]);
}

router.beforeEach((to, from, next) => {
  ViewUI.LoadingBar.start();
    if (to.matched.length ===0) {  //如果未匹配到路由
      from.name ? next({ name:from.name }) : next('/404');   //如果上级也未匹配到路由则跳转登录页面，如果上级能匹配到则转上级路由
    } else {
      next();    //如果匹配到正确跳转
    }
});
router.afterEach(route => {
  ViewUI.LoadingBar.finish();
});
new Vue({
  el: '#app',
  router:router,
  components: { App },
  template: '<App/>'
});
/* 窗体高度监控 */
window.addEventListener('resize', function() {
  if (!this.timer) {
    this.timer = true;
    setTimeout(function () {
      this.timer = false;
      for(let key in Vue.prototype.heightChangeFun){
        let item = Vue.prototype.heightChangeFun[key];
        if(item.enabled){
          try{
            item.fun(document.documentElement.clientHeight);
          }catch (e) {}
        }
      }
    }, 500)
  }
});


