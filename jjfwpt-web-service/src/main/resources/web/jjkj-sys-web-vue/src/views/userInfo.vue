<template>

</template>
<script>
  export default {
    data () {
      return {
        value2: 0
      }
    },
    mounted: function () {
      this.$util.req("/api/user/getUser")
        .setJsonDataStr({username:"wj2"})
        .setSuccessfn(function(result){
          console.log(result);
        }).go()
    },
    methods:{
      test:function () {

      }
    }
  }
</script>
