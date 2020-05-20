<template>
  <div>
    <Card style="border-radius: 0px;">
      <div style="width: 1000px;margin: auto;height: 30px;padding: 0px;min-width:1000px;">
        <Row>
          <Col span="2" style="text-align: right">
            <img src="../assets/img/logo.png" style="width: 30px; height: 30px;"/>
          </Col>
          <Col span="4">
            <span style="font-size: 20px;color: white;display: block;line-height: 30px;margin-left: 10px;">关联账号</span>
          </Col>
        </Row>
      </div>
    </Card>
    <br/> <br/>
    <Card style="width: 900px;margin: auto;">
      <Tabs style="color:white;font-weight: bold;height: 400px">

        <TabPane  label="关联手机号" >
          <br/> <br/>
          <Form ref="cascadePhoneForm"  :model="cascadePhoneForm.data" :rules="cascadePhoneForm.ruleValidate" style="width: 500px;margin: auto;">
            <div v-if="showPrise" style="color: #ff7708;font-size: 14px;">注册失败，请重试</div>
            <FormItem label="" prop="phoneNumber" >
              <Input class="input-big" v-model="cascadePhoneForm.data.phoneNumber" placeholder="请输入您的手机号码" style="height: 45px;">
                <Icon type="ios-phone-portrait" size="26" slot="prefix" style="line-height: 40px; margin-left: 15px;margin-top: 2px;color: white;"/>
              </Input>
            </FormItem>
            <FormItem  >

              <Row>
                <Col span="18">
                  <FormItem prop="phoneCode" >
                    <Input class="input-big" v-model="cascadePhoneForm.data.phoneCode" placeholder="请输入短信验证码">
                      <Icon type="ios-key-outline" size="26" slot="prefix" style="line-height: 40px; margin-left: 15px;margin-top: 2px;color: white;"/>
                    </Input>
                  </FormItem>
                </Col>
                <Col span="6">
                  <Button type="primary" :disabled="cascadePhoneForm.SMSBtn" @click="sendSMSMsg()" style="height: 45px;float: right;">获取验证码{{cascadePhoneForm.vCodeTime}}</Button>
                </Col>
              </Row>
            </FormItem>
            <FormItem prop="readAgree">
              <Checkbox v-model="cascadePhoneForm.data.readAgree" style="height: 30px;width: 100%;color: lightblue" >我已阅读并同意
                <router-link to="/register">《服务协议》</router-link>和
                <router-link to="/register">《隐私政策》</router-link>
              </Checkbox>
            </FormItem>
            <FormItem>
              <Button style="width: 100%;height:45px" type="success" @click="cascadePhone('cascadePhoneForm')">确定</Button>
            </FormItem>
          </Form>
        </TabPane>


        <TabPane label="关联IWant账号">
          <br/> <br/>
          <Form ref="cascadeAccountForm"  :model="cascadeAccountForm.data" :rules="cascadeAccountForm.ruleValidate"  style="width: 500px;margin: auto;">
            <FormItem label="" prop="account" >
              <Input class="input-big" v-model="cascadeAccountForm.data.account" placeholder="账号/邮箱/手机号" style="height: 45px;">
                <Icon type="ios-person-outline" size="26" slot="prefix" style="line-height: 40px; margin-left: 15px;margin-top: 2px;color: white;"/>
              </Input>
            </FormItem>
            <FormItem >
              <FormItem prop="password" >
                <Input class="input-big" type="password" v-model="cascadeAccountForm.data.password" placeholder="请输入密码">
                  <Icon type="ios-unlock-outline" size="26" slot="prefix" style="line-height: 40px; margin-left: 15px;margin-top: 2px;color: white;"/>
                </Input>
              </FormItem>
            </FormItem>
            <FormItem >
                <router-link style="float: right" to="/register">忘记密码?</router-link>
            </FormItem>
            <FormItem>
              <Button style="width: 100%;height:45px" type="success" @click="cascadeAccount('cascadeAccountForm')">登录</Button>
            </FormItem>
          </Form>
        </TabPane>
      </Tabs>
    </Card> {{$route.params}}
  </div>

</template>

<script>
  import md5 from "md5"
  export default {
    data () {
      const validatePhoneNumber = (rule, value, callback) => {
        if (value === '') {
          callback(new Error("手机号码不能为空！"));
        } else {
          this.cascadPhoneForm.SMSBtn=true
          if(!(/^1(3|4|5|6|7|8|9)\d{9}$/.test(value))){
            callback(new Error('手机号码有误，请重填'));
          }else {
            this.cascadPhoneForm.SMSBtn=false
            callback()
          }
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
        cascadePhoneForm: {
          SMSBtn:true,
          vCodeTime:60,
          ruleValidate:{
            phoneNumber:[{validator: validatePhoneNumber, trigger: 'blur' } ],
            phoneCode:[{ required: true, message: '短信验证码不能为空', trigger: 'blur' }],
            readAgree: [ { validator: validateReadAgree, trigger: 'blur' }]
          },
          data:{
            phoneNumber: "",
            phoneCode:"",
            readAgree:false
          }
        },
        cascadeAccountForm: {
          ruleValidate:{
            account: [{ required: true, message: '账号不能为空', trigger: 'blur' }],
            password:[{ required: true, message: '密码不能为空', trigger: 'blur' },
              {type: 'string', min: 6, message: '至少有6位密码', trigger: 'blur'}
            ]
          },
          data:{
            account: "",
            password:""
          }
        }
      }
    },
    methods: {
      vCodeTime(){

      },
      sendSMSMsg(){
          this.$util.req("/api/security/sms/send")
            .setJsonDataStr({
              phoneNumber:this.cascadePhoneForm.data.phoneNumber
          }).go()
      },
      cascadePhone (name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.$util.req("/api/security/user/cascadePhone")
              .setJsonDataStr(this.$jq.extend({},this.cascadeAccountForm.data,this.$route.params))
              .setSuccessfn(function (result) {

              })
              .go(this)
          } else {
            //this.$Message.error('Fail!');
          }
        })
      },
      cascadeAccount(name){
        const that =this;
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.$util.req("/api/security/user/cascadeAccount")
              .setJsonDataStr(this.$jq.extend({},this.cascadeAccountForm.data,this.$route.params,{
                password:md5(this.cascadeAccountForm.data.password)
              }))
              .setSuccessfn(function (result) {
                 if(result.code===1000){
                   sessionStorage.setItem("token",result.data.token)
                   delete result.data.token
                   sessionStorage.setItem("userinfo",JSON.stringify(result.data))
                   that.$store.commit("setUserInfo",{id:result.data.id})
                   that.$util.dynamicRouters(that,function(routers){
                     that.$router.addRoutes(routers)
                     that.$router.push({path:"/index/indexHome"})
                   })
                 }else if(result.code===1001){

                 }
              })
              .go(this)
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

