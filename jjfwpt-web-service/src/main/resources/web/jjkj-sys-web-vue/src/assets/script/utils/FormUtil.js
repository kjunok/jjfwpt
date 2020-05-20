import RulesUtil from "./RulesUtil";

const FormElement =[
  {design:true,key:"input",icon:"form-input",show:true,type:'input',label:'输入框',props:{},style:{width:"100%"},events:{}},

  {design:true,key:"textarea",icon:"ios-create-outline",show:true,type:'input',label:'文本域',props:{type: 'textarea'},style:{width:"100%"},events:{}},

  {design:true,key:"select",icon:"ios-arrow-down",url:null,urlParam:{},dataModal:{label:"",value:""},refresh:false,show:true,type:'select',label:'下拉框',props:{transfer:true},value:"",events:{},
    children: [{label: '选项1',value:'1'},{label: '选项2',value:'2'}],style:{width:"100%"}},

  {design:true,key:"radioGroup",icon:"ios-radio-button-on",url:null,urlParam:{},dataModal:{label:"",value:""},refresh:false,show:true,type:'radioGroup',label:'单选按钮',value:"",multiple:false,props:{},events:{},
    children: [{label: '选项1',value:'1',border:true},{label: '选项2',value:'2',border:true}],rules:{},style:{width:"100%"}},

  {design:true,key:"checkbox",icon:"ios-checkbox-outline",show:true,type:'checkbox',border:true,label: '单复选框',style:{width:"100%"}},

  {design:true,key:"checkboxGroup",def:[],icon:"md-checkbox-outline",url:null,urlParam:{},dataModal:{label:"",value:""},refresh:false,show:true,type:'checkboxGroup',props:{},label:'多复选框',value:[],events:{},
    children: [{label: '选项1',value:'1',border:true},{label: '选项2',value:'2',border:true}],style:{width:"100%"}},

  {design:true,key:"switch",icon:"ios-switch",show:true,type: 'switch',label: '开关按钮',props:{},value:"",events:{},
    children: [{type: 'span',slot:"open",value: '1',label: '开'},{type: 'span',slot:"close",value: '0',label: '关'}]},

  {design:true,key:"date",icon:"ios-calendar-outline",label:'日期选择',show:true,type:'date',props:{type: 'date'},style:{width:"100%"}},

  {design:true,key:"time",icon:"ios-time-outline",label:'时间选择',show:true,type: 'time',props: {type: 'timerange'},style:{width:"100%"}},

  {design:true,key:"inputNumber",icon:"ios-calculator",label:'数字框',show:true,type: 'inputNumber',style:{width:"100%"}}
];
const DesignFormAllOptions = function () {
  let options =  {
      "design$$label": {
        label: '名称',
        show: true,
        type: 'input',
        key: 'design$$label',
        props: {
          placeholder: '字段名称...'
        },
        rules: {
          required: true,
          trigger: 'blur',
          message:"请输入字段名称"
        }
    },
      "design$$key": {
        label: '代码',
        show: true,
        value:"",
        type: 'input',
        key: 'design$$key',
        props: {
          placeholder: '请输入字段代码...'
        },
        rules: {
          required: true,
            message: '请输入字段代码',
            trigger: 'blur'
        }
      },
    "design$$placeholder": {
      label: '提示',
      show: true,
      type: 'input',
      key: 'design$$placeholder',
      props: {
        placeholder: '字段提示...'
      },
      rules: {
        required: false,
        trigger: 'blur'
      }
    },
      "design$$show": {
        label: '默认显示',
        show: true,
        value: true,
        type: 'checkbox',
        key: 'design$$show'
      },
      "rule_required": {
        label: '是否必填',
        show: true,
        type: 'radioGroup',
        key: 'rule_required',
        props: {
          value: "true",
          size:"small"
        },
      children: [
        {
          label: '是',
          value: "true",
          border: true
        },
        {
          label: '否',
          value: "false",
          border: true
        }
      ]
    },
      "design$$multiple":{
        label: '是否多选',
        value:false,
        show: true,
        type: 'checkbox',
        key: 'design$$multiple',
        props: {
        },
      },
      "design$$data_resource": {
        label: '数据类型',
        value:"custom",
        show: true,
        type: 'radioGroup',
        key: 'design$$data_resource',
        props: {
          size:"small"
        },
      children: [
        {
          label: '自定义',
          value: 'custom',
          border: true
        },
        {
          label: '数据字典',
          value: 'dict',
          border: true
        },
        {
          label: 'API',
          value: 'api',
          border: true
        }
      ],
        events: {
        'on-change'(a) {
          switch (a) {
            case 'dict':
              options["design$$data_resource_api"]['show'] = false;
              options["design$$data_resource_dict"]['show'] = true;
              options["design$$data_resource_custom"]['show'] = false;
              break;
            case 'custom':
              options["design$$data_resource_api"]['show'] = false;
              options["design$$data_resource_dict"]['show'] = false;
              options["design$$data_resource_custom"]['show'] = true;
              break;
            case 'api':
              options["design$$data_resource_dict"]['show'] = false;
              options["design$$data_resource_custom"]['show'] = false;
              options["design$$data_resource_api"]['show'] = true;
              break;
          }
        }
      }
    },
      "design$$data_resource_dict": {
        label: "数据字典",
        show:false,
        type: 'select',
        key: 'design$$data_resource_dict',
        value:"",
        children: [
          {
            value: 'shenzhen',
            label: '深圳'
          },
          {
            label: '韶关',
            value: 'shaoguan'
          }
        ]
    },
      "design$$rules": {
        label: '表单校验',
        value:"",
        show: true,
        type: 'rules',
        key: 'design$$rules',
        props:{
          'show-header':false,
          size:'small'
        }
    },
    "design$$data_resource_api":{
      show:false,
      label:"Api",
      type:"api",
      refresh:false,
      children:[],
      key:"design$$data_resource_api",
      props:{
        'show-header':false,
        size:'small'
      }
    },
      "design$$data_resource_custom":{
        show:true,
        label:"自定义键值",
        type:"table",
        key:"design$$data_resource_custom",
        props:{
        'show-header':false,
          size:'small'
      }
    },
      "design$$event_change_val": {
      label: '显隐控制',
        type: 'event',
        key: 'design$$event_change_val',
        value:[],
        props:{
        'show-header':false,
          size:"small"
      }
    }
    };
  return options;
};
const GetDesignFormOptionByType = function(type,data){
    let formFields = [];
    let formFieldOptions ={};
    let designFormAllOptions = DesignFormAllOptions();
    formFields.push("design$$label","design$$key","design$$placeholder","design$$show");
    switch (type) {
      case "input":
        break;
      case "radio":
        break;
      case "radioGroup":
        formFields.push(
          "design$$data_resource",
          "design$$data_resource_dict",
          "design$$data_resource_custom",
          "design$$data_resource_api",
          "design$$event_change_val");
        break;
      case "checkbox":
        break;
      case "checkboxGroup":
        formFields.push(
          "design$$data_resource",
          "design$$data_resource_dict",
          "design$$data_resource_custom",
          "design$$data_resource_api",
          "design$$event_change_val");
        break;
      case "switch":
        designFormAllOptions.design$$data_resource_custom.show=true;
        formFields.push(
          "design$$data_resource_custom",
          "design$$event_change_val");
        break;
      case "select":
        formFields.push(
          "design$$multiple",
          "design$$data_resource",
          "design$$data_resource_dict",
          "design$$data_resource_custom",
          "design$$data_resource_api",
          "design$$event_change_val");
        break;
      case "slider":
        break;
      case "date":
        break;
      case "time":
        break;
      case "cascader":
        break;
      case "inputNumber":
        break;
      case "upload":
        break;
      case "colorPicker":
        break;
    }
    formFields.push("design$$rules");
    if(data){
      formFields.map(field=>{
        formFieldOptions[field] = data[field];
      });
    }else {
      formFields.map(field=>{
        formFieldOptions[field] = designFormAllOptions[field];
      });
    }
    return formFieldOptions;
};

const EmptyFormData = function (formOption) {
    let formData ={};
    let forEachFormOption = function(formItem,key) {
      let elementType = formItem.type;
      if(!key){
        key = formItem.key;
      }
      switch (elementType) {
        case "input":
          formData[key]=formItem.value||"";
          break;
        case "radio":
          formData[key]=formItem.value||"";
          break;
        case "radioGroup":
          formData[key]=formItem.value||"";
          break;
        case "checkbox":
          formData[key]=formItem.value||"";
          break;
        case "checkboxGroup":
          formData[key]=formItem.value||[];
          break;
        case "switch":
          formData[key]=formItem.value||"";
          break;
        case "select":
          formData[key]=formItem.value||"";
          break;
        case "slider":
          formData[key]=formItem.value||"";
          break;
        case "date":
          formData[key]=formItem.value||"";
          break;
        case "time":
          formData[key]=formItem.value||"";
          break;
        case "cascader":
          formData[key]=formItem.value||"";
          break;
        case "inputNumber":
          formData[key]=formItem.value||1;
          break;
        case "upload":
          formData[key]=formItem.value||"";
          break;
        case "colorPicker":
          formData[key]=formItem.value||"";
          break;
        case "table":
          formData[key]=formItem.value||[];
          break;
        case "event":
          formData[key]=formItem.value||[];
          break;
        case "rules":
          formData[key]=formItem.value||[];
          break;
        case "api":
          formData[key]=formItem.value||{
            params:[],
            response:{},
            api:"",
            label:"",
            value:"",
            paramsVal:{}
          };
          break;
        default:
          console.log(elementType);
          break;
      }
    };
    if(formOption instanceof Array){
      for(let key in formOption){
        let formItem = formOption[key];
        forEachFormOption(formItem,false)
      }
    }else{
      for(let key in formOption){
        let formItem = formOption[key];
        forEachFormOption(formItem,key)
      }
    }
    return formData;
};
const ToMapFormOption=function (optionsArray) {
  let mapForOption={};
  optionsArray.map(item=>{
    mapForOption[item.key]=item;
    //删除设计标记
    delete mapForOption[item.key].design;
  });
  return mapForOption;
};
const ToArrayFormOption=function (optionsMap) {
  let mapForArray=[];
  for(let key in optionsMap){
    mapForArray.push(optionsMap[key]);
  }
  return mapForArray;
};
const FormCreate = function () {
  let obj = new Object();
  obj.setFormProps= function (opt) {
    obj.formProps = opt||{'label-width': 60};
    return this;
  };
  obj.setFormData= function (opt) {
    obj.formData = opt||{};
    return this;
  };
  obj.setFormItem= function (opt) {
    obj.formItem= opt||[];
    return this;
  };
  obj.setSubmit = function (opt) {
    obj.submit=opt;
    return this;
  }
};
const Create = function () {
    let formCreate = FormCreate();
};

export default {
  Create,
  FormElement,
  ToMapFormOption,
  ToArrayFormOption,
  EmptyFormData,
  GetDesignFormOptionByType
}
