<template>
  <div style="background: rgba(0, 0, 0, 0.36)">
    <Card style="width:100%;background: rgba(0, 0, 0, 0.36);border: none;border-bottom: white 1px solid">
      <Icon type="ios-bulb"/>
      说明
      <span style="color: #ffabfa">菜单管理</span>
    </Card>
    <Tabs style="color:white;font-weight: bold">
      <TabPane label="菜单列表" icon="logo-apple">
        <Card :bordered="false"
              style="width: 100%;padding:0px;background: rgba(0, 0, 0, 0.36);margin-top: 0px;">
          <Button icon="md-add" type="default" size="small" shape="circle" style="float: right"
                  @click="saveOrUpdatedictInfoForm('add',{type:'1'})" ghost>增加
          </Button>
          <Icon size="20" type="md-dict"/>
          <Input suffix="ios-search" size="small" placeholder="Enter text" style="width: auto;width: 400px"/>
        </Card>
        <Table ref="table"
               style="background: rgba(0, 0, 0, 0.36);"
               :height="clientHeight"
               :columns="dictInfoGrid.columns"
               :data="dictInfoGrid.data" showStripe>

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
            <strong>{{$util.localDict.dictinfo.state[row.state]}}</strong>
          </template>
          <template slot-scope="{ row }" slot="type">
            <strong>{{$util.localDict.dictinfo.type[row.type]}}</strong>
          </template>
          <template slot-scope="{ row,index }" slot="action">
            <div style="text-align: right">
              <Icon size="25" style="color: #3862f5" type=md-add-circle @click="showChildTable()"></Icon>
              <Icon size="25" style="color: lightblue" type=md-search @click="showDictView(index)"></Icon>
              <Icon size="25" style="color: green" type=ios-create
                    @click="saveOrUpdatedictInfoForm('update',{type:row.type,id:row.id})"></Icon>
              <Icon size="25" style="color: red" type=ios-trash @click="removeDict(row.id)"></Icon>
            </div>
          </template>
        </Table>
        <div style="margin: 10px;">
          <div style="float: right;">
            <Page style="height: 40px" show-total show-elevator show-sizer
                  :total="dictInfoGrid.pageData.total"
                  :current="dictInfoGrid.pageData.start"
                  :page-size="dictInfoGrid.pageData.size"
                  @on-change="changePageNum"
                  @on-page-size-change="changePageSize"
            />
          </div>
        </div>
      </TabPane>
      <TabPane label="Windows" icon="logo-windows">
      </TabPane>

      <TabPane label="Linux" icon="logo-tux">标签三的内容</TabPane>

    </Tabs>


    <Drawer style="background: rgba(0, 0, 0, 0.36);"
            :title="dictInfoForm.title"
            v-model="dictInfoForm.show"
            width=700
            :styles="styles">
      <Card :bordered="false" style="background: rgba(0, 0, 0, 0.16);height: 100%">
        <Form ref="dictInfoForm" :model="dictInfoForm.data"
              :rules="dictInfoForm.validate" :label-width="80">
          <FormItem label="字典名称" label-position="left" prop="groupName">
            <Input class="" v-model="dictInfoForm.data.groupName"
                   placeholder=""/>
          </FormItem>
          <FormItem label="字典代码" label-position="left" prop="groupName">
            <Input class="" v-model="dictInfoForm.data.dictCode"
                   placeholder=""/>
          </FormItem>
          <FormItem label="类型" label-position="left" prop="type">
            <Input class="" v-model="dictInfoForm.data.type"
                   placeholder=""/>
          </FormItem>
        </Form>
        <div class="demo-drawer-footer" style="margin: auto">
          <Button style="margin-right: 8px" @click="dictInfoForm.show = false">取消</Button>
          <Button type="primary" @click="saveOrUpdateDict('dictInfoForm')">提交</Button>
        </div>
      </Card>
    </Drawer>

    <IconSelect v-on:iconSelectListen="listener"
                :clientHeight="clientHeight"
                :show="dictInfoForm.iconSelect"></IconSelect>
    <ChildTable v-on:childTableListen="listener"
                :client-height="clientHeight"
                :show="dictInfoGrid.childTable.show"></ChildTable>

    <Drawer v-model="dictInfoForm.dictView.show" width="700" title="详情">
      <Card :bordered="false" style="background: rgba(0, 0, 0, 0.16);height: 100%">
        <Row type="flex" v-for="(item ,index) in dictInfoGrid.columns"
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
                <Icon size="30" :type="dictInfoForm.dictView.data[item.key||item.slot]"/>
              </div>
              <div v-else>
               <span v-if="dictInfoForm.dictView.data[item.key||item.slot]" style="color: yellow">
                    {{(dictInfoForm.dictView.data[item.key||item.slot])}}
               </span>
               <span v-else></span>
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
  import ChildTable from "../../components/ChildTable";

  export default {
    components: {ChildTable, IconSelect},
    props: ["clientHeight"],
    data() {
      return {
        styles: {
          height: 'calc(100% - 55px)',
          overflow: 'auto',
          paddingBottom: '53px',
          position: 'static'
        },
        dictInfoGrid: {
          childTable:{
            show:false
          },
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
              title: '分组名称',
              key: 'groupName',
              //width: 200,
              //fixed: 'left'
            },
            {
              title: '类型',
              key: 'type',
              //width: 200,
              //fixed: 'left'
            },
            {
              title: '字典代码',
              key: 'dictCode',
              //width: 200,
              //fixed: 'left'
            },
            {
              title: '创建人',
              key: 'createUserId',
              //width: 200,
              //fixed: 'left'
            },
            {
              title: '创建时间',
              key: 'createTime',
              //width: 200,
              //fixed: 'left'
            },
            {
              title: '修改人',
              key: 'updateUserId',
              //width: 200,
              //fixed: 'left'
            },
            {
              title: '修改时间',
              key: 'updateTime',
              //width: 200,
              //fixed: 'left'
            }
          ],
          data: [],
          pageData: {
            total: 0,
            start: 1,
            size: 10
          }
        },
        dictInfoForm: {
          title: "添加",
          iconSelect: false,
          showField: {
            id: false,
            pid: false,
            groupName: false,
            text: false,
            val: false,
            type: false,
            createUserId: false,
            createTime: false,
            updateUserId: false,
            updateTime: false,
          },
          select: {
            pid: []
          },
          clearData: {
            id: '',
            pid: '',
            groupName: '',
            dictCode:'',
            text: '',
            val: '',
            type: '',
            createUserId: '',
            createTime: '',
            updateUserId: '',
            updateTime: '',
          },
          data: {
            id: '',
            pid: '',
            groupName: '',
            dictCode:'',
            text: '',
            val: '',
            type: '',
            createUserId: '',
            createTime: '',
            updateUserId: '',
            updateTime: '',
          },
          validate: {
            groupName: [
              {required: true, message: '请输入分组名称', trigger: 'blur'},
            ],
            text: [
              {required: true, message: '请输入键', trigger: 'blur'},
            ],
            val: [
              {required: true, message: '请输入值', trigger: 'blur'},
            ],
            type: [
              {required: true, message: '请输入类型', trigger: 'blur'},
            ],
          },
          show: false,
          dictView: {
            data: {},
            show: false
          }
        }
      }
    },
    methods: {
      checkDictName() {

      },
      showChildTable(){
        this.dictInfoGrid.childTable.show=true
      },
      showDictView(index) {
        this.dictInfoForm.dictView.show = true;
        this.dictInfoForm.dictView.data = this.dictInfoGrid.data[index]
      },
      removeDict(id) {
        const that = this;
        that.$Modal.confirm({
          title: "警告",
          content: "确定要删除吗？",
          onOk: function () {
            that.$util.req("/api/security/dict/delDict")
              .setJsonDataStr({ids: id})
              .setSuccessfn(function (result) {
                if (result.code === 1000) {
                  that.$Notice.success({
                    title: '操作成功',
                    desc: 'Dict删除成功'
                  });
                  that.searchDictList()
                } else {
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
          this.dictInfoForm.showField.pid = false
          this.dictInfoForm.showField.viewPath = false
          this.dictInfoForm.showField.groupName = false
          this.dictInfoForm.data.pid = '0'
          callBack()
        } else if (value == "2" || value == "3") {
          if (value == "2") {
            this.dictInfoForm.showField.groupName = true
          }
          if (value == "3") {
            this.dictInfoForm.showField.groupName = false
          }
          this.dictInfoForm.showField.pid = true
          this.dictInfoForm.showField.viewPath = true
        }
      },
      listener: function (fun) {
        fun(this)
      },
      saveOrUpdatedictInfoForm(actiontype, data) {
        if (actiontype === "add") {
          this.dictInfoForm.title = "添加"
          this.dictInfoForm.data = this.dictInfoForm.clearData
          this.changeType(data.type)
        } else if (actiontype === "update") {
          this.dictInfoForm.title = "修改"
          const that = this;
          that.$util.req("/api/security/dict/searchDict")
            .setJsonDataStr({id: data.id})
            .setSuccessfn(function (result) {
              let dictInfo = result.data
              that.dictInfoForm.data = dictInfo
            })
            .go(that)
        }
        this.dictInfoForm.show = true
      },
      saveOrUpdateDict(name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            const that = this;
            that.$util.req("/api/security/dict/saveOrUpdateDict")
              .setJsonDataStr(that.dictInfoForm.data)
              .setSuccessfn(function (result) {
                if (result.code === 1000) {
                  that.$Notice.success({
                    title: '操作成功',
                    desc: 'dict' + that.dictInfoForm.title + '成功'
                  });
                  that.searchDictList();
                  that.dictInfoForm.show = false;
                }
              })
              .setErrorfn(function (e) {
              })
              .go(this)
          } else {
            //this.$Message.error('Fail!')
          }
        })
      },
      searchDictList() {
        const that = this;
        that.$util.req("/api/security/dict/searchDictList")
          .setJsonDataStr({
              start:that.dictInfoGrid.pageData.start,
              size:that.dictInfoGrid.pageData.size
          })
          .setSuccessfn(function (result) {
            if (result.code === 1000) {
              that.dictInfoGrid.data = result.data.records
              that.dictInfoGrid.pageData.start = result.data.start;
              that.dictInfoGrid.pageData.total = result.data.total;
            }
          })
          .setErrorfn(function (e) {
          })
          .go(that)
      },
      changePageNum(pageNum) {
        this.dictInfoGrid.pageData.start = pageNum;
        this.searchDictList()
      },
      changePageSize(pageSize) {
        this.dictInfoGrid.pageData.size = pageSize;
        this.searchDictList()
      }
    }
    ,
    mounted() {
      this.searchDictList();
    }
  }
</script>

<style>

</style>
