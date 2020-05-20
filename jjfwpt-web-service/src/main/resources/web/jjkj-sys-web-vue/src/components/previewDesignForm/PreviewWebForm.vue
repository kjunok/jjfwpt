<template>
  <Drawer v-model="openWebForm"
          draggable
          :closable="false"
          :mask-closable="false"
          width="60%">
    <div class="ivu-drawer-header">
      <Icon size="25" type="md-close" class="drawer-close-icon" @click="close()"/>
      <div class="ivu-drawer-header-inner">菜单</div>
    </div>
    <Form :label-width="200" :model="viewFormData" :style="{height:clientHeight-65+'px'}" style="overflow-y: auto;marginTop:10px;">
      <FormMaker :options="viewFormOption"
                 :form-data="viewFormData"
                 :single-data="false" />
    </Form>
    <div class="drawer-footer">
      <Button @click="close()" style="margin-right: 8px;background-image: linear-gradient(-15deg, #ff754c,#7af0f6, #f62e45);">取消</Button>
      <Button @click="saveOrUpdate()" type="primary" style="background-image: linear-gradient(-15deg, #987cff,#7af0f6, #8593f6);" >提交</Button>
    </div>
  </Drawer>
</template>

<script>
    import CommonUtil from "../../assets/script/utils/CommonUtil";
    import MsgUtil from "../../assets/script/utils/MsgUtil";

    export default {
        props:["openWebForm","viewFormOption","viewFormData"],
        name: "previewWebForm",
        data(){
          return {
            clientHeight:CommonUtil.ClientHeight()
          }
        },
        methods:{
          close(){
            this.$emit("PreviewWebFormListen", function (parent) {
              parent.openWebForm=false;
            })
          },
          saveOrUpdate(){
            MsgUtil.MODAL_INFO({
              data:this.viewFormData,
              options:this.viewFormOption
            })
          }
        }
    }
</script>

<style scoped>

</style>
