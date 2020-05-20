<template>
  <Drawer
    v-model="show"
    draggable
    :closable="false"
    :mask-closable="false"
    width="800">

    <div class="ivu-drawer-header">
      <Icon size="25" type="md-close" class="drawer-close-icon" @click="close()"/>
      <div class="ivu-drawer-header-inner">用户</div>
    </div>
    <Form :model="roleForm.data" :rules="roleForm.validate" :label-width="80" style="margin-top: 20px;">
      <FormItem label="名称" label-position="left" prop="name">
        <Input class="" v-model="roleForm.data.name"
               placeholder=""/>
      </FormItem>
      <FormItem label="代码" label-position="left" prop="code">
        <Input class="" v-model="roleForm.data.code"
               placeholder=""/>
      </FormItem>
      <FormItem label="图标" label-position="left" prop="icon">
        <Input class="" v-model="roleForm.data.icon"
               placeholder=""/>
      </FormItem>
    </Form>
    <div class="drawer-footer">
      <Button @click="close()" style="margin-right: 8px;background-image: linear-gradient(-15deg, #ff754c,#7af0f6, #f62e45);">取消</Button>
      <Button @click="api_saveOrUpdateRoleInfo()" type="primary" style="background-image: linear-gradient(-15deg, #987cff,#7af0f6, #8593f6);" >提交</Button>
    </div>
  </Drawer>
</template>

<script>
  import CommonUtil from "../../assets/script/utils/CommonUtil";
  import ReqUtil from "../../assets/script/utils/ReqUtil";
  import API from "../../assets/script/API";
  import TRoleInfo from "../../assets/script/modal/TRoleInfo";

  export default {
    name: "RoleForm",
    props:['show',"handle"],
    data(){
      return{
        clientHeight:CommonUtil.ClientHeight(100),
        roleForm:{
          data:TRoleInfo.form.saveOrUpdateUser.columns,
          validate:TRoleInfo.form.saveOrUpdateUser.validate,
          isView:false
        }
      }
    },
    watch:{
      show:function (value) {
        if(value){
          this.api_searchRoleById()
        }
      }
    },
    methods:{
      close(){
        this.$emit("RoleFormListen", function (parent) {
          parent.roleForm=false;
          parent.api_loadRoleInfoInfoList();
        })
      },
      saveOrUpdateSuccess(){
        this.$emit("RoleFormListen", function (parent) {
          parent.api_loadRoleInfoInfoList();
        })
      },
      api_saveOrUpdateUser(){
        let _this = this;
        ReqUtil.doPost(API.userService.saveOrUpdateUser.path)
          .setData(this.roleForm.data)
          .setSuccessFunction(function (result) {
            if(result.success){
              _this.saveOrUpdateSuccess();
              _this.close();
            }
          })
          .isRawBody()
          .execute()
      },
      api_searchRoleById(){
        let _this = this;
        ReqUtil.doGet(API.roleService.searchRoleById.path)
          .setData({id:_this.handle.data.id})
          .setSuccessFunction(function (result) {
            if(result.success){
              _this.roleForm.data=result.data;
            }
          })
          .execute();
      },
      api_saveOrUpdateRoleInfo(){
        let _this = this;
        ReqUtil.doPost(API.roleService.saveOrUpdateRoleInfo.path)
          .setData(_this.roleForm.data)
          .setSuccessFunction(function (result) {
            if(result.success){
              _this.close();
            }
          })
          .isRawBody()
          .execute();
      }
    }
  }
</script>

<style scoped>

</style>
