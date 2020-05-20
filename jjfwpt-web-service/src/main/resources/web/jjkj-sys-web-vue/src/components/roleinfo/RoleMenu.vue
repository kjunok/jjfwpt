<template>
  <Drawer
    v-model="show"
    draggable
    :closable="false"
    :mask-closable="false"
    width="800">
    <div class="ivu-drawer-header">
      <Icon size="25" type="md-close" class="drawer-close-icon" @click="close()"/>
      <div class="ivu-drawer-header-inner">角色菜单</div>
    </div>
    <div :style="{height:clientHeight+'px'}" style="margin-top:0px;overflow: auto;width: 100%;">
      <Tree show-checkbox
            ref="roleMenuTree"
            :render="treeRender"
            :style="{height:clientHeight+'px'}"
            style="width: 100%"
            :data="menuData" ></Tree>
    </div>
    <div class="drawer-footer">
      <Button @click="close()" style="margin-right: 8px;background-image: linear-gradient(-15deg, #ff754c,#7af0f6, #f62e45);">取消</Button>
      <Button @click="api_saveOrUpdateRoleMenu()" type="primary" style="background-image: linear-gradient(-15deg, #987cff,#7af0f6, #8593f6);" >提交</Button>
    </div>
  </Drawer>
</template>

<script>
    import ReqUtil from "../../assets/script/utils/ReqUtil";
    import API from "../../assets/script/API";
    import CommonUtil from "../../assets/script/utils/CommonUtil";

    export default {
      name: "RoleMenu",
      props:['show',"handle"],
      data(){
          return{
            clientHeight:CommonUtil.ClientHeight(130),
            menuData:[],

          }
      },
      watch:{
        show:function (value) {
          if(value){
            this.api_loadMenuInfoList();
          }
        }
      },
      created() {
        let _this =this;
        /*
        * 窗体高度改变处理
        */
        CommonUtil.RegHeightChangeFun("RoleMenu",function (height) {
          _this.clientHeight=height-130;
        });
        /*============================================================================*/
      },
      destroyed(){
        CommonUtil.UnRegHeightChangeFun("RoleMenu");
      },
      methods:{
        treeRender(h, { root, node, data }) {
          return h('span', {
            style: {
              display: 'inline-block',
              width: '100%'
            }
          }, [
            h('span', [
              h('Icon', {
                props: {
                  type: data.icon,
                  size:22
                },
                style: {
                  marginRight: '8px'
                }
              }),
              h('span', data.title)
            ]),
            h('span', {
              style: {
                display: 'inline-block',
                float: 'right',
                fontSize:'15px',
                marginRight: '42px'
              }
            },data.name)
          ]);
        },
        close(){
          this.$emit("RoleMenuListen", function (parent) {
            parent.roleMenu=false;
          })
        },
        treeMenuAddRender(menuList,roleMenuRelation,expand) {
          for (let i in menuList) {
            if(roleMenuRelation.indexOf(menuList[i].id)>-1){
              menuList[i].checked=true
            }
            menuList[i].expand = expand;
            if (menuList[i].children.length > 0) {
              this.treeMenuAddRender(menuList[i].children,roleMenuRelation,expand)
            }
          }
          return menuList
        },
        api_loadMenuInfoList(){
          let _this = this;
          /*加载所有树形菜单*/
          ReqUtil.doPost(API.menuService.searchMenuTreeSortList.path)
            .setData({})
            .isRawBody()
            .setSuccessFunction(function (result) {
              if (result.success) {
                /*加载角色菜单关联关系*/
                ReqUtil.doGet(API.roleService.getRoleMenusByRoleId.path)
                  .setData({id:_this.handle.data.id})
                  .setSuccessFunction(function (roleResult) {
                    if (roleResult.success) {
                      let roleMenus =[];
                      for(let i in roleResult.data){
                        roleMenus[i]= roleResult.data[i]["menu_id"];
                      }
                      _this.menuData = _this.treeMenuAddRender(result.data,roleMenus,true)
                    }
                  }).execute()

              }
            })
            .execute()
        },
        api_saveOrUpdateRoleMenu(){
          const _this = this;
          let menus=_this.$refs.roleMenuTree.getCheckedAndIndeterminateNodes();
          let menu_checks=_this.$refs.roleMenuTree.getCheckedNodes();
          let menu_checks_id=[];
          let roleMenuIds=[];
          for(let i in menu_checks){
            menu_checks_id.push(menu_checks[i].id);
          }
          for(let i in menus){
            roleMenuIds.push(menus[i].id);
            roleMenuIds.push(menus[i].type);
            if(menu_checks_id.indexOf(menus[i].id)>-1){
              roleMenuIds.push('1')//全选中状态
            }else{
              roleMenuIds.push('0')//半选中状态
            }
          }
          ReqUtil.doPost(API.roleService.saveOrUpdateRoleMenu.path)
            .setData({
              id:_this.handle.data.id,
              menuIds:roleMenuIds
            })
            .setSuccessFunction(function (result) {
                if(result.success){
                  _this.close()
                }
            }).isRawBody().execute()
        }
      }
    }
</script>

<style scoped>

</style>
