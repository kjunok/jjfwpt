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
                <Button slot="append" icon="ios-search" style="width: 70px;"  @click="api_loadBserviceInfoInfoList(1)"></Button>
              </Input>
            </FormItem>
          </Form>
        </Col>
        <Col span="12">
          <Button style="float: right;margin-right: 50px;"  @click="addNewUser()">
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
    <UserForm v-on:UserFormListen="UserFormListen" v-bind:handle="handle" :show="bserviceForm"></UserForm>
  </div>
</template>
<script>
  import Explain from "../../components/common/Explain";
  import CommonUtil from "../../assets/script/utils/CommonUtil";
  import TBserviceInfo from "../../assets/script/modal/TBserviceInfo";
  import ReqUtil from "../../assets/script/utils/ReqUtil";
  import API from "../../assets/script/API";
  import UserForm from "../../components/userinfo/UserForm";
  export default {
    name: "roleInfo",
    components: {UserForm, Explain},
    data(){
      return {
        explain:"服务管理",
        userForm:false,
        clientHeight:CommonUtil.ClientHeight(150),
        gridColumns: TBserviceInfo.gridColumns(this),
        gridPageData: {
          total: 100,
          start: 0,
          size: 10
        },
        searchColumns: TBserviceInfo.searchColumns(),
        keyword:"",
        searchField: TBserviceInfo.searchColumns()[0].value,
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
      CommonUtil.RegHeightChangeFun("bserviceInfo",function (height) {
        _this.clientHeight=height-150;
      });
      /*============================================================================*/
      _this.api_loadRoleInfoInfoList();
    },
    destroyed(){
      CommonUtil.UnRegHeightChangeFun("bserviceInfo");
    },
    methods:{
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
        ReqUtil.doPost(API.bserviceService.searchBserviceInfoPagesByParam.path)
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
      },
      addNewUser:function(){
        this.bserviceForm=true;
        this.handle={
          type:"add",
          data:{}
        }
      },
      UserFormListen:function (fun) {
        fun(this);
      },
      changePageNum(pageNum) {
        this.gridPageData.start = pageNum;
        this.api_loadRoleInfoInfoList()
      },
      changePageSize(pageSize) {
        this.gridPageData.size = pageSize;
        this.api_loadRoleInfoInfoList()
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
