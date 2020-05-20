<template>
  <body>
  <Drawer
    v-model="show"
    draggable
    :closable="false"
    :mask-closable="false"
    width="800">
    <div class="ivu-drawer-header">
      <Icon size="25" type="md-close" class="drawer-close-icon" @click="close()"/>
      <div class="ivu-drawer-header-inner">菜单</div>
    </div>
    <Form ref="menuInfoForm"
          :model="menuForm.data"
          :rules="menuForm.validate"
          enctype="multipart/form-data"
          :label-width="80" style="margin-top: 20px;">
      <FormItem label="类型" label-position="left" prop="type">
        <RadioGroup v-model="menuForm.data.type" :onselect="menuTypeSelected()">
          <Radio :disabled="menuForm.isView" v-if="menuForm.menuType.type_1" label="1" value="1" border>功能模块</Radio>
          <Radio :disabled="menuForm.isView" v-if="menuForm.menuType.type_2" label="2" value="2" border>模块菜单</Radio>
          <Radio :disabled="menuForm.isView" v-if="menuForm.menuType.type_3" label="3" value="3" border>页面权限</Radio>
        </RadioGroup>
      </FormItem>
      <FormItem label="名称" label-position="left" prop="name">
        <Input :disabled="menuForm.isView" class="" v-model="menuForm.data.name" placeholder="请输入菜单名称"/>
      </FormItem>
      <FormItem v-if="menuForm.showField.viewPath" label="页面路径" label-position="left">
        <Input :disabled="menuForm.isView" class="" v-model="menuForm.data.viewPath" placeholder="视图路径"></Input>
      </FormItem>
      <FormItem v-if="menuForm.showField.groupName" label="分组名称" label-position="left">
        <Input :disabled="menuForm.isView" class="" v-model="menuForm.data.groupName" placeholder="分组名称">
        </Input>
      </FormItem>
      <FormItem v-if="menuForm.showField.code" label="权限代码" label-position="left">
        <Input :disabled="menuForm.isView" class="" v-model="menuForm.data.code" placeholder="权限代码">
        </Input>
      </FormItem>
      <FormItem label="选择图标" label-position="left">
        <div style="height: 80px;width: 80px;display: block;border: 1px solid rgba(189,255,236,0.6);cursor: pointer;border-radius: 10px">
          <div style="position: relative;width: 260px">
            <img v-if="menuForm.data.icon.indexOf('blob:')>-1" :src="menuForm.data.icon" class="ivu-icon" style="border-radius: 10px" width="80" height="80"/>
            <img v-if="menuForm.data.icon.indexOf('/')>-1&&menuForm.data.icon.indexOf('blob:')==-1"
                 :src="menuForm.data.iconPath" class="ivu-icon" style="border-radius: 10px" width="80" height="80"/>
            <Icon v-if="menuForm.data.icon.indexOf('/')==-1" size="80" :type="menuForm.data.icon" style=""/>
            <span  @click="iconSelect=true"
                  style="font-size:10px;width:70px;text-align:center;position: absolute;left: 85px;color: white;bottom:30px;background: #6660ff;line-height: 22px;border-radius: 5px">选择图标</span>
            <span style="position: absolute;width:70px;text-align:center;left: 85px;bottom: 0px;color: white; background: #ff5c4f;line-height: 22px;border-radius: 5px">
                  <Upload :disabled="menuForm.isView" action=""
                          :headers="uploadHeader"
                          :format="['jpg','jpeg','png']"
                          :on-success="uploadSuccess"
                          :on-error="uploadError"
                          :before-upload="beforeUpload"
                          :max-size="948"
                          :show-upload-list="false">
                    上传图标
                  </Upload>
                </span>
            <span v-if="menuForm.uploadError" class="ivu-form-item-error-tip" style="margin-top: 5px;">超过文件大小限制,不能超过100KB!</span>
          </div>
        </div>
      </FormItem>
      <FormItem label="启用?" label-position="left">
        <i-switch :disabled="menuForm.isView" true-value="0" false-value="1" size="large" v-model="menuForm.data.enabled">
          <span slot="open">启用</span>
          <span slot="close">禁用</span>
        </i-switch>
      </FormItem>
      <FormItem label="排序" label-position="left">
        <InputNumber :disabled="menuForm.isView" :max="10000" style="width: 100%" :mix="1" v-model="menuForm.data.orderNum"></InputNumber>
      </FormItem>
      <FormItem label="描述" label-position="left">
        <Input :disabled="menuForm.isView" type="textarea" v-model="menuForm.data.description" :rows="5"/>
      </FormItem>
    </Form>
    <div class="drawer-footer">
      <Button @click="close()" style="margin-right: 8px;background-image: linear-gradient(-15deg, #ff754c,#7af0f6, #f62e45);">取消</Button>
      <Button @click="saveOrUpdate()" type="primary" style="background-image: linear-gradient(-15deg, #987cff,#7af0f6, #8593f6);" >提交</Button>
    </div>
  </Drawer>
  <IconSelect
    v-on:IconSelectListen="IconSelectListen"
    v-bind:iconSelect="iconSelect"></IconSelect>
  </body>
</template>

<script>
    import $ from "jquery";
    import TMenuInfo from "../../assets/script/modal/TMenuInfo";
    import ReqUtil from "../../assets/script/utils/ReqUtil";
    import IconSelect from "../../components/IconSelect";
    import CommonUtil from "../../assets/script/utils/CommonUtil";

    export default {
      name: "MenuFormEdit",
      components: {IconSelect},
      props:['show',"handle"],
      created(){
        CommonUtil.RegHeightChangeFun("MenuFormEdit",function (height) {

        })
      },
      data(){
        return {
          uploadHeader:{
            access_token:sessionStorage.getItem('access_token')
          },
          iconSelect:false,
          menuForm: {
            title: "添加",
            menuType:{
              type_1:false,
              type_2:false,
              type_3:false
            },
            isView:false,
            showField: {
              groupName: false,
              pid: false,
              viewPath: false
            },
            select: {
              pid: []
            },
            uploadError:false,
            clearData:TMenuInfo.form.saveOrUpdate.columns,
            data: TMenuInfo.form.saveOrUpdate.columns,
            validate:TMenuInfo.form.saveOrUpdate.validate,
            show: false
          }
        }
      },
      mounted(){
      },
      watch:{
        show:function () {
          this.menuForm.isView=false;
          this.menuForm.menuType.type_1=false;
          this.menuForm.menuType.type_2=false;
          this.menuForm.menuType.type_3=false;
          let menuData = this.handle.data;
          if(this.show){
            switch (this.handle.type) {
              case "view":
                this.menuForm.isView=true;
                if(menuData.type==="1"){
                  this.menuForm.menuType.type_1=true;
                }
                if(menuData.type==="2"){
                  this.menuForm.menuType.type_2=true;
                }
                if(menuData.type==="3"){
                  this.menuForm.menuType.type_3=true;
                }
                this.searchMenuById(menuData.id);
                break;
              case "add":
                if(menuData.type==="0"){
                  this.menuForm.menuType.type_1=true;
                }
                if(menuData.type==="1"){
                  this.menuForm.menuType.type_2=true;
                  this.menuForm.menuType.type_3=true;
                }
                if(menuData.type==="2"){
                  this.menuForm.menuType.type_2=true;
                  this.menuForm.menuType.type_3=true;
                }
                this.menuForm.data=TMenuInfo.form.saveOrUpdate.columns;
                break;
              case "update":
                if(menuData.type==="1"){
                  this.menuForm.menuType.type_1=true;
                }
                if(menuData.type==="2"){
                  this.menuForm.menuType.type_2=true;
                  if(menuData.children.length==0){
                    this.menuForm.menuType.type_3=true;
                  }
                }
                if(menuData.type==="3"){
                  this.menuForm.menuType.type_2=true;
                  this.menuForm.menuType.type_3=true;
                }
                this.searchMenuById(menuData.id);
                break;
            }
          }
        }
      },
      methods:{
        IconSelectListen(fun){
          fun(this);
        },
        menuTypeSelected(){
          //不使用groupName
          this.menuForm.showField.groupName=false;
          switch(this.menuForm.data.type){
            case "1":
              //this.menuForm.showField.groupName=true;
              this.menuForm.showField.code=false;
              break;
            case "2":
              //this.menuForm.showField.groupName=false;
              this.menuForm.showField.viewPath=true;
              this.menuForm.showField.code=false;
              break;
            case "3":
              //this.menuForm.showField.groupName=false;
              this.menuForm.showField.viewPath=false;
              this.menuForm.showField.code=true;
              break;
          }
        },
        uploadSuccess(response, file, fileList){
          let fileInfo = response.data;
          this.menuForm.data.icon=fileInfo[0]+"/"+fileInfo[1]
          console.log(response, file, fileList)
        },
        uploadError(response, file){
          let that = this;
          if(file.statusCode==="201"){
            that.$Modal.warning({
              title: "登录已过期",
              content: "请重新登录",
              width: 400,
              onOk: function () {
                sessionStorage.setItem("history", that.$route.path);
                that.$router.push({path: "/login"})
              }
            })
          }
        },
        beforeUpload(file){
          if(file.size<=100000){
            this.menuForm.uploadError=false;
            this.menuForm.data.iconFile=file;
            let imgURL = URL.createObjectURL(file);
            this.menuForm.data.icon=imgURL
          }else {
            this.menuForm.uploadError=true
          }
          return false
        },
        close(){
          this.$emit("MenuFormListen", function (parent) {
            parent.addOrUpdateShow=false;
          })
        },
        saveOrUpdateSuccess(){
          this.$emit("MenuFormListen", function (parent) {
            parent.loadMenuInfoList();
          })
        },
        saveOrUpdate(){
          let _this =this;
          let formData = new FormData();
          //添加新的菜单需要设置pid
          if(!_this.menuForm.data.id){
            _this.menuForm.data.pid=_this.handle.data.id;
          }
          formData.append("iconFile",_this.menuForm.data.iconFile);
          formData.append("tMenuInfo",JSON.stringify(_this.menuForm.data));
          ReqUtil.doPost("/api/menuService/addOrUpdateMenu")
            .setData(formData)
            .setSuccessFunction(function (result) {
              if (result.success) {
                _this.$Notice.success({
                  title: '操作成功',
                  desc: '菜单' + _this.menuForm.title + '成功'
                });
                _this.saveOrUpdateSuccess();
                _this.close();
              }
            })
            .isFileBody()
            .setErrorFunction(function (result) {
            })
            .execute();
        },
        searchMenuById(menuId){
          let _this = this;
          ReqUtil.doPost("/api/menuService/searchSingleMenuByParam")
            .setData({id:menuId})
            .setSuccessFunction(function (result) {
              if(result.success){
                let menuInfo = result.data;
                _this.menuForm.data = menuInfo;
                _this.menuForm.data.iconPath=CommonUtil.GetFilePath(_this.menuForm.data.icon)
              }
            })
            .isRawBody()
            .setErrorFunction(function () {
            })
            .execute();
        }
      }
    }
</script>
<style scoped>
</style>
