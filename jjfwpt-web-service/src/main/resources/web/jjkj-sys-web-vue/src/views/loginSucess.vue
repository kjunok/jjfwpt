<template>
  <div>
    <Spin fix style="background-color:transparent;color: rgba(11, 255, 144, 0.79);font-size: 20px;">
      <Icon type="ios-loading" size=18 class="demo-spin-icon-load"></Icon>
      <div>稍等一下</div>
    </Spin>
  </div>
</template>
<script>
  import md5 from "md5"
  export default {
    data () {
      return {
        test:''
      }
    },
    created:function(){
        console.log(this.$util.getUrlParam())
        const that=this;
        this.$util.req("/api/qq/oauth2.0/me")
          .setData({
            access_token:that.$util.getUrlParam().access_token,
          })
          .setDataType("text")
          .setSuccessfn(function (data) {
            eval("that."+data)
          }).go()
    },
    methods:{
      callback:function (obj) {
        const that = this
        const urlParam=that.$util.getUrlParam();
        this.$util.req("/api/security/user/tloginSucess")
          .setJsonDataStr({
            type:"qq",
            access_token:urlParam.access_token,
            oauth_consumer_key:obj['client_id'],
            openid:obj['openid']
          })
          .setSuccessfn(function (result) {
            //新用户跳转到绑定页面
            if(result.data.user_type&&result.data.user_type=="new_user"){
              that.$router.push({name:"bindPhone",params:that.$jq.extend({
                  type:"qq",
                  oauth_consumer_key:obj['client_id']
                },result.data)})
            }else{
                //老用户直接登录
              sessionStorage.setItem("token",result.data.token)
              delete result.data.token
              sessionStorage.setItem("userinfo",JSON.stringify(result.data))
              that.$store.commit("setUserInfo",{id:result.data.id})
              that.$util.dynamicRouters(that,function(routers){
                that.$router.addRoutes(routers)
                if(sessionStorage.getItem("history")!=null){
                    that.$router.push({path:sessionStorage.getItem("history")})
                }else {
                   that.$router.push({path:"/index/indexHome"})
                }
              })
            }
            console.log(result)
          }).go()
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
  }
  .demo-spin-icon-load{
    animation: ani-demo-spin 1s linear infinite;
  }
  .ivu-icon-ios-loading:before{
    font-size: 95px;
  }
</style>
