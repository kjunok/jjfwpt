<template>
  <Drawer
    v-model="show"
    draggable
    :closable="false"
    :mask-closable="false"
    width="800">

    <div class="ivu-drawer-header">
      <Icon size="25" type="md-close" class="drawer-close-icon" @click="close()"/>
      <div class="ivu-drawer-header-inner">添加角色用户--[{{handle.data.name}}]</div>
    </div>
    <Tabs value="roleUser">
      <TabPane label="角色用户" name="roleUser">
        <Row>
            <Form style="margin: 0px 0px -10px;">
              <FormItem>
                <Input class="searchInput" v-model="roleUserKeyword" style=""
                       @focusin.native="searchFieldShow=true"
                       @focusout.native="searchFieldShow=true"  placeholder="请输入查询关键字">
                  <Select v-model="roleUserSearchField" slot="prepend" style="width: 100px">
                    <Option v-for="item in roleUserSearchColumns" :key="item.value"  :value="item.value">{{item.label}} </Option>
                  </Select>
                  <Button slot="append" icon="ios-search" style="width: 70px;"  @click="api_searchRoleUserPageByParam(1)"></Button>
                </Input>
              </FormItem>
            </Form>
        </Row>
        <Table size="small" width="100%" :height="clientHeight-170" :columns="roleUserGridColumns" :data="roleUserGridData" border> </Table>
        <Card class="view-card-body" style="margin-left:0px;height: 40px;">
          <Page  style="margin: -12px;text-align: center" show-total show-elevator show-sizer transfer
                 :total="roleUserGridPageData.total"
                 :current="roleUserGridPageData.start"
                 :page-size="roleUserGridPageData.size"
                 :page-size-opts="[10, 20, 30, 40]"
                 on-change="changePageNum"
                 on-page-size-change="changePageSize"/>
        </Card>
      </TabPane>
      <TabPane label="添加" name="user">
        <Row>
          <Form style="margin: 0px 0px -10px;">
            <FormItem>
              <Input class="searchInput" v-model="userKeyword" style=""
                     @focusin.native="searchFieldShow=true"
                     @focusout.native="searchFieldShow=true"  placeholder="请输入查询关键字">
                <Select v-model="userSearchField" slot="prepend" style="width: 100px">
                  <Option v-for="item in userSearchColumns" :key="item.value"  :value="item.value">{{item.label}} </Option>
                </Select>
                <Button slot="append" icon="ios-search" style="width: 70px;"  @click="api_searchNotRoleUserPageByParam(1)"></Button>
              </Input>
            </FormItem>
          </Form>
        </Row>
        <Table size="small" width="100%" :height="clientHeight-170" :columns="userGridColumns" :data="userGridData" border> </Table>
        <Card class="view-card-body" style="margin-left:0px;height: 40px;">
          <Page  style="margin: -12px;text-align: center" show-total show-elevator show-sizer transfer
                 :total="userGridPageData.total"
                 :current="userGridPageData.start"
                 :page-size="userGridPageData.size"
                 :page-size-opts="[10, 20, 30, 40]"
                 on-change="changePageNum"
                 on-page-size-change="changePageSize"/>
        </Card>
      </TabPane>
    </Tabs>
    <div class="drawer-footer">
      <Button @click="close()" style="margin-right: 8px;background-image: linear-gradient(-15deg, #ff754c,#7af0f6, #f62e45);">取消</Button>
      <Button @click="api_saveOrUpdateRoleInfo()" type="primary" style="background-image: linear-gradient(-15deg, #987cff,#7af0f6, #8593f6);" >提交</Button>
    </div>
  </Drawer>
</template>

<script>
  import CommonUtil from "../../assets/script/utils/CommonUtil";
  import ReqUtil from "../../assets/script/utils/ReqUtil";
  import API from "../../assets/script/API";
  import TUserInfo from "../../assets/script/modal/TUserInfo";
  import MsgUtil from "../../assets/script/utils/MsgUtil";

  export default {
    name: "RoleUser",
    props:['show',"handle"],
    data(){
      return{
        clientHeight:CommonUtil.ClientHeight(100),
        userGridColumns:TUserInfo.gridColumns(this,"notRoleUser"),
        roleUserGridColumns:TUserInfo.gridColumns(this,"roleUser"),
        userGridData:[],
        roleUserGridData:[],
        userGridPageData: {
          total: 100,
          start: 0,
          size: 10
        },
        userSearchColumns: TUserInfo.searchColumns(),
        userKeyword:"",
        userSearchField: TUserInfo.searchColumns()[0].value,
        userSearchFieldShow:false,
        roleUserGridPageData: {
          total: 100,
          start: 0,
          size: 10
        },
        roleUserSearchColumns: TUserInfo.searchColumns(),
        roleUserKeyword:"",
        roleUserSearchField: TUserInfo.searchColumns()[0].value,
        roleUserSearchFieldShow:false,
      }
    },
    watch:{
      show:function (value) {
        if(value){
          this.api_searchRoleUserPageByParam(1);
          this.api_searchNotRoleUserPageByParam(1);
        }
      }
    },
    created() {
      let _this =this;
      /*
      * 窗体高度改变处理
      */
      CommonUtil.RegHeightChangeFun("RoleUser",function (height) {
        _this.clientHeight=height-100;
      });
      /*============================================================================*/
    },
    destroyed(){
      CommonUtil.UnRegHeightChangeFun("RoleUser");
    },
    methods:{
      close(){
        this.$emit("RoleUserListen", function (parent) {
          parent.roleUser=false;
        })
      },
      saveOrUpdateSuccess(){
        this.$emit("RoleUserListen", function (parent) {
          parent.api_loadRoleInfoInfoList();
        })
      },
      api_searchRoleUserPageByParam(start){
        let _this = this;
        let param ={};
        if(_this.roleUserKeyword){
          param.keyword=_this.roleUserKeyword;
        }
        if(_this.roleUserSearchField){
          param.field = _this.roleUserSearchField;
        }
        param.roleId = _this.handle.data.id;
        param.start=start||_this.roleUserGridPageData.start;
        param.size=_this.roleUserGridPageData.size;
        ReqUtil.doPost(API.userService.searchRoleUserPageByParam.path)
          .setData(param)
          .setSuccessFunction(function (result) {
            if(result.success){
              _this.roleUserGridData = result.data.records||result.data.content
              _this.roleUserGridPageData.start = (result.data.number+1)||result.data.current;
              _this.roleUserGridPageData.total = result.data.totalPages|| result.data.total;
            }
          })
          .isRawBody()
          .execute()
      },
      api_searchNotRoleUserPageByParam(start){
        let _this = this;
        let param ={};
        if(_this.userKeyword){
          param.keyword=_this.userKeyword;
        }
        if(_this.userSearchField){
          param.field = _this.userSearchField;
        }
        param.roleId = _this.handle.data.id;
        param.start=start||_this.userGridPageData.start;
        param.size=_this.userGridPageData.size;
        ReqUtil.doPost(API.userService.searchNotRoleUserPageByParam.path)
          .setData(param)
          .setSuccessFunction(function (result) {
            if(result.success){
              _this.userGridData = result.data.records||result.data.content
              _this.userGridPageData.start = (result.data.number+1)||result.data.current;
              _this.userGridPageData.total = result.data.totalPages|| result.data.total;
            }
          })
          .isRawBody()
          .execute()
      },
      api_addRoleUser:function (userId) {
        let _this = this;
        let param = _this.handle.data;
        param.userIds=[userId];
         ReqUtil.doPost(API.roleService.addRoleUser.path)
           .setData(param)
           .setSuccessFunction(function (result) {
               if(result.success){
                 MsgUtil.NOTICE_INFO("成功",result.data);
                 _this.api_searchRoleUserPageByParam(1);
                 _this.api_searchNotRoleUserPageByParam(1);
               }
           })
           .isRawBody()
           .execute();
      },
      api_delRoleUserByRoleIdAndUserIds:function (userId) {
        let _this = this;
        let param = _this.handle.data;
        param.userIds=[userId];
        ReqUtil.doPost(API.roleService.delRoleUserByRoleIdAndUserIds.path)
          .setData(param)
          .setSuccessFunction(function (result) {
            if(result.success){
              MsgUtil.NOTICE_INFO("成功",result.data);
              _this.api_searchRoleUserPageByParam(1);
              _this.api_searchNotRoleUserPageByParam(1);
            }
          })
          .isRawBody()
          .execute();
      }
    }
  }
</script>

<style scoped>

</style>
