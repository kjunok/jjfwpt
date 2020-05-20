<template>
  <Card class="nav-menu" id="navMenuAutoHight" :style="{height: clientHeight+'px'}"
        style="overflow: auto;overflow-x: hidden;padding: 0px; border: none;border-bottom: white 1px solid;">
  <Menu accordion class="jjkj" active-name="1-2" theme="light" style="margin-right: -16px;"
        :style="{background: '#fff'}" width="auto">
    <ul v-if="menuFold" style="list-style: none;margin-top: 0px;">
       <li v-for="(item,index) in menuData"
           style="border-bottom: 1px solid rgba(0,0,0,0.16); padding: 12px 6px; margin-left: -11px; width: 40px;text-align: center;">
         <span @mouseover="menuFoldFun(item,index)" >
            <AutoIcon :type="item.icon" size="25" :STYLE="{}"/>
         </span>
       </li>
    </ul>
    <span v-if="!menuFold" v-for="item in menuData" >
        <menu-item v-if="item.children.length==0"
                   :key="item.id"
                   :name="'Menu'+item.id"
                   @click.native="toPage(item.viewPath)" style="padding-right: 0px;">
              <AutoIcon size="25" :type="item.icon"></AutoIcon>
              <span style="margin-left: 10px;">{{item.name}}</span>
        </menu-item>
        <Submenu v-if="item.children.length>0"  ref="navMenu"
                  :key="item.id"
                  :name="'pMenu'+item.id">
            <template slot="title">
              <AutoIcon size="25" :type="item.icon"></AutoIcon>
              <span style="margin-left: 10px;">{{item.name}}</span>
            </template>
            <menu-item v-for="cItem in item.children"
                       :key="cItem.id"
                       :name="'cMenu'+cItem.id"
                       @click.native="toPage(cItem.viewPath)" style="padding-right: 0px;">
              <AutoIcon size="25" :type="cItem.icon"></AutoIcon>
              <span style="margin-left: 10px;">{{cItem.name}}</span>
            </menu-item>
    </Submenu>
    </span>
  </Menu>
  </Card>
</template>
<script>
    import ReqUtil from "../../assets/script/utils/ReqUtil";
    import CommonUtil from "../../assets/script/utils/CommonUtil";
    import API from "../../assets/script/API";
    import AutoIcon from "../common/AutoIcon";
    export default {
      components: {AutoIcon},
      props:["menuFold"],
      name: "NavMenu",
      data(){
        return {
          clientHeight:CommonUtil.ClientHeight(85),
          menuData:[]
        }
      },
      methods:{
        toPage:CommonUtil.ToPage,
        menuFoldFun(item,index){
          let _this = this;
          setTimeout(function () {
            _this.$emit("NavMenuListen", function (parent) {
              parent.secondMenu={
                show:true,
                top:(51*index)+75,
                data:item
              };
            })
          },300)
        }
      },
      created () {
        let _this = this;
        CommonUtil.RegHeightChangeFun("NavMenu",function (height) {
          _this.clientHeight = height -85;
        });
      },
      destroyed(){
        CommonUtil.UnRegHeightChangeFun("NavMenu");
      },
      watch:{
        menuFold:function (a,b) {
        }
      },
      mounted () {
        let _this = this;
        ReqUtil.doPost(API.menuService.searchMenuTreeNavList.path)
          .setSuccessFunction(function (result) {
            if(result.success){
              _this.menuData = result.data;
            }
          })
          .setErrorFunction(function (e) {

          })
          .execute();
      }
    }
</script>

<style >
.nav-menu .ivu-card-body{
  padding-top: 0px!important;
}
</style>
