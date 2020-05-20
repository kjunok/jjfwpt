<template>
    <Row type="flex" :gutter="10"  style="padding: 0px;margin: 0px 6px 0px -12px;">
      <Col span="4">
        <Card title="元素" :style="{height:clientHeight+'px'}"  :padding="0" style="padding: 0px!important;width:100%;">
          <Form :label-width="80" :model="formOptionsData">
              <draggable  :list="formOptions" v-bind:options="{group:{name:'form',pull:'clone',put:'false'},touchStartThreshold: 100,sort:false}"
                         :clone="cloneData">
                <Card v-for="item in formOptions" :padding="10"  style="width: 45%;text-align: left;float: left;cursor: move;line-height: 5px;"
                  :key="item.key">
                  <Icon size="15" style="font-weight: bold" :type="item.icon"/>
                  {{item.label}}
                </Card>
              </draggable>
          </Form>
        </Card>
      </Col>
      <Col span="7">
        <Card style="width:100%;background: #f5f7f9;" :padding="0" :style="{height:clientHeight+'px'}" class="formDrag">
          <p slot="title">设计 </p>
          <ButtonGroup slot="extra"  style="margin-top: -10px;">
            <Button @click="viewForm">预览表单</Button>
            <Button @click="saveOrUpdateForm">保存表单</Button>
          </ButtonGroup>
          <Form label-position="right" :label-width="100" :model="formInData">
            <draggable
              @add="formInEnd"
              @end="formInEnd" :list="formIn"
              :style="{height:clientHeight-45+'px'}"
              style="overflow-y:auto"
              v-bind:options="{group:{name:'form'},disabled:false}">
              <transition-group tag="div" :style="{height:clientHeight-55+'px'}" type="transition" :name="'flip-list'">
                <Card @mouseup.native="settings(item,index)" :padding="8" shadow
                      style="margin-bottom:10px;cursor:move;min-height: 93px; overflow: hidden;"
                     v-for="(item,index) in formIn" :key="item.key">
                    <ButtonGroup style="position: absolute;right: -1px;top:-1px" class="formItemBtn" size="small">
                      <Button  disabled style="background-color: #fff;">
                        <span v-if="item.show" style="color: green"><Icon type="md-eye" size="18" /></span>
                        <span v-if="!item.show" style="color: red"><Icon type="md-eye-off" size="18"/></span>
                      </Button>
                      <Button  disabled style="width: 150px;color: #2d8cf0;background-color: #fff;">{{item.key}}</Button>
                      <Button  @click="remove(index,item.key)">
                        <span><Icon type="ios-trash" size="18" /></span>
                      </Button>
                    </ButtonGroup>
                    <div v-if="selectIndex == index" style="height:100%;width: 3px;
                    display: block;background:#2d8cf0;right: 0px;top:0px;position: absolute;z-index: 100"></div>
                  <br/>
                  <FormMaker :options="item" :single-data="true" :form-data="formInData"/>
                </Card>
              </transition-group>
            </draggable>
          </Form>
        </Card>
      </Col>
      <Col span="13" >
        <Card  style="width:100%;overflow-y: auto;position: relative;" title="属性" :padding="10">
          <Form label-position="right" :label-width="90" :model="optionsData" :style="{height:clientHeight-65+'px'}" style="overflow-y: auto;">
            <FormMaker :options="options"
                       :form-data="optionsData"
                       :design-form-options="formIn"
                       :design-select-data="selectData"
                       :single-data="false" />
          </Form>
        </Card>
      </Col>
      <PreviewWebForm v-on:PreviewWebFormListen="PreviewWebFormListen"
                      :view-form-option="viewFormOption"
                      :viewFormData="viewFormData"
                      :openWebForm="openWebForm"/>
    </Row>
</template>
<script>
  import $ from "jquery"
  import draggable from 'vuedraggable'
  import CommonUtil from "../../assets/script/utils/CommonUtil";
  import FormUtil from "../../assets/script/utils/FormUtil";
  import PreviewWebForm from "../../components/previewDesignForm/PreviewWebForm";
  import MsgUtil from "../../assets/script/utils/MsgUtil";
  export default {
    name: 'formPlugin',
    components: {PreviewWebForm, draggable },
    watch: {
    },
    data () {
      return {
        clientHeight:CommonUtil.ClientHeight(85),
        openWebForm:false,
        viewFormData:{},
        viewFormOption:[],
        chooseItem:null,
        formIn:[],
        formInData:{},
        formInDesign:{},
        formOptionsData:{},
        formOptions:FormUtil.FormElement,
        optionsData:{},
        options:{},
        selectData:{},
        selectIndex:0,
      }
    },
    methods:{
      PreviewWebFormListen(fun){
        fun(this);
      },
      viewForm(){
        this.openWebForm = true;
        this.viewFormOption = FormUtil.ToMapFormOption(CommonUtil.Clone(this.formIn));
        this.viewFormData = FormUtil.EmptyFormData(this.viewFormOption);
      },
      saveOrUpdateForm(){
        MsgUtil.MODAL_INFO({
          options:FormUtil.ToMapFormOption(CommonUtil.Clone(this.formIn))
        })
      },
      formInEnd(evt){
        let item = this.formIn[evt.newIndex];
        this.settings(item,evt.newIndex);
      },
      settings(item,index){
        if(item) {
          item.index = index;
          this.selectIndex = index;
          let options = function (_this) {
            if (_this.formInDesign[item.key]) {
              return _this.formInDesign[item.key]["option"];
            } else {
              return false;
            }
          }(this) || function (_this) {
            _this.formInDesign[item.key] = {};
            _this.formInDesign[item.key]["option"] = FormUtil.GetDesignFormOptionByType(item.type);
            _this.formInDesign[item.key]["data"] = FormUtil.EmptyFormData(_this.formInDesign[item.key]["option"]);
            return _this.formInDesign[item.key]["option"];
          }(this);
          this.options = options;
          this.optionsData = this.formInDesign[item.key]["data"];
          this.selectData = item;
          this.optionsData.design$$label = item.label;
          this.optionsData.design$$key = item.key;
        }else{
          this.options = {};
          this.optionsData = {};
          this.selectData = {};
          this.selectIndex = -1;
        }
        console.log(this.optionsData)
      },
      randomKey(len){
        return CommonUtil.RandomCode(len)
      },
      remove(index,key){
        this.formIn.splice(index, 1);
        this.settings(null,null);
        delete this.formInData[key];
      },
      cloneData(data) {
        let chooseItem = CommonUtil.Clone(data);
        let key =this.randomKey(10).toLowerCase();
        chooseItem.key=key;
        this.formInData[key]="";
        return chooseItem;
      }
    },
    created(){
      let _this = this;
      CommonUtil.RegHeightChangeFun("formPlugin",function (height) {
        _this.clientHeight = height-85;
      });
    },
    destroyed(){
      CommonUtil.UnRegHeightChangeFun("formPlugin");
    }
  }
</script>

<style >
  .flip-list-move {
    transition: transform 0.5s;
  }
  /*选中某一行高亮*/
  .ivu-table-row-highlight td {
    background-color: #2d8cf0!important;
  }
</style>
