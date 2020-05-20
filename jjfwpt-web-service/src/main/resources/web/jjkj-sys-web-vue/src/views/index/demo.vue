<template>
  <div>
    <Explain :content="explain"></Explain>
    <Card class="view-card-body" style="margin-left:0px;" :style="{height: clientHeight+'px'}">

    </Card>
  </div>
</template>
<script>
  import Explain from "../../components/common/Explain";
  import CommonUtil from "../../assets/script/utils/CommonUtil";
  export default {
    name: "demo",
    components: {Explain},
    data(){
      return {
        explain:"用户管理",
        clientHeight:CommonUtil.ClientHeight(150)
      }
    },
    created() {
      let _this =this;
      CommonUtil.RegHeightChangeFun("demo",function (height) {
        _this.clientHeight=height-150;
      });
    },
    destroyed(){
      CommonUtil.UnRegHeightChangeFun("demo");
    },
  }
</script>
