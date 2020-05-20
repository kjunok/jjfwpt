<template>
  <div style="background: url('../../static/bg/skin-greenish.jpg') no-repeat;background-size: 100% 100%;" :style="{height:clientHeight+'px'}">
    <Card shadow style="border-radius: 0px;margin: 0px;background: #ffffff26;">
      <div style="width: 1000px;height: 30px;padding: 0px;min-width:1000px;">
        <Row>
          <Col span="2" style="text-align: right">
            <img src="../assets/img/logo.png" style="width: 30px; height: 30px;"/>
          </Col>
          <Col span="4">
            <span style="font-size: 25px;color: white;display: block;line-height: 30px;margin-left: 10px;font-family: 'Microsoft YaHei UI'">叮呤江湖</span>
          </Col>
        </Row>
      </div>
    </Card>
    <div style="height: 700px;width: 1200px; margin: 50px auto;display: block;position: relative">
      <img src="../assets/img/av.png" style=" position: absolute;left: 0px;"/>
      <Card shadow class="custom-card" style="position:absolute; right: 20px; top: 10%;padding: 5px;">
        <Divider orientation="left" >账号登录</Divider>
        <div v-if="showPrise.show" style="color: #ff7708;font-size: 12px;">{{showPrise.msg}}</div>
        <Form ref="formInline" :model="formInline" :rules="ruleInline"  style="margin-top:10px;">
          <FormItem prop="user">
            <Input class="input-big" type="text" v-model="formInline.user" placeholder="账号/邮箱/手机号">
              <Icon type="ios-person-outline" style="font-weight: bold" size="20" slot="prefix"/>
            </Input>
          </FormItem>
          <FormItem prop="password">
            <Input class="input-big"  type="password" v-model="formInline.password" placeholder="密码">
              <Icon type="ios-unlock-outline" style="font-weight: bold" size="20" slot="prefix" />
            </Input>
          </FormItem>
          <FormItem>
            <CheckboxGroup v-model="formInline.checkbox">
              <Checkbox style="height: 30px;width: 130px;color: #2629ff" label="记住我"></Checkbox>
            </CheckboxGroup>
          </FormItem>
          <FormItem>
            <router-link to="/register" style="margin-left: 0px;color: rgba(107,169,255,0.79)">免费注册>></router-link>
            <router-link to="/forget" style="margin-left: 20px;color: rgba(107,169,255,0.79)">忘记密码?</router-link>
            <Button style="float: right;width: 100px" type="primary" @click="handleSubmit('formInline')">登录</Button>
          </FormItem>
        </Form>
        <Divider style="color: white">其他登录方式</Divider>
        <div style="width: 100%;text-align: center">
          <span id="weixin_login" @click="tlogin('wixin')" class="login-icon weixin"></span>&nbsp;&nbsp;
          <span @click="tlogin('qq')" class="login-icon qq"></span>&nbsp;&nbsp;
          <span @click="tlogin('qq')" class="login-icon weibo"></span>&nbsp;&nbsp;
        </div>
      </Card>
    </div>
  </div>
</template>
<script>
  import md5 from "md5"
  import API from "../assets/script/API";
  import ReqUtil from "../assets/script/utils/ReqUtil";
  import CommonUtil from "../assets/script/utils/CommonUtil";
  import routerUtil from "../router/routerUtil";
  export default {
    data () {
      return {
        clientHeight:CommonUtil.ClientHeight(),
        showPrise:{
          show:false,
          msg:""
        },
        formInline: {
          user: '',
          password: ''
        },
        ruleInline: {
          user: [
            {required: true, message: '请输入账号', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {type: 'string', min: 6, message: '至少有6位密码', trigger: 'blur'}
          ]
        }
      }
    },
    created:function(){
      this.$emit("ListenContent",function (parent) {
        // parent.headerRegister=true;
        // parent.headerLogin=true
      })
    },
    methods: {
      handleSubmit(name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            const that=this;
            sessionStorage.clear();
            ReqUtil.doPost(API.auth.loginByPwd.path,this)
              .setData({account:this.formInline.user,
                  password:md5(this.formInline.password)})
              .setSuccessFunction(function (result) {
                if(!result.success){
                  that.showPrise.show=true;
                  that.showPrise.msg=result.msg;
                }else{
                  sessionStorage.setItem("access_token",result.data.access_token);
                  delete result.data.access_token;
                  routerUtil.DynamicRouters((router)=>{
                      that.$router.addRoutes(router);
                      console.log(router)
                      that.$router.push({
                        path: '/index/indexHome'
                      })
                  });
                }
              })
              .isRawBody()
              .execute()
          } else {
            //this.$Message.error('Fail!')
          }
        })
      },
      tlogin(type){
          this.$util.tlogin(type)
      }
    }
  }
</script>
<style scoped>
  .custom-input-size-40 > input{
    height: 43px;
    font-size: 17px;
  }
  .custom-card{
    width:350px;  padding: 10px;
    border-left:0px;
    border-right: 0px;
    /*box-shadow:0 0 12px #f9fcff inset;*/
  }
</style>
