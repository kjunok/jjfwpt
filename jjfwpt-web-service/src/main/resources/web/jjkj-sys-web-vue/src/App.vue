<template>
  <div id="app">
    <router-view />
  </div>
</template>
<script>
  export default {
    data() {
      return {
        bgimg:"bg3.jpg",
        headerRegister:true,
        headerLogin:true,
        headerUserInfo:false,
        clientHeight:`${document.documentElement.clientHeight}`,
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
    mounted() {
      if(!sessionStorage.getItem("access_token")){
        this.$router.push({
          path: '/login'
        })
      }
      // 获取浏览器可视区域高度
      this.clientHeight = `${document.documentElement.clientHeight}`;
      //document.body.clientWidth;
      //console.log(self.clientHeight);
      window.onresize = function temp() {
        this.clientHeight = `${document.documentElement.clientHeight}`;
      };
    },
    watch: {
      // 如果 `clientHeight` 发生改变，这个函数就会运行
      clientHeight: function () {
        this.changeFixed(this.clientHeight)
      }
    },
    methods: {
      listenMethod:function (fun) {
        fun(this)
      },
      changeFixed(clientHeight) {                        //动态修改样式
        console.log(clientHeight);
        this.$refs.content.style.height = clientHeight ;
      },
      logout() {
        const that=this;
        this.$util.req("/api/security/user/logout")
          .setSuccessfn(function (result) {
            if(result.code===1000){
              sessionStorage.removeItem("userinfo")
              sessionStorage.removeItem("token")
              that.headerLogin=true
              that.headerUserInfo=false
              that.headerRegister=true
              that.$router.push({path:"/login"})
            }
          })
          .setErrorfn(function (result) {
            sessionStorage.removeItem("userinfo")
            sessionStorage.removeItem("token")
            that.headerLogin=true
            that.headerUserInfo=false
            that.headerRegister=true
            that.$router.push({path:"/login"})
          })
          .go()
      }
    }
  }
</script>
<style scoped>

</style>
