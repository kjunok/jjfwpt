<template>
  <div style="background: rgba(0, 0, 0, 0.36)">
    <Card style="width:100%;background: rgba(0, 0, 0, 0.36);border: none;border-bottom: white 1px solid">
      <Icon type="ios-bulb"/>
      说明
      <span style="color: #ffabfa">菜单管理</span>
    </Card>
    <Tabs style="color:white;font-weight: bold">
      <TabPane label="菜单列表" icon="logo-apple">
        <Card :bordered="false" style="width: 100%;padding:0px;background: rgba(0, 0, 0, 0.36);margin-top: -15px;">
          <div style="margin:-5px 10px 10px 0px">
            <Button icon="md-add" type="default" size="small" shape="circle" style="float: right"
                    @click="saveOrUpdatecityInfoForm('add',{type:'1'})" ghost>增加
            </Button>
            <Input v-model="cityInfoGrid.keyword" placeholder="搜索关键字" style="width: auto;width: 500px">
              <Button @click="searchCityList" slot="append" icon="ios-search"></Button>
            </Input>
          </div>

        <Table ref="table"
               style="background: rgba(0, 0, 0, 0.36);"
               :height="clientHeight"
               :columns="cityInfoGrid.columns"
               :data="cityInfoGrid.data" showStripe>

          <template slot-scope="{ row }" slot="name">
            <Row>
              <Col span="4" v-if="row.type==2">&nbsp;</Col>
              <Col span="8" v-if="row.type==3">&nbsp;</Col>
              <Col :span="16">
                <div style="color: rgba(13,30,13,0.58);border-radius: 20px"
                     :style="{color:function(){
                                        if(row.type==2){return '#2d8cf0'}
                                        else if(row.type==3){return 'green'}
                                        else{return 'yellow'}
                                    }()}"
                >
                  <Icon size="30" :type=row.icon></Icon>&nbsp;&nbsp;
                  <strong>{{ row.name }}</strong>&nbsp;&nbsp;
                </div>
              </Col>
            </Row>
          </template>
          <template slot-scope="{ row }" slot="state">
            <strong>{{$util.localDict.cityinfo.state[row.state]}}</strong>
          </template>
          <template slot-scope="{ row }" slot="type">
            <strong>{{$util.localDict.cityinfo.type[row.type]}}</strong>
          </template>
          <template slot-scope="{ row,index }" slot="action">
            <div style="text-align: right">
              <Icon size="25" style="color: lightblue" type=md-search @click="showCityView(index)"></Icon>
              <Icon size="25" style="color: green" type=ios-create
                    @click="saveOrUpdatecityInfoForm('update',{type:row.type,id:row.id})"></Icon>
              <Icon size="25" style="color: red" type=ios-trash @click="removeCity(row.id)"></Icon>
            </div>
          </template>
        </Table>
        <div style="margin: 10px;">
          <div style="float: right;">
            <Page style="height: 40px" show-total show-elevator show-sizer
                  :total="cityInfoGrid.pageData.total"
                  :current="cityInfoGrid.pageData.start"
                  :page-size="cityInfoGrid.pageData.size"
                  @on-change="changePageNum"
                  @on-page-size-change="changePageSize"
            />
          </div>
        </div>
        </Card>
      </TabPane>
      <TabPane label="Windows" icon="logo-windows">
      </TabPane>

      <TabPane label="Linux" icon="logo-tux">标签三的内容</TabPane>

    </Tabs>


    <Drawer v-model="cityInfoForm.show"
            draggable
            :closable="false"
            :mask-closable="false"
            width=1000>
      <div class="ivu-drawer-header">
        <Icon size="20" type="md-close" class="drawer-close-icon" @click="cityInfoForm.show=false"/>
        <div class="ivu-drawer-header-inner">{{cityInfoForm.title}}</div>
        <div class="" style="position: absolute;right: 20px;top:10px;">
          <Button type="primary" @click="saveOrUpdateCity('cityInfoForm')">提交</Button>
        </div>
      </div>
      <Card :bordered="false" :style="{height:(clientHeight+170)+'px'}">
        <Form ref="cityInfoForm" :model="cityInfoForm.data"
              :rules="cityInfoForm.validate" :label-width="80">
          <FormItem label='城市名称' label-position="left" prop="code">
            <Input class="" v-model="cityInfoForm.data.cityName"
                   placeholder=""/>
          </FormItem>

          <FormItem label='省份名称' label-position="left" prop="code">
            <Input class="" v-model="cityInfoForm.data.provinceName"
                   placeholder=""/>
          </FormItem>

        </Form>
      </Card>
    </Drawer>

    <IconSelect v-on:iconSelectListen="selectIcon" :clientHeight="clientHeight"
                :show="cityInfoForm.iconSelect"></IconSelect>

    <Drawer v-model="cityInfoForm.cityView.show"
            draggable
            :closable="false"
            :mask-closable="false"
            width=1000>
      <!--头部 start-->
      <div class="ivu-drawer-header">
        <Icon size="20" type="md-close" class="drawer-close-icon" @click="cityInfoForm.cityView.show=false"/>
        <div class="ivu-drawer-header-inner">详情</div>
      </div>
      <!--头部 end-->
      <Card :bordered="false" :style="{height:(clientHeight+170)+'px'}">
        <Row type="flex" v-for="(item ,index) in cityInfoGrid.columns"
             v-if="item.key"
             :key="index"
             style="height: 40px;margin-top: 5px;background: rgba(4,9,7,0.2);line-height: 35px;border-radius: 6px">
          <Col span="4">
            <div style="color: rgb(167,225,230);padding: 5px;text-align: center;font-weight: bold">
              {{item.title}}
            </div>
          </Col>
          <Col span="20">
            <div style="color: rgba(159,216,209,0.65);padding: 5px;">
              <div v-if="item.key=='icon1.css'">
                <Icon size="30" :type="cityInfoForm.cityView.data[item.key||item.slot]"/>
              </div>
              <div v-else>
                            <span v-if="cityInfoForm.cityView.data[item.key||item.slot]" style="color: yellow">
                              {{(cityInfoForm.cityView.data[item.key||item.slot])}}
                            </span>
                <span v-else>
                                                                </span>
              </div>
            </div>
          </Col>
        </Row>
      </Card>
    </Drawer>
  </div>
</template>

<script>
  import IconSelect from "../../components/IconSelect";

  export default {
    components: {IconSelect},
    props: ["clientHeight"],
    data() {
      return {
        styles: {
          height: 'calc(100% - 55px)',
          overflow: 'auto',
          paddingBottom: '53px',
          position: 'static'
        },
        cityInfoGrid: {
          keyword:"",
          columns: [
            {
              type: 'selection',
              width: 60,
              align: 'center',
              fixed: 'left',
            },
            {
              title: '操作',
              slot: 'action',
              width: 150,
              fixed: 'left'
            },
            {
              title: '城市名称',
              key: 'cityName',
              //width: 200,
              //fixed: 'left'
            },
            {
              title: '省份名称',
              key: 'provinceName',
              //width: 200,
              //fixed: 'left'
            },
          ],
          data: [],
          pageData: {
            total: 100,
            start: 1,
            size: 10
          }
        },
        cityInfoForm: {
          title: "添加",
          iconSelect: false,
          showField: {
            id:false,
            cityName:false,
            provinceName:false,
          },
          select:{
            pid:[]
          },
          clearData: {
            id:'',
            cityName:'',
            provinceName:'',
          },
          data: {
            id:'',
            cityName:'',
            provinceName:'',
          },
          validate: {
            cityName:[
              {required: true, message: '请输入城市名称', trigger: 'blur'},
            ],
            provinceName:[
              {required: true, message: '请输入省份名称', trigger: 'blur'},
            ],
          },
          show: false,
          cityView:{
            data: {},
            show: false
          }
        }
      }
    },
    methods: {
      checkCityName() {

      },
      showCityView(index) {
        this.cityInfoForm.cityView.show = true;
        this.cityInfoForm.cityView.data = this.cityInfoGrid.data[index]
      },
      removeCity(id) {
        const that = this;
        that.$Modal.confirm({
          title: "警告",
          content: "确定要删除吗？",
          onOk: function () {
            that.$util.req("/api/security/city/delCity")
              .setJsonDataStr({ids: id})
              .setSuccessfn(function (result) {
                if (result.code === 1000) {
                  that.$Notice.success({
                    title: '操作成功',
                    desc: 'City删除成功'
                  });
                  that.searchCityList()
                }else{
                  that.$Notice.error({
                    title: '操作失败',
                    desc: '${cfg.UpModuleName}删除失败'
                  });
                }
              }).go(that)
          }
        })
      },
      changeType(value, callBack) {
        callBack = callBack || function () {
        }
        if (value == "1") {
          this.cityInfoForm.showField.pid = false
          this.cityInfoForm.showField.viewPath = false
          this.cityInfoForm.showField.groupName = false
          this.cityInfoForm.data.pid = '0'
          callBack()
        } else if (value == "2" || value == "3") {
          if (value == "2") {
            this.cityInfoForm.showField.groupName = true
          }
          if (value == "3") {
            this.cityInfoForm.showField.groupName = false
          }
          this.cityInfoForm.showField.pid = true
          this.cityInfoForm.showField.viewPath = true
        }
      },
      selectIcon: function (fun) {
        fun(this)
      },
      saveOrUpdatecityInfoForm(actiontype, data) {
        if (actiontype === "add") {
          this.cityInfoForm.title = "添加"
          this.cityInfoForm.data = this.cityInfoForm.clearData
          this.changeType(data.type)
        } else if (actiontype === "update") {
          this.cityInfoForm.title = "修改"
          const that = this;
          that.$util.req("/api/security/city/searchCity")
            .setJsonDataStr({id: data.id})
            .setSuccessfn(function (result) {
              let cityInfo = result.data
              that.cityInfoForm.data = cityInfo
            })
            .go(that)
        }
        this.cityInfoForm.show = true
      },
      saveOrUpdateCity(name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            const that = this;
            that.$util.req("/api/security/city/saveOrUpdateCity")
              .setJsonDataStr(that.cityInfoForm.data)
              .setSuccessfn(function (result) {
                if (result.code === 1000) {
                  that.$Notice.success({
                    title: '操作成功',
                    desc: 'city' + that.cityInfoForm.title + '成功'
                  });
                  that.searchCityList();
                  that.cityInfoForm.show = false;
                }})
              .setErrorfn(function (e) {})
              .go(this)
          } else {
            //this.$Message.error('Fail!')
          }
        })
      },
      searchCityList() {
        const that = this;
        that.$util.req("/api/security/city/searchCityList")
          .setJsonDataStr({
            start:that.cityInfoGrid.pageData.start,
            size:that.cityInfoGrid.pageData.size,
            keyword:that.cityInfoGrid.keyword
          })
          .setSuccessfn(function (result) {
            if (result.code === 1000) {
              that.cityInfoGrid.data = result.data.records
              that.cityInfoGrid.pageData.start = result.data.start;
              that.cityInfoGrid.pageData.total = result.data.total;
            }
          })
          .setErrorfn(function (e) {})
          .go(that)
      },
      changePageNum(pageNum) {
        this.cityInfoGrid.pageData.start = pageNum;
        this.searchCityList()
      },
      changePageSize(pageSize) {
        this.cityInfoGrid.pageData.size = pageSize;
        this.searchCityList()
      }
    }
    ,
    mounted() {
      this.searchCityList();
    }
  }
</script>

<style>

</style>
