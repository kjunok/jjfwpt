<template >
  <div class="layout" :style="{height:clientHeight+'px',background: '#eaedef'}"  >
    <layout style="background: url('../../../static/bg/skin-greenish.jpg') no-repeat;background-size: 100% 100%;">
      <MainHeader style="margin: 0px;z-index: 1" v-on:HeaderListen="HeaderListen"></MainHeader>
      <layout style="">
        <sider :style="{background: '#eaedef'}"  style="margin-left: 0px;" :width="(menuFold?70:280)">
          <NavMenu v-on:NavMenuListen="NavMenuListen" v-bind:menuFold="menuFold"></NavMenu>
        </sider>
        <Layout>
          <Content :style="{padding: '1px 4px', minHeight: '280px', background: '#eaedef'}">
            <router-view />
          </Content>
        </Layout>
      </layout>
    </layout>
    <Card dis-hover @mouseout.native="menuClose()" v-show="menuFold&&secondMenu.show&&secondMenu.data.children.length==0" :style="{top:secondMenu.top+'px'}"
          style="position: absolute; top: 0px;left: 53px;z-index: 10000;padding: 0px!important;" >
      <span @mouseout="menuClose()" style="color: darkgreen">{{secondMenu.data.description}}</span>
    </Card>
    <div :name="secondMenu.data.id" @mouseout="menuClose()"
         v-if="menuFold && secondMenu.show && secondMenu.data.children.length>0"
         :style="{top : secondMenu.top +'px'}"
         class="animated_opacity ivu-select-dropdown"
         style="transform-origin: center top;position: absolute;clear: both; width: 150px; top: 0px;left: 68px;z-index: 100;padding:0px;">
        <DropdownItem
          v-for="(item,index) in secondMenu.data.children" :key="item.id"
          @mouseover.native="secondMenuOpen({show:true,top:(index*43)+secondMenu.top,data:item})"
          @click.native="toPage(item.viewPath)"
          style="line-height: 25px;">
          <AutoIcon size="20" :type="item.icon"></AutoIcon>
          {{item.name}}
          <Icon v-if="item.children.length>0" style="float: right;margin-right: -10px;margin-top: 5px;"  type="ios-arrow-forward"></Icon>
        </DropdownItem>
    </div>
    <div @mouseout="menuClose()"
         v-show="menuFold && thirdMenu.show && thirdMenu.data.children.length>0"
         class="animated_opacity  ivu-select-dropdown"
         :style="{top : thirdMenu.top +'px'}"
         style="transform-origin: center top; position: absolute;clear: both; width: 150px; top: 0px;left: 223px;z-index: 10000;padding:0px;">
        <DropdownItem
          v-for="(item,index) in thirdMenu.data.children" :key="item.id"
          @mouseover.native="thirdMenuOpen()"
          @click.native="toPage(item.viewPath)"
          style="line-height: 25px;">
          <AutoIcon size="20" :type="item.icon"></AutoIcon>
          {{item.name}}
        </DropdownItem>
    </div>
  </div>
</template>

<script>
  import $ from "jquery";
  import MainHeader from "./MainHeader";
  import NavMenu from "./NavMenu";
  import CommonUtil from "../../assets/script/utils/CommonUtil";
  import AutoIcon from "../common/AutoIcon";
  export default {
    name: "MainLayout",
    components: {AutoIcon, NavMenu, MainHeader},
    data () {
      return {
        secondMenu:{
          show:false,
          top:0,
          data:{}
        },
        thirdMenu:{
          show:false,
          top:0,
          data:{}
        },
        menuFold: localStorage.getItem("menuFold")==="true"?true:false,
        secondMenuIsShow:false,
        thirdMenuIsShow:false,
        clientHeight: CommonUtil.ClientHeight()
      }
    },
    created() {
      let _this  = this;
      CommonUtil.RegHeightChangeFun("MainLayout",function (height) {
        _this.clientHeight=height;
      });
    },
    destroyed(){
      CommonUtil.UnRegHeightChangeFun("MainLayout");
    },
    mounted () {
      if(!sessionStorage.getItem("access_token")){
        this.$router.push({path: '/login'});
      }
    },
    watch: {

    },
    methods : {
      toPage:CommonUtil.ToPage,
      menuClose(){
        this.thirdMenuIsShow=false;
        this.secondMenuIsShow=false;
        let _this = this;
          setTimeout(function () {
            if(!_this.thirdMenuIsShow&&!_this.secondMenuIsShow){
              _this.thirdMenu.show=false;
              _this.secondMenu.show=false;
            }
          },300);
      },
      secondMenuOpen(thirdMenu){
        this.secondMenuIsShow=true;
        this.secondMenu.show=true;
        this.thirdMenu=thirdMenu;
      },
      thirdMenuOpen(){
        this.secondMenuIsShow=true;
        this.secondMenu.show=true;
        this.thirdMenuIsShow=true;
        this.thirdMenu.show=true;
      },
      NavMenuListen(fun){
        fun(this);
      },
      HeaderListen(fun){
        fun(this);
      },
      changeFixed(fullHeight) {                        //动态修改样式
        this.$refs.content.style.height = fullHeight ;
      }
    }
  }
</script>

<style>
  .child-menu{
    cursor: pointer;
    line-height: 30px;
  }
  .child-menu:hover{
    color: #2d8cf0;
  }
  .app-sidebar__heading {
    padding-left: -5px;
    text-transform: uppercase;
    font-size: 15px;
    margin: .75rem 0;
    font-weight: bold;
    color: #3f6ad8;
    white-space: nowrap;
    position: relative;
  }
  /*.ivu-menu-vertical .ivu-menu-item:hover, .ivu-menu-vertical .ivu-menu-submenu-title:hover {*/
  /*  color: white;*/
  /*  background: #3f6ad8;*/
  /*}*/
  .ivu-menu-vertical .ivu-menu-item, .ivu-menu-vertical .ivu-menu-submenu-title{
    padding: 12px 2px!important;
  }
  .jjkj .ivu-menu-submenu .ivu-menu-item{
    padding-left: 33px!important;
  }

  .jjkj .ivu-menu-submenu-has-parent-submenu{
    padding-left:27px!important;
  }
  .jjkj .ivu-menu-submenu-has-parent-submenu .ivu-menu-item{
    padding-left:46px!important;
  }
  .ivu-menu-submenu-has-parent-submenu .ivu-menu-submenu-title {
    font-weight: normal;
    font-size: 13px;
  }
  .ivu-menu-submenu-title{
    font-size: 13px;
  }
  .ivu-menu-opened .ivu-menu-submenu-title{
    font-weight: bold;
  }
  /*.ivu-menu-item-selected{*/
  /*  color: white!important;*/
  /*  background: #3f6ad8!important;*/
  /*}*/
  .ivu-menu-opened ul:before {
    content: '';
    height: 100%;
    opacity: .3;
    width: 2px;
    background: #2d8cf0;
    left: 12px;
    top: 0;
    z-index: 100;
    position: absolute;
    border-radius: 15px;

  }

  .slider_container{
    color: #333;
    transition: width .28s;
    width: 180px !important;
    height: 100%;
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    z-index: 1001;
    background-color: rgb(48, 65, 86);
  }
</style>
