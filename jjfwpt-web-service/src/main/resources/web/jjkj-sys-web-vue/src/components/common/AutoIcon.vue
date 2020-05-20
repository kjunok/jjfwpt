<template>
  <span style="cursor: pointer;">
    <img v-if="type.indexOf('/')>-1" :src="SRC" style="margin-bottom: -5px;cursor: pointer;" class="ivu-icon" :style="STYLE" :width="size+'px'" :height="size+'px'"/>
    <Icon v-if="type.indexOf('/')==-1" :size="size" :style="STYLE" :type="type" style="cursor: pointer;margin: auto;"/>
  </span>
</template>
<script>
    import CommonUtil from "../../assets/script/utils/CommonUtil";
    export default {
        name: "AutoIcon",
        props:["STYLE","size","type"],
        data(){
          return{
            SRC: CommonUtil.GetFilePath(this.type)
          }
        }
    }
</script>

<style scoped>

</style>
