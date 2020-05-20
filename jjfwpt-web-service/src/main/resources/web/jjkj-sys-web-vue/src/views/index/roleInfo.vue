<template>
  <div>
    <Explain :content="explain"></Explain>
    <Card class="view-card-body" style="margin-left:0px;" :style="{height: (clientHeight-43)+'px'}">
      <Row>
        <Col span="12">
          <Form style="margin: 0px 0px -10px;">
            <FormItem>
              <Input class="searchInput" v-model="keyword" style=""
                     @focusin.native="searchFieldShow=true"
                     @focusout.native="searchFieldShow=true"  placeholder="请输入查询关键字">
                <Select v-model="searchField" slot="prepend" style="width: 100px">
                  <Option v-for="item in searchColumns" :key="item.value"  :value="item.value">{{item.label}} </Option>
                </Select>
                <Button slot="append" icon="ios-search" style="width: 70px;"  @click="api_loadRoleInfoInfoList(1)"></Button>
              </Input>
            </FormItem>
          </Form>
        </Col>
        <Col span="12">
          <Button style="float: right;margin-right: 50px;"  @click="addNewRole()">
            <Icon type="md-add-circle" size="20" style="margin-top: 1px;"></Icon>
            添加</Button>
        </Col>
      </Row>
      <Table border size="small"
             :height="clientHeight-106"
             style="margin:0px -18px 0px;"
             :data="gridData"
             :columns="gridColumns" ></Table>
    </Card>
    <Card class="view-card-body" style="margin-left:0px;height: 40px;">
      <Page  style="margin: -12px;text-align: center" show-total show-elevator show-sizer transfer
             :total="gridPageData.total"
             :current="gridPageData.start"
             :page-size="gridPageData.size"
             :page-size-opts="[10, 20, 30, 40]"
             @on-change="changePageNum"
             @on-page-size-change="changePageSize"/>
    </Card>
    <RoleForm v-on:RoleFormListen="RoleFormListen" v-bind:handle="handle" :show="roleForm"></RoleForm>
    <RoleMenu v-on:RoleMenuListen="RoleMenuListen" v-bind:handle="handle" :show="roleMenu"></RoleMenu>
    <RoleUser v-on:RoleUserListen="RoleUserListen" v-bind:handle="handle" :show="roleUser"></RoleUser>
  </div>
</template>
<script>
  import Explain from "../../components/common/Explain";
  import CommonUtil from "../../assets/script/utils/CommonUtil";
  import TRoleInfo from "../../assets/script/modal/TRoleInfo";
  import ReqUtil from "../../assets/script/utils/ReqUtil";
  import API from "../../assets/script/API";
  import UserForm from "../../components/userinfo/UserForm";
  import RoleForm from "../../components/roleinfo/RoleForm";
  import RoleMenu from "../../components/roleinfo/RoleMenu";
  import RoleUser from "../../components/roleinfo/RoleUser";
  export default {
    name: "roleInfo",
    components: {RoleUser, RoleMenu, RoleForm, UserForm, Explain},
    data(){
      return {
        explain:"角色管理",
        roleForm:false,
        roleMenu:false,
        roleUser:false,
        clientHeight:CommonUtil.ClientHeight(150),
        gridColumns: TRoleInfo.gridColumns(this),
        gridPageData: {
          total: 100,
          start: 0,
          size: 10
        },
        searchColumns: TRoleInfo.searchColumns(),
        keyword:"",
        searchField: TRoleInfo.searchColumns()[0].value,
        searchFieldShow:false,
        gridData:[],
        handle:{type:"",data:{}}
      }
    },
    created() {
      let _this =this;
      /*
      * 窗体高度改变处理
      */
      CommonUtil.RegHeightChangeFun("roleInfo",function (height) {
        _this.clientHeight=height-150;
      });
      /*============================================================================*/
      _this.api_loadRoleInfoInfoList();
    },
    destroyed(){
      CommonUtil.UnRegHeightChangeFun("roleInfo");
    },
    methods:{
      addNewRole:function(){
        this.roleForm=true;
        this.handle={
          type:"add",
          data:{}
        }
      },
      RoleFormListen:function (fun) {
        fun(this);
      },
      RoleMenuListen:function(fun){
        fun(this);
      },
      RoleUserListen:function(fun){
        fun(this);
      },
      changePageNum(pageNum) {
        this.gridPageData.start = pageNum;
        this.api_loadRoleInfoInfoList()
      },
      changePageSize(pageSize) {
        this.gridPageData.size = pageSize;
        this.api_loadRoleInfoInfoList()
      },
      api_loadRoleInfoInfoList:function (start) {
        let _this = this;
        let param ={};
        if(_this.keyword){
          param.keyword=_this.keyword;
        }
        if(_this.searchField){
          param.field = _this.searchField;
        }
        param.start=start||_this.gridPageData.start;
        param.size=_this.gridPageData.size;
        ReqUtil.doPost(API.roleService.searchRolePagesByParam.path)
          .setData(param)
          .setSuccessFunction(function (result) {
            if(result.success){
              _this.gridData = result.data.records||result.data.content;
              _this.gridPageData.start = (result.data.number+1)||result.data.current;
              _this.gridPageData.total = result.data.totalPages|| result.data.total;
            }
          })
          .isRawBody()
          .execute()
      }
      
    }
  }
</script>
<style>
  .searchInput .ivu-input:focus,.searchInput .ivu-input:hover
  {
    border: 1px solid ;
    box-shadow:none;
    border: 1px solid #dcdee2;
  }
</style>
