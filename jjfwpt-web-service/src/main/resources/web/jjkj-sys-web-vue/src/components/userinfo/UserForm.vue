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
      <Form :model="userForm.data" :rules="userForm.validate" :label-width="80" style="margin-top: 20px;">
        <FormItem label="类别" label-position="left" prop="userType">
          <RadioGroup v-model="userForm.data.userType">
            <Radio v-for="item in userForm.dict_user_type" :key="'dict_user_type'+item.value" :disabled="userForm.isView" :label="item.value+''" :value="item.value+''" border>{{item.label}}</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="账号" label-position="left" prop="account">
          <Input class="" v-model="userForm.data.account"
                 placeholder=""/>
        </FormItem>
        <FormItem label="姓名" label-position="left" prop="userName">
          <Input class="" v-model="userForm.data.userName"
                 placeholder=""/>
        </FormItem>
        <FormItem label="昵称" label-position="left" prop="avatar">
          <Input class="" v-model="userForm.data.avatar"
                 placeholder=""/>
        </FormItem>
        <FormItem label="生日" label-position="left" prop="birthDay">
          <DatePicker  type="date" format="yyyy-MM-dd"
                      v-model="userForm.data.birthDay"
                      :start-date="new Date()"
                      placement="bottom-end" placeholder="请选择"></DatePicker>
        </FormItem>
        <FormItem label="性别" label-position="left" prop="sex">
          <RadioGroup v-model="userForm.data.sex">
            <Radio v-for="item in userForm.dict_sex"
                   :key="'dict_sex'+item.value"
                   :disabled="userForm.isView"
                   :label="item.value"
                   :value="item.value" border>{{item.label}}</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="邮箱" label-position="left" prop="email">
          <Input class="" v-model="userForm.data.email"
                 placeholder=""/>
        </FormItem>
        <FormItem label="手机号" label-position="left" prop="phone">
          <Input class="" v-model="userForm.data.phone"
                 placeholder=""/>
        </FormItem>
        <FormItem label="状态" label-position="left" prop="enable">
          <i-switch :disabled="userForm.isView" size="large" v-model="userForm.data.enable">
            <span slot="open">启用</span>
            <span slot="close">禁用</span>
          </i-switch>
        </FormItem>
      </Form>
      <div class="drawer-footer">
        <Button @click="close()" style="margin-right: 8px;background-image: linear-gradient(-15deg, #ff754c,#7af0f6, #f62e45);">取消</Button>
        <Button @click="api_saveOrUpdateUser()" type="primary" style="background-image: linear-gradient(-15deg, #987cff,#7af0f6, #8593f6);" >提交</Button>
      </div>
  </Drawer>
</template>

<script>
    import TUserInfo from "../../assets/script/modal/TUserInfo";
    import CommonUtil from "../../assets/script/utils/CommonUtil";
    import ReqUtil from "../../assets/script/utils/ReqUtil";
    import API from "../../assets/script/API";
    import DICT from "../../assets/script/DICT";

    export default {
      name: "UserForm",
      props:['show',"handle"],
      data(){
          return{
            clientHeight:CommonUtil.ClientHeight(100),
            userForm:{
              dict_user_type:DICT.DICT_USER_TYPE,
              dict_sex:DICT.DICT_SEX,
              data:TUserInfo.form.saveOrUpdateUser.columns,
              validate:TUserInfo.form.saveOrUpdateUser.validate,
              isView:false
            }
          }
      },
      watch:{
        show:function (value) {
          if(!value){
            return;
          }
          switch (this.handle.type) {
            case "add":
              this.userForm.data=TUserInfo.form.saveOrUpdateUser.columns;
              break;
            case "view":
              this.api_searchSingleUserById();
              break;
            case "edit":
              this.api_searchSingleUserById();
              break;
          }
        }
      },
      methods:{
        close(){
          this.$emit("UserFormListen", function (parent) {
            parent.userForm=false;
            parent.api_loadUserInfoInfoList();
          })
        },
        saveOrUpdateSuccess(){
          this.$emit("UserFormListen", function (parent) {
            parent.api_loadRoleInfoInfoList();
          })
        },
        api_saveOrUpdateUser(){
          let _this = this;
          ReqUtil.doPost(API.userService.saveOrUpdateUser.path)
            .setData(this.userForm.data)
            .setSuccessFunction(function (result) {
                if(result.success){
                  _this.saveOrUpdateSuccess();
                  _this.close();
                }
            })
            .isRawBody()
            .execute()
        },
        api_searchSingleUserById(){
          let _this = this;
          ReqUtil.doPost(API.userService.searchSingleUserByParam.path)
            .setData({id:_this.handle.data.id})
            .setSuccessFunction(function (result) {
              if(result.success){
                  _this.userForm.data=result.data;
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
