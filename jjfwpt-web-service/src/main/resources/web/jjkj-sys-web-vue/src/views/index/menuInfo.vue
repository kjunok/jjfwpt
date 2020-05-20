<template>
    <div style="">
      <Explain :content="explain"></Explain>
      <Card class="view-card-body" style="margin-left:0px;" :style="{height: clientHeight+'px'}">
<!--          <Tree :data="data5" :render="renderContent" class="demo-tree-render"></Tree>-->
        <Table :height="clientHeight+2"
               style="margin: -16px;"
               row-key="id"
               border
               :columns="menuGridColumns"
               :data="data5" >
        </Table>
      </Card>
      <MenuForm v-on:MenuFormListen="MenuFormListen" v-bind:handle="handle" v-bind:show="addOrUpdateShow" ></MenuForm>
    </div>
</template>
<script>
    import ReqUtil from "../../assets/script/utils/ReqUtil";
    import CommonUtil from "../../assets/script/utils/CommonUtil";
    import MenuForm from "../../components/menuinfo/MenuForm";
    import Explain from "../../components/common/Explain";
    import API from "../../assets/script/API";
    import MsgUtil from "../../assets/script/utils/MsgUtil";
    import TMenuInfo from "../../assets/script/modal/TMenuInfo";
    export default {
      name: "menuInfo",
      components: {Explain, MenuForm},
      data () {
        return {
          explain:"菜单管理",
          addOrUpdateShow:false,
          menuGridColumns:this.processGridColumns(TMenuInfo.gridColumns(this)),
          clientHeight:CommonUtil.ClientHeight(150),
          data5:[],
          handle:{type:"",data:{}},
          buttonProps: {
            type: 'default',
            size: 'small',
          }
        }
      },
      created(){
        let _this = this;
        CommonUtil.RegHeightChangeFun("menuInfo",function (height) {
          _this.clientHeight = height-150;
        });
      },
      destroyed(){
        CommonUtil.UnRegHeightChangeFun("menuInfo");
      },
      mounted(){
       this.loadMenuInfoList();
      },
      methods: {
        loadMenuInfoList(){
          let that = this;
          ReqUtil.doPost(API.menuService.searchMenuTreeSortList.path)
            .setData({})
            .isRawBody()
            .setSuccessFunction(function (result) {
              if (result.success) {
                that.data5 = [{
                  type:"0",
                  name: "菜单",
                  _showChildren: true,
                  children:result.data
                }];
              }
            })
            .setErrorFunction(function (e) {
            })
            .execute()
        },
        processGridColumns(gridColumns){
          return gridColumns;
        },
        MenuFormListen(fun){
          fun(this);
        }
      }
    }
</script>

<style>
  .demo-tree-render .ivu-tree-title{
    width: 100%;
  }
</style>
