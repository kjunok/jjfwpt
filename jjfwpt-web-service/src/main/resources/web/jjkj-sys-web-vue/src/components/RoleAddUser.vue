<template>
  <Drawer draggable
    :closable="false"
    v-model="show"
    width="1000"
    :mask-closable="false"
    @on-visible-change="visibleChange">
    <div class="ivu-drawer-header">
      <Icon size="20" type="md-close" class="drawer-close-icon" @click="close()"/>
      <div class="ivu-drawer-header-inner">角色用户</div>
      <!--<div class="" style="position: absolute;right: 20px;top:10px;">-->
        <!--<Button type="primary" @click="saveOrUpdateRole('roleInfoForm')">提交</Button>-->
      <!--</div>-->
    </div>
    <Tabs value="name1">
      <TabPane label="角色用户" name="name1">
        <Table width="100%" :height="clientHeight+140" :columns="userInfoGrid.columns" :data="userInfoGrid.roleUser">
          <template slot-scope="{ row,index }" slot="action">
            <div style="">
              <Icon size="25" style="color: red" type=ios-trash @click="delRoleUser(row.id)"></Icon>
            </div>
          </template>

          <template slot-scope="{ row,index }" slot="avatar">
            <div style="">
              <img v-if="row.avatar.indexOf('http')==0" :src="row.avatar" width="30" height="30" style="border-radius: 15px"/>
              <Icon v-if="!row.avatar" type="ios-flower" size="35"/>
            </div>
          </template>
        </Table>
        <Page style="margin-top: 10px;float: right" size="small" show-total show-elevator
              :total="userInfoGrid.roleUserPageData.total"
              :current="userInfoGrid.roleUserPageData.start"
              :page-size="userInfoGrid.roleUserPageData.size"/>
      </TabPane>
      <TabPane label="添加" name="name2">
        <Table width="100%" :height="clientHeight+140" :columns="userInfoGrid.columns" :data="userInfoGrid.notRoleUser">
          <template slot-scope="{ row,index }" slot="action">
            <div>
              <Icon size="25" style="color: #3862f5" type=md-add-circle @click="addRoleUser(row.id)"></Icon>
            </div>
          </template>
          <template slot-scope="{ row,index }" slot="avatar">
            <div style="">
              <img v-if="row.avatar.indexOf('http')==0" :src="row.avatar" width="30" height="30" style="border-radius: 15px"/>
              <Icon v-if="!row.avatar" type="ios-flower" size="35"/>
            </div>
          </template>
        </Table>
        <Page style="margin-top: 10px;float: right" size="small" show-total show-elevator
              :total="userInfoGrid.notRoleUserPageData.total"
              :current="userInfoGrid.notRoleUserPageData.start"
              :page-size="userInfoGrid.notRoleUserPageData.size"/>
      </TabPane>
    </Tabs>
  </Drawer>
</template>

<script>
    export default {
      props:['clientHeight','role','show'],
      name: "RoleAddUser",
      data () {
        return {
          userInfoGrid:{
            columns: [
              {
                type: 'selection',
                width: 35,
                align: 'center',
                fixed: 'left',
              },
              {
                title: '操作',
                slot: 'action',
                width: 80,
                align:'center',
                fixed: 'left'
              },
              {
                title: '用户名',
                key: 'username',
                width: 150,
                fixed: 'left'
              },
              {
                title: '昵称',
                key: 'nickName',
                width: 150,
                //fixed: 'left'
              },
              {
                title: '姓名',
                key: 'name',
                width: 150,
                //fixed: 'left'
              },{
                title: '图像',
                slot: 'avatar',
                width: 100,
                //fixed: 'left'
              },
              {
                title: '生日',
                key: 'birthday',
                width: 150,
                //fixed: 'left'
              },
              {
                title: '性别',
                key: 'sex',
                width: 100,
                //fixed: 'left'
              },
              {
                title: '邮箱',
                key: 'email',
                width: 200,
                //fixed: 'left'
              },
              {
                title: '手机号',
                key: 'phone',
                width: 150,
                //fixed: 'left'
              },
              {
                title: '状态',
                key: 'status',
                width: 100,
                //fixed: 'left'
              },
              {
                title: '创建人',
                key: 'createUserId',
                width: 200,
                //fixed: 'left'
              },
              {
                title: '创建时间',
                key: 'createTime',
                width: 200,
                //fixed: 'left'
              },
              {
                title: '修改人',
                key: 'updateUserId',
                width: 200,
                //fixed: 'left'
              },
              {
                title: '修改时间',
                key: 'updateTime',
                width: 200,
                //fixed: 'left'
              }
            ],
            roleUser: [],
            notRoleUser:[],
            roleUserPageData: {
              total: 100,
              start: 1,
              size: 10
            },
            notRoleUserPageData: {
              total: 100,
              start: 1,
              size: 10
            }
          }
        }
      },
      methods: {
        //添加角色用户
        addRoleUser(ids){
          const that = this;
          that.$util.req("/api/security/role/addRoleUserList")
            .setJsonDataStr({
              id:that.role.id,
              userIds:[ids]
            })
            .setSuccessfn(function (result) {
              if (result.code === 1000) {
                that.$Notice.success({
                  title: '操作成功',
                  desc: '角色用户添加成功!'
                });
                that.searchNotRoleUserList();
                that.searchRoleUserList();
              }
            })
            .setErrorfn(function (e) {})
            .go(that)
        },
        delRoleUser(ids){
          const that = this;
          that.$util.req("/api/security/role/delRoleUserList")
            .setJsonDataStr({
              id:that.role.id,
              userIds:[ids]
            })
            .setSuccessfn(function (result) {
              if (result.code === 1000) {
                that.$Notice.success({
                  title: '操作成功',
                  desc: '角色用户删除成功!'
                });
                that.searchNotRoleUserList();
                that.searchRoleUserList();
              }
            })
            .setErrorfn(function (e) {})
            .go(that)
        },
        searchRoleUserList() {
          const that = this;
          that.$util.req("/api/security/user/searchRoleUserList")
            .setJsonDataStr({
              roleId:that.role.id,
              start:that.userInfoGrid.roleUserPageData.start,
              size:that.userInfoGrid.roleUserPageData.size
            })
            .setSuccessfn(function (result) {
              if (result.code === 1000) {
                that.userInfoGrid.roleUser = result.data.records;
                that.userInfoGrid.roleUserPageData.start = result.data.start;
                that.userInfoGrid.roleUserPageData.total = result.data.total;
              }
            })
            .setErrorfn(function (e) {})
            .go(that)
        },
        searchNotRoleUserList() {
          const that = this;
          that.$util.req("/api/security/user/searchNotRoleUserList")
            .setJsonDataStr({
              roleId:that.role.id,
              start:that.userInfoGrid.notRoleUserPageData.start,
              size:that.userInfoGrid.notRoleUserPageData.size
            })
            .setSuccessfn(function (result) {
              if (result.code === 1000) {
                that.userInfoGrid.notRoleUser = result.data.records
                that.userInfoGrid.notRoleUserPageData.start = result.data.start;
                that.userInfoGrid.notRoleUserPageData.total = result.data.total;
              }
            })
            .setErrorfn(function (e) {})
            .go(that)
        },
        close(){
          this.$emit("roleAddUserListen", function (parent) {
            parent.roleInfoForm.roleAddUser=false
          })
        },
        visibleChange(state){
          if(state){
            this.searchRoleUserList()
            this.searchNotRoleUserList()
          }
        }
      }
    }
</script>

