<template>
  <Card style="border-radius: 0px;">
    <ul style="width: 269px;height: 30px;padding: 0px;min-width:255px;list-style: none;">
        <li style="float: left;width: 30px;">
          <img src="../../assets/img/logo.png" style="width: 30px; height: 30px;display: block;margin: auto"/>
        </li>
        <li v-if="!menuFold" style="float: left">
          <span style="font-size: 25px;display: block;line-height: 30px;margin-left: 10px;">
            JJKJ<span style="font-size: 15px">系统管理平台</span>
          </span>
        </li>
        <!--折叠后-->
        <li v-if="menuFold" :style="{float:(!menuFold?'right':'left')}">
          <Icon size="20" type="fa-outdent" @click.native="fold(false)"
                style="margin-top: 5px;transform: rotate(180deg); -webkit-transform: rotate(180deg);margin-left: 12px;cursor: pointer" >
          </Icon>
        </li>
       <!--折叠前-->
        <li v-if="!menuFold" :style="{float:(!menuFold?'right':'left')}">
          <Icon size="20" type="fa-outdent" @click.native="fold(true)"
                style="margin-top: 6px;cursor: pointer"
                @click="fold">
          </Icon>
        </li>
    </ul>
    <ul style="padding: 0px;list-style: none;float: right;margin-top: -37px;">
      <li style="float: left;width:75px;">
        <Dropdown >
          <a href="javascript:void(0)">
            <Avatar style="border-radius:50px;height: 43px;width: 43px;border: 2px solid white" src=""/>
            <Icon type="ios-arrow-down"></Icon>
          </a>
          <DropdownMenu slot="list">
            <DropdownItem @click.native="logOut">注销</DropdownItem>
          </DropdownMenu>
        </Dropdown>
      </li>
      <li style="float: left;width: 100px;">
        admin<br/>超级管理员
      </li>
      <li style="float: left;width: 50px;">
        <Icon size="43" type="ios-mail"/>
      </li>
    </ul>
  </Card>
</template>
<script>
    import ReqUtil from "../../assets/script/utils/ReqUtil";
    import API from "../../assets/script/API";

    export default {
        name: "header",
      data(){
          return {
            menuFold : localStorage.getItem("menuFold")==="true"?true:false
          }
      },
      watch:{
        menuFold(value){
          localStorage.setItem("menuFold",value);
        }
      },
      methods:{
        logOut(){
          let _this = this;
          ReqUtil.doPost(API.auth.logOut.path)
            .setSuccessFunction(function (result) {
              if(result.success){
                sessionStorage.clear();
                _this.$router.push({path: '/login'});
              }
            })
            .setErrorFunction(function () {

            })
            .execute()
        },
        fold(fold){
          this.menuFold=fold;
          this.$emit("HeaderListen", function (parent) {
            parent.menuFold=fold;
            parent.secondMenu.show=false;
          })
        }
      }
    }
</script>

<style scoped>

</style>
