<template>
  <div class="layout" style="border: 0px;background: rgba(201,255,237,0.01);">
    <Icon @mouseover="mouseover()" @click="openLeftMenuPanel()" size="30" :type="leftMenuIcon"
          style="cursor: pointer; color:white;width:50px;float: left;position: absolute;left:10px;top: 15px"/>

    <Layout style="background: rgba(201,255,237,0.01)">
      <Header
        style="text-align:right; background: rgba(101, 84, 84, 0);color: white; padding: 0px 0px; height: 60px;font-size: 13px">
        <div style="float: left; margin-left: 13px;margin-left: 52px; border-left: #726893 1px solid;height:60px; padding:0 5px">
          <span style="font-size: 20px;color: #ffc2ba">叮呤@江湖</span>
          <span style="font-size: 13px;color: #ffc2ba">[{{address.city}}]</span>
          <Badge :count="43" :offset="[16,0]" overflow-count="99" >
            <a href="#" >
              <Icon size="45" type="ios-mail-outline" style="margin-left: 10px" />
            </a>
          </Badge>
          <Badge :count="43" :offset="[16,0]" overflow-count="99" style="">
            <a href="#" >
              <Icon type="ios-notifications-outline" style="margin-left: 20px" size="45"></Icon>
            </a>
          </Badge>
          <Poptip trigger="hover" placement="bottom-end" width="200" style="margin-left: 15px">
            <Icon style="font-size: 30px;color: lightblue;" type="ios-shirt"></Icon>
            <div slot="content" style="text-align: left;padding: 0px">
              <button type="primary"
                      style="background:url('../static/img/skin-kiwi.jpg');width: 99%;height: 30px"></button>
              <br>
              <button type="primary"
                      style="background:url('../static/img/skin-sunny.jpg');width: 99%;height: 30px"></button>
              <br>
              <button type="primary"
                      style="background:url('../static/img/skin-greenish.jpg');width: 99%;height: 30px"></button>
              <br>
              <button type="primary"
                      style="background:url('../static/img/skin-cloth.jpg');width: 99%;height: 30px"></button>
            </div>
          </Poptip>
        </div>
      </Header>
      <layout style="background: rgba(201,255,237,0.01)">
        <Sider hide-trigger :style="{flex:siderFlex}"
               style="flex:0 0 50px; min-width: 20px; max-width: 250px; background: rgba(0, 0, 0, 0.34);padding:0 13px">
          <div style="width: 40px;border-right: rgba(131, 149, 230, 0.54) 1px solid;"
               :style="{height: clientHeight+'px'}">
            <Menu ref="parentMenu" style="width: 53px;padding: 0px;margin-left: -13px; margin-top:0px; background: none; "
                  :active-name="menuActive.parent">
              <MenuItem v-for="(item,index) in menuData"
                        :key="index"
                        :name="'menu'+index"
                        @click.native="listMenus(index)"
                        style="padding:15px 5px;position: relative;text-align: center">
                <img v-if="item.icon.indexOf('/')>-1" :src="'/api/file/'+item.icon" class="ivu-icon" style="border-radius: 5px" width="28" height="28"/>
                <Icon size="28" v-if="item.icon.indexOf('/')==-1" :type="item.icon" @click.native="openLeftMenuPanel(1)" style="cursor: pointer;padding: 0px;"/>
                <span style="margin-left: -15px; display: block;width: 70px;text-align: center; font-size: 1px; -webkit-transform:scale(0.7);">
                  {{item.name}}</span>
              </MenuItem>
            </Menu>
          </div>
          <!-- Sidebar -->
          <aside v-if="showChildMenu"
                 style="width: 200px;height:800px;padding: 5px; position: absolute;right: 0px;top:0px;" id="sidebar"
                 :style="{height:clientHeight+'px'}"
          >
            <!-- Sidbar Widgets -->
            <div class="side-widgets overflow">
              <!-- Profile Menu -->
              <div style="text-align: center;" id="profile-menu">
                <Dropdown trigger="click" >
                  <a href="javascript:void(0)">
                    <Avatar style="border-radius:50px;height: 70px;width: 70px;border: 2px solid white" :src="user_avatar"/>
                  </a>
                  <DropdownMenu slot="list"  style="width: 190px;">
                    <DropdownItem><router-link to="/myResume" >我的简历</router-link></DropdownItem>
                    <DropdownItem  divided >
                      <div @click="logout()">
                        <Icon size="20" type="md-power" style="color: #ff5d42;font-weight: bold;"></Icon>
                        <span style="color: #ff5d42;font-weight: bold;">注销登录</span>
                      </div>
                    </DropdownItem>
                  </DropdownMenu>
                </Dropdown>
                <h4 class="m-1" style="color: white;margin: 10px">{{user_nick_name}}</h4>
                <Menu style="width: 205px;padding: 0px;margin-left: 0px; background: none; " ref="childMenu"  :active-name="menuActive.child">
                  <div v-for="(item,i) in childrenMenu" :key="i"  style="padding: 0px">
                    <Divider orientation="center" size="small" style="color: gray;font-size: 10px;background: none;">
                      {{item.groupName||"分类"}}
                    </Divider>
                    <MenuItem v-for="(citem,j) in item.menuData"
                              :key="citem.id+'_'+j"
                              :name="'cmenu'+citem.id"
                              @click.native="topage(citem.viewPath,'cmenu'+citem.id)"
                              style="padding: 5px;text-align: left;">
                      <div style="width: 100%">
                        <img v-if="citem.icon.indexOf('/')>-1" :src="'/api/file/'+citem.icon" class="ivu-icon" style="border-radius: 5px" width="25" height="25"/>
                        <Icon v-if="citem.icon.indexOf('/')==-1" size="25" :type="citem.icon" style="cursor: pointer;"/>
                        {{citem.name}}
                      </div>
                      <span class="menudescription" v-if="citem.description" style="">
                         <Icon style="float: left;color: #ffd5bb;font-size: 17px;margin-right: 5px" type="ios-help-circle-outline" />
                        {{citem.description}}</span>
                    </MenuItem>

                  </div>
                </Menu>
              </div>

            </div>
          </aside>
        </Sider>
        <Content style="background: rgba(0, 0, 0, 0.34);color: yellow"
                 :style="{padding: '13px', height: clientHeight+'px'}">
          <router-view v-bind:clientHeight="clientHeight-300"></router-view>
        </Content>
      </layout>
    </Layout>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        user_avatar:'',
        user_nick_name:'',
        address:{
          city:''
        },
        headerLogin:true,
        clientHeight: (`${document.documentElement.clientHeight}`),
        tabs: 2,
        siderFlex: "0 0 390px",
        leftMenuIcon: 'ios-arrow-dropleft-circle',
        showChildMenu: true,
        childrenMenu: [],
        menuData:{},
        menuActive:{
          parent:"",
          child:""
        }
      }
    },
    created:function(){
      let loginUserInfo = JSON.parse(sessionStorage.getItem("userinfo"))
      this.user_avatar = loginUserInfo.avatar
      this.user_nick_name=loginUserInfo.nickName
    },
    mounted: function () {
      window.onresize = function temp() {
        this.clientHeight = `${document.documentElement.clientHeight}`;
      };
      this.searchMenuList()
      //this.getLocation()
    },
    methods: {
      getLocation(){
        let geolocation = new BMap.Geolocation();
        let that=this;
        geolocation.getCurrentPosition(function(r){
          if(this.getStatus() == BMAP_STATUS_SUCCESS){
            that.address.city=r.address.city
          }
          else {
            alert('failed'+this.getStatus());
          }
        },{enableHighAccuracy: true})
      },
      searchMenuList() {
        const that = this;
        that.$util.req("/api/menuService/searchMenuTreeNavList")
          .setSuccessfn(function (result) {
            if (result.success) {
              that.menuData={};
              let firstPmenu,firstCmenu
              for(let i in result.data){
                that.menuData[result.data[i].id]=result.data[i]
                if(i==0){
                  firstPmenu = "menu"+result.data[i].id;
                  firstCmenu = "cmenu"+result.data[i].children[0].id
                  //第一次登录后定位为一个父菜单和第一个子菜单页面
                  if(!sessionStorage.getItem("currentPagePath")){
                    that.$router.push({path:result.data[i].children[0].viewPath})
                  }
                }
              }
              that.menuActive.parent=that.sessionStorageActiveMenu('menuActive_parent')
              that.menuActive.child=that.sessionStorageActiveMenu('menuActive_child')
              that.menuActive.parent = that.menuActive.parent?that.menuActive.parent:firstPmenu
              that.menuActive.child=that.menuActive.child?that.menuActive.child:firstCmenu
              that.listMenus(that.menuActive.parent.replace("menu",""))
              that.$nextTick(() => {
                that.$refs.secondMenu.updateActiveName()
                that.$refs.show.updateActiveName()
              })
            }
          })
          .setErrorfn(function (e) {
          })
          .go(that)
      },
      openLeftMenuPanel(type) {
        const that=this;
        if(type===1){
          this.leftMenuIcon = 'ios-arrow-dropleft-circle'
          this.siderFlex = "0 0 390px"
          window.setTimeout(function(){
            that.showChildMenu = true
          },150)
        }else{
          if (this.leftMenuIcon === "ios-arrow-dropright-circle") {
            this.leftMenuIcon = 'ios-arrow-dropleft-circle'
            this.siderFlex = "0 0 390px"
            window.setTimeout(function(){
              that.showChildMenu = true
            },150)
          } else {
            this.leftMenuIcon = 'ios-arrow-dropright-circle'
            this.siderFlex = "0 0 40px"
            this.showChildMenu = false
          }
        }
      },
      handleTabRemove(name) {
        this['tab' + name] = false;
      },
      sessionStorageActiveMenu(key){
        if(!sessionStorage[this.$route.path]){
          return null;
        }
       return JSON.parse(sessionStorage[this.$route.path])[key]
      },
      setSessionStorageActiveMenu(path,key,value){
        if(!sessionStorage[path]){
          sessionStorage[path]=JSON.stringify({})
        }
        let ss=JSON.parse(sessionStorage[path]);

        ss[key]=value;
        sessionStorage[path]=JSON.stringify(ss)
      },
      mouseover() {
      },
      listMenus(key) {
        let childrenMenu = this.menuData[key].children;
        let groupMap={};
        for(let i in childrenMenu){
          let item = childrenMenu[i]
          let groupName=item.groupName;
          if(groupName == ""){
            groupName = "默认分组"
          }
          if(!groupMap[groupName]){
            groupMap[groupName]=[];
          }
          groupMap[groupName].push(item)
        }
        let groupList=[];
        for(let key in groupMap){
          groupList.push({
            groupName: key,
            menuData:groupMap[key]
          })
        }
        this.menuActive.parent="menu"+key;
        this.childrenMenu=groupList;
      },
      topage(path,childMenuName) {
        try{
          this.setSessionStorageActiveMenu(path,'menuActive_parent',this.menuActive.parent)
          this.setSessionStorageActiveMenu(path,'menuActive_child',childMenuName)
          this.$router.push({path: path||'/index/404'})
        }catch (e) {
        }

      },
      logout() {
        const that=this;
        this.$util.req("/auth/oauth/logOut")
          .setSuccessfn(function (result) {
            if(result.success){
              sessionStorage.clear()
              that.$router.push({path:"/login"})
            }
          })
          .setErrorfn(function (result) {
            sessionStorage.clear()
            that.$router.push({path:"/login"})
          })
          .go()
      }
    }
  }
</script>
<style scoped>
  input::-webkit-input-placeholder{
    color:red;
  }
  input::-moz-placeholder{   /* Mozilla Firefox 19+ */
    color:red;
  }
  input:-moz-placeholder{    /* Mozilla Firefox 4 to 18 */
    color:red;
  }
  input:-ms-input-placeholder{  /* Internet Explorer 10-11 */
    color:red;
  }
</style>
