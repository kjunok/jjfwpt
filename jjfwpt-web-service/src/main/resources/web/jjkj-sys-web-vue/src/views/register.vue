<template>
  <div>
    <Card style="border-radius: 0px;">
      <div style="width: 1000px;margin: auto;height: 30px;padding: 0px;min-width:1000px;">
        <Row>
          <Col span="2" style="text-align: right">
            <img src="../assets/img/logo.png" style="width: 30px; height: 30px;"/>
          </Col>
          <Col span="4">
            <span style="font-size: 20px;color: white;display: block;line-height: 30px;margin-left: 10px;">注册账号</span>
          </Col>
        </Row>
      </div>
    </Card>
    <br/>
    <Card class="custom-card" v-if="registerCard" :style="{margin:'4% auto'}" style="width:450px" >
      <router-link  to="/login" style="font-size: 12px;border-radius: 3px;height: 40px;color: rgba(11, 255, 144, 0.79)">已有账号直接登录>></router-link>
      <Form ref="registerForm"  :model="registerForm" :rules="ruleValidate" style="margin-top: 20px">
        <div v-if="showPrise" style="color: #ff7708;font-size: 14px;">注册失败，请重试</div>
        <FormItem label="" prop="mobilePhone" >
          <Input class="input-big" v-model="registerForm.mobilePhone" placeholder="请输入您的手机号码">
            <Icon type="ios-phone-portrait" size="26" slot="prefix" style="line-height: 40px; margin-left: 15px;margin-top: 2px;color: white;"/>
          </Input>
        </FormItem>
        <FormItem>
          <Row>
            <Col span="16">
              <FormItem prop="msgCode" >
                <Input class="input-big" v-model="registerForm.msgCode" placeholder="请输入短信验证码">
                  <Icon type="ios-key-outline" size="26" slot="prefix" style="line-height: 40px; margin-left: 15px;margin-top: 2px;color: white;"/>
                </Input>
              </FormItem>
            </Col>
            <Col span="1" style="text-align: center">&nbsp;</Col>
            <Col span="7">
              <Button type="primary" style="height: 45px;float: right;">获取短信验证码</Button>
            </Col>
          </Row>
        </FormItem>
        <FormItem prop="username" >
          <Input class="input-big" v-model="registerForm.username" placeholder="请设置您的账号名">
            <Icon type="ios-person-outline" size="26" slot="prefix" style="line-height: 40px; margin-left: 15px;margin-top: 2px;color: white"/>
          </Input>
        </FormItem>
        <FormItem prop="password">
          <Input class="input-big" v-model="registerForm.password" type="password" placeholder="请设置您的密码">
            <Icon type="ios-unlock-outline" size="26" slot="prefix" style="line-height: 40px; margin-left: 15px;margin-top: 2px;color: white;"/>
          </Input>
        </FormItem>
        <FormItem prop="rePassword">
          <Input class="input-big" v-model="registerForm.rePassword" type="password" placeholder="请再次输入密码">
            <Icon type="ios-lock" size="26" slot="prefix" style="line-height: 40px; margin-left: 15px;margin-top: 2px;color: white;"/>
          </Input>
        </FormItem>
        <FormItem prop="readAgree">
            <Checkbox v-model="registerForm.readAgree" style="height: 30px;width: 100%;color: lightblue" >我已阅读并同意
              <router-link to="/register">《服务协议》</router-link>和
              <router-link to="/register">《隐私政策》</router-link>
            </Checkbox>
        </FormItem>
        <FormItem>
          <Button style="width: 100%;height:45px" type="success" @click="register('registerForm')">确认注册</Button>
        </FormItem>
      </Form>
    </Card>
    <Card class="custom-card" v-if="registerSuccess" :style="{margin:'4% auto'}" style="background: rgba(0,0,0,0.54);width:650px" >
      注册成功 <router-link to="/login">去登录</router-link>
    </Card>
  </div>
</template>
<script>
  import md5 from "md5"
  export default {
    data () {
      const validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入你的密码'));
        }else {
          callback();
        }
      };
      const validatePassCheck = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入你的密码'));
        } else if (value !== this.registerForm.password) {
          callback(new Error('两次密码不一致!'));
        } else {
          callback();
        }
      };
      const validateReadAgree = (rule, value, callback) => {
        if (value === false) {
          callback(new Error('是否同意服务协议和隐私政策'));
        }else {
          callback();
        }
      };
      return {
        showPrise:false,
        registerSuccess:false,
        registerCard:true,
        registerForm: {
          username: 'wj2',
          mobilePhone:'18619740826',
          msgCode:'6666',
          password:'123456',
          rePassword:'123456',
          readAgree:false
        },
        ruleValidate: {
          mobilePhone: [
            { required: true, message: '手机号码不能为空', trigger: 'blur' }
          ],
          msgCode:[
            { required: true, message: '短信验证码不能为空', trigger: 'blur' }
          ],
          username: [
            { required: true, message: '账户名不能为空', trigger: 'blur' }
          ],
          password: [
            { validator: validatePass, trigger: 'blur' }
          ],
          rePassword: [
            { validator: validatePassCheck, trigger: 'blur' }
          ],
          readAgree: [
            { validator: validateReadAgree, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      register (name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            const that=this;
            that.registerForm.rePassword=null;
            that.registerForm.password=md5(that.registerForm.password);
            this.$util.req("/api/security/user/register")
              .setJsonDataStr(that.registerForm)
              .setSuccessfn(result => {
                console.log(result)
                if(result.code===1001){
                  that.showPrise=true;
                }else{
                  this.registerCard=false;
                  this.registerSuccess=true;
                  //that.$router.push({path:"/"})
                }
              })
              .setErrorfn(result => {
                that.$util.errorMsg(that,result['responseJSON'])
            }).go()
          } else {
            //this.$Message.error('Fail!');
          }
        })
      },
      handleReset (name) {
        this.$refs[name].resetFields();
      }
    }
  }
</script>
<style>

</style>
