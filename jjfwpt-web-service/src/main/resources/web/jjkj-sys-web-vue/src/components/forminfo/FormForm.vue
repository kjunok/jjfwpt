<template>
  <Drawer
    v-model="show"
    draggable
    :closable="false"
    :mask-closable="false"
    width="800">
    <div class="ivu-drawer-header">
      <Icon size="25" type="md-close" class="drawer-close-icon" @click="close()"/>
      <div class="ivu-drawer-header-inner">基本信息</div>
    </div>
    <Form :label-width="100" :model="formForm.data" :style="{height:clientHeight-65+'px'}" style="overflow-y: auto;marginTop:10px;">
      <FormMaker :options="options"
                 :form-data="formForm.data"
                 :single-data="false" />
    </Form>
      <div class="drawer-footer">
        <Button @click="close()" style="margin-right: 8px;background-image: linear-gradient(-15deg, #ff754c,#7af0f6, #f62e45);">取消</Button>
        <Button @click="api_saveOrUpdateForm()" type="primary" style="background-image: linear-gradient(-15deg, #987cff,#7af0f6, #8593f6);" >提交</Button>
      </div>
  </Drawer>
</template>

<script>
    import TForminfo from "../../assets/script/modal/TForminfo";
    import CommonUtil from "../../assets/script/utils/CommonUtil";
    import ReqUtil from "../../assets/script/utils/ReqUtil";
    import API from "../../assets/script/API";
    import FormUtil from "../../assets/script/utils/FormUtil";

    export default {
      name: "FormForm",
      props:['show',"handle"],
      data(){
          return{
            clientHeight:CommonUtil.ClientHeight(100),
            formForm:{
              data:TForminfo.form.saveOrUpdate.columns,
              validate:TForminfo.form.saveOrUpdate.validate,
              isView:false
            },
            options:{
              name:{
                key:"name",
                icon:"form-input",
                show:true,
                type:"input",
                label:"表单名称",
                props:{
                  placeholder:"请输入表单名称"
                },
                style:{
                  width:"100%"
                },
                events:{},
                index:0,
                validators:[
                  {
                    validator:"isNotNull",
                    msg:"不能为空"
                  }
                ]
              },
              code:{
                key:"code",
                icon:"form-input",
                show:true,
                type:"input",
                label:"表单标识",
                props:{
                  placeholder:"请输入表单标识"
                },
                style:{
                  width:"100%"
                },
                events:{},
                index:1,
                validators:[
                  {
                    validator:"isNotNull",
                    msg:"不能为空"
                  }
                ]
              },
              enable:{
                key:"enable",
                icon:"ios-switch",
                show:true,
                type:"switch",
                label:"启用?",
                props:{
                  placeholder:""
                },
                value:"true",
                events:{},
                children:[
                  {
                    type:"span",
                    slot:"open",
                    value:"true",
                    label:"开",
                    def:true
                  },
                  {
                    type:"span",
                    slot:"close",
                    value:"false",
                    label:"关",
                    def:false
                  }
                ],
                index:2
              },
              description:{
                key:"description",
                icon:"ios-create-outline",
                show:true,
                type:"input",
                label:"描述",
                props:{
                  type:"textarea",
                  placeholder:""
                },
                style:{
                  width:"100%"
                },
                events:{},
                index:2
              }
            },
            formData:[]
          }
      },
      created:function(){
        this.formForm.data=FormUtil.EmptyFormData(this.options)
      },
      watch:{
        show:function (value) {
          if(!value){
            return;
          }
          switch (this.handle.type) {
            case "add":
              this.formForm.data=TForminfo.form.saveOrUpdateUser.columns;
              break;
            case "view":
              this.api_searchFormById();
              break;
            case "edit":
              this.api_searchFormById();
              break;
          }
        }
      },
      methods:{
        close(){
          this.$emit("FormFormListen", function (parent) {
            parent.formForm=false;
          })
        },
        saveOrUpdateSuccess(){
          this.$emit("FormFormListen", function (parent) {
            parent.formForm=false;
            parent.api_searchFormPagesByParam();
          })
        },
        api_saveOrUpdateForm(){
          let _this = this;
          ReqUtil.doPost(API.formService.saveOrUpdateForm.path)
            .setData(this.formForm.data)
            .setSuccessFunction(function (result) {
                if(result.success){
                  _this.saveOrUpdateSuccess();
                  _this.close();
                }
            })
            .isRawBody()
            .execute()
        },
        api_searchFormById(){
          let _this = this;
          ReqUtil.doPost(API.formService.searchFormById.path)
            .setData({id:_this.handle.data.id})
            .setSuccessFunction(function (result) {
              if(result.success){
                  _this.formForm.data=result.data;
              }
            })
            .isFormBody()
            .execute();
        }
      }
    }
</script>

<style scoped>

</style>
