import {Table} from "view-design";
import CommonUtil from "../../assets/script/utils/CommonUtil";
import RulesUtil from "../../assets/script/utils/RulesUtil";
import ReqUtil from "../../assets/script/utils/ReqUtil";
const rules =RulesUtil;
const componentObj = {
    input: generateInputComponent,
    button: generateButtonComponent,
    buttonGroup: generateButtonGroupComponent,
    reset: generateResetComponent,
    submit: generateSubmitComponent,
    icon: generateIconComponent,
    radio: generateRadioComponent,
    radioGroup: generateRadioGroupComponent,
    checkbox: generateCheckboxComponent,
    checkboxGroup: generateCheckboxGroupComponent,
    switch: generateSwitchComponent,
    select: generateSelectComponent,
    slider: generateSliderComponent,
    date: generateDateComponent,
    time: generateTimeComponent,
    cascader: generateCascaderComponent,
    inputNumber: generateInputNumberComponent,
    rate: generateRateComponent,
    upload: generateUploadComponent,
    colorPicker: generateColorPickerComponent,
    col: generateColComponent,
    row: generateRowComponent,
    formItem: generateFormItemComponent,
    table:generateKeyValTableComponent,
    event:generateEventTableComponent,
    rules:generateRulesTableComponent,
    api:generateApiTableComponent
};

function generateInputComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    const key = obj.key? obj.key : '';
    let children = [];
    if (obj.children) {
        children = obj.children.map(item => {
            let component;
            if (item.type == 'span') {
                component = h('span', {
                    slot: item.slot
                }, [item.text])
            } else {
                let func = componentObj[item.type];
                component = func? func.call(vm, h, formData, item, vm) : null
            }
            return component
        })
    }
    return h('Input', {
        props: {
            value: key? formData[key] :  function (){formData[key]=''; return ''}(),
            ...obj.props
        },
        style: obj.style,
        on: {
            ...translateEvents(obj.events, vm,options),
            /*设计表单时顺便修改设计区元素的key属性*/
            'on-blur':function(val){
              /*设计表但时反填设计界面*/
              try{
                switch (key) {
                  case "design$$label":
                    designFormOptions[designSelectData.index]["label"]=val.target.value;
                    break;
                  case "design$$key":
                    designFormOptions[designSelectData.index]["key"]=val.target.value;
                    break;
                  case "design$$placeholder":
                    designFormOptions[designSelectData.index]["props"]["placeholder"]=val.target.value;
                    refreshDesignSelectData(designFormOptions,designSelectData);
                    break;
                }
              }catch (e) {}
            },
            input(val) {
                if (key) {
                    formData[key] = val
                }
            }
        },
        slot: obj.slot
    }, children)
}

function generateButtonComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    return h('Button', {
        props: obj.props,
        slot: obj.slot,
        style: obj.style,
        on: obj.events
    }, [obj.text])
}

function generateButtonGroupComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    const components = obj.children.map(item => {
        return h('Button', {
            props: item.props? item.props : item,
            slot: item.slot,
            style: item.style,
            on: item.events
        }, [item.text])
    })

    return h('ButtonGroup', {
        props: obj.props,
        style: obj.style,
        slot: obj.slot,
    }, [components])
}

function generateSubmitComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    const components = []
    const submit = h('Button', {
        props: obj.props,
        style: obj.style,
        on: {
            click() {
                vm.$refs['form'].validate((valid) => {
                    if (valid) {
                        obj.success.call(vm, formData)
                    } else {
                        obj.fail.call(vm, formData)
                    }
                })
            }
        }
    }, [obj.text])

    components.push(submit)

    if (obj.reset) {
        const reset = h('Button', {
            props: obj.reset.props,
            style: {
                marginLeft: '10px',
                ...obj.style,
            },
            on: {
                click() {
                    vm.$refs['form'].resetFields()
                }
            }
        }, [obj.reset.text]);

        components.push(reset)
    }

    return h('div', components)
}

function generateResetComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    return h('Button', {
        props: obj.props,
        style: obj.style,
        slot: obj.slot,
        on: {
            click() {
                vm.$refs['form'].resetFields()
            }
        }
    }, [obj.text])
}

function generateIconComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    return h('Icon', {
        props: obj.props,
        style: obj.style,
        slot: obj.slot,
    })
}

function generateRadioComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    const key = obj.key? obj.key : '';

    return h('Radio', {
        props: {
            value: key? formData[key] : function (){formData[key]=false; return false}(),
            ...obj.props
        },
        style: obj.style,
        slot: obj.slot,
        on: {
            ...translateEvents(obj.events, vm,options),
            input(val) {
                if (key) {
                    formData[key] = val
                }
            }
        }
    }, [obj.text])
}

function generateRadioGroupComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
  let components = [];
  const key = obj.key? obj.key : '';
  let radioGroup = function (components) {
    return h('RadioGroup', {
      props: {
        value: key? formData[key] : function (){formData[key]=''; return ''}(),
        ...obj.props
      },
      style: obj.style,
      slot: obj.slot,
      on: {
        ...translateEvents(obj.events, vm,options),
        input(val) {
          if (key) {
            formData[key] = val
          }
        }
      }
    }, [components])
  };
  if(obj.url&&!obj.refresh&&!obj.design){
    let urlParams={};
    for(let key in obj.urlParam){
      urlParams[key] =  [obj.urlParam[key]]
    }
    ReqUtil.doApiPost(obj.url)
      .setData(urlParams)
      .setSuccessFunction(function (result) {
        obj.children = result.data.map(item => {
          return {label:item[obj.dataModal.label],value:item[obj.dataModal.value]}
        });
        obj.refresh = true;
      }).isFormBody().execute();
    return radioGroup(components);
  }else if(obj.children){
    components = obj.children.map(child => {
      return h('Radio', {
        style:{
          marginBottom:"3px"
        },
        props: {
          ...(child.props? child.props : child),
          label:child.value,
          border:true
        }
      }, [child.label])
    });
   return  radioGroup(components)
  }
}

function generateCheckboxComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    const key = obj.key? obj.key : '';
    return h('Checkbox', {
        props: {
          value: key? formData[key] : function (){formData[key]=''; return ''}(),
          ...obj.props
        },
        style: obj.style,
        slot: obj.slot,
        on: {
            ...translateEvents(obj.events, vm,options),
            input(val) {
              try{
                switch (obj.key) {
                  case "design$$show"://控制表单元素显示隐藏
                    designFormOptions[designSelectData.index].show=val;
                    break;
                  case "design$$multiple"://控制select元素为单选或单选
                    designFormOptions[designSelectData.index].multiple=val;
                    refreshDesignSelectData(designFormOptions,designSelectData);
                    break;
                }
              }catch (e) {}
              if (key) {
                formData[key] = val;
              }
            }
        }
    }, [obj.text])

}

function generateCheckboxGroupComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
  let components = [];
  const key = obj.key? obj.key : '';
  let checkBoxGroup = function (components) {
    return h('CheckboxGroup', {
      props: {
        value: key? formData[key] : function (){formData[key]=[]; return []}(),
        ...obj.props,
      },
      style: obj.style,
      slot: obj.slot,
      on: {
        ...translateEvents(obj.events, vm,options),
        input(val){
          if (key) {
            formData[key] = val
          }
        }
      }
    }, [components])
  };
  if(obj.url&&!obj.refresh&&!obj.design){
    let urlParams={};
    for(let key in obj.urlParam){
      urlParams[key] = formData[obj.urlParam[key]]
    }
    ReqUtil.doApiPost(obj.url)
      .setData(urlParams)
      .setSuccessFunction(function (result) {
        obj.children = result.data.map(item => {
          return {label:item[obj.dataModal.label],value:item[obj.dataModal.value]}
        });
        obj.refresh = true;
        formData[key]=[];
      }).isFormBody().execute();
    return checkBoxGroup(components);
  }else if(obj.children){
        components = obj.children.map(child => {
            return h('Checkbox', {
              style: {
                marginBottom:"3px",
              },
                props: {
                ...(child.props? child.props : child),
                  label:child.value+"",
                  border:true
                }
            }, [child.label])
        })
    return checkBoxGroup(components);
    }
}

function generateSwitchComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    const key = obj.key? obj.key : '';
    let components = [];
    let true_false = {};
    if (obj.children) {
      let number =0;
        components = obj.children.map(item => {
            if(item.slot==="open"){
              true_false.trueValue=item.value+"";
            }else if(item.slot==="close"){
              true_false.falseValue=item.value+"";
            }
            let temp;
            if (item.type == 'icon') {
              temp = generateIconComponent(h, formData, item)
            } else {
              temp = h('span',{
                slot: item.slot
              }, [item.label])
            }
            number++;
            return temp
        })
    } 

    return h('i-switch', {
        props: {
            value: key? formData[key]  : function (){formData[key]=false; return false}(),
            size:"large",
            ...true_false,
            ...obj.props
        },
        style:{
          ... obj.style
        },
        slot: obj.slot,
        on: {
            ...translateEvents(obj.events, vm,options),
            input(val) {
                if (key) {
                    formData[key] = val
                }
            }
        },
    }, components)
}

function generateSelectComponent(h, formData = {}, obj, vm,designSelectData,designFormOptions=[],options) {
    const key = obj.key? obj.key : '';
    let components = [];
    let select = function (components) {
      return  h('Select', {
        props: {
          value: key? formData[key]  : function (){
            if(obj.multiple){
              formData[key]=[];
              return []
            }else{
              formData[key]="";
              return ""}
          }(),
          multiple:obj.multiple,
          ...obj.props
        },
        style: obj.style,
        on: {
          ...translateEvents(obj.events, vm,options),
          input(val) {
            if (key) {
              formData[key] = val
            }
          }
        },
        slot: obj.slot
      }, components)
    };
    if(obj.url&&!obj.refresh&&!obj.design){
      let urlParams={};
      for(let key in obj.urlParam){
        urlParams[key] = formData[obj.urlParam[key]]
      }
      ReqUtil.doApiPost(obj.url)
        .setData(urlParams)
        .setSuccessFunction(function (result) {
          obj.children = result.data.map(item => {
            return {label:item[obj.dataModal.label],value:item[obj.dataModal.value]}
          });
          obj.refresh = true;
          if(obj.multiple){
            formData[key]=[];
          }else{
            formData[key]=""
          }
        }).isFormBody().execute();
      return select(components);
    }else if(obj.children){
      components = obj.children.map(item => {
        item.label+="";
        item.value+="";
        if (item.type == 'optionGroup') {
          return h('OptionGroup', {
            props: item.props? item.props : item
          },
          item.children.map(child => {
            return h('Option', {
              props: child.props? child.props : child
            })
          }))
        } else {
          return h('Option', {
            props: item.props? item.props : item
          })
        }
      });
      return select(components);
    }
}

function generateSliderComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    const key = obj.key? obj.key : '';

    return h('Slider', {
        props: {
            value: key? formData[key]  : function (){formData[key]=''; return ''}(),
            ...obj.props
        },
        style: obj.style,
        slot: obj.slot,
        on: {
            ...translateEvents(obj.events, vm,options),
            input(val) {
                if (key) {
                    formData[key] = val
                }
            }
        }
    })
}

function generateDateComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    const key = obj.key? obj.key : ''
    const type = obj.props.type
    return h('DatePicker', {
        props: {
            transfer:true,
            value: key? formData[key]  : function (){formData[key]=''; return ''}(),
            ...obj.props
        },
        style: obj.style,
        slot: obj.slot,
        on: {
            ...translateEvents(obj.events, vm,options),
            input(date) {
                if (key) {
                    if (type.includes('datetime')) {
                        if (Array.isArray(date)) {
                            formData[key] = date? date.map(item => item? item.toLocaleDateString() 
                                                  + ' ' + item.toTimeString().split(' ')[0] : '') : []
                        } else {
                            formData[key] = date? date.toLocaleDateString() + ' ' + date.toTimeString().split(' ')[0] : ''
                        }
                    } else {
                        if (Array.isArray(date)) {
                            formData[key] = date? date.map(item => item? item.toLocaleDateString() : '') : []
                        } else {
                            formData[key] = date? date.toLocaleDateString() : ''
                        }
                    }
                }
            },
        }
    })
}

function generateTimeComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    const key = obj.key? obj.key : '';

    return h('TimePicker', {
        props: {
            transfer:true,
            value: key? formData[key] : function (){formData[key]=''; return ''}(),
            ...obj.props
        },
        style: obj.style,
        slot: obj.slot,
        on: {
            ...translateEvents(obj.events, vm,options),
            input(val) {
                if (key) {
                    formData[key] = val
                }
            }
        }
    })
}

function generateCascaderComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    const key = obj.key? obj.key : ''
    
    return h('Cascader', {
        props: {
            value: key? formData[key] : function (){formData[key]=[]; return []}(),
            ...obj.props
        },
        style: obj.style,
        slot: obj.slot,
        on: {
            ...translateEvents(obj.events, vm,options),
            input(val) {
                if (key) {
                    formData[key] = val
                }
            }
        }
    })
}

function generateInputNumberComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    const key = obj.key? obj.key : '';

    return h('InputNumber', {
        props: {
            value: key? formData[key] : function (){formData[key]=null; return null}() ,
            ...obj.props
        },
        style: obj.style,
        slot: obj.slot,
        on: {
            ...translateEvents(obj.events, vm,options),
            input(val) {
                if (key) {
                    formData[key] = val
                }
            }
        }
    })
}

function generateRateComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    const key = obj.key? obj.key : ''

    return h('Rate', {
        props: {
            value: key? formData[key] : 0,
            ...obj.props
        },
        slot: obj.slot,
        style: obj.style,
        on: {
            ...translateEvents(obj.events, vm,options),
            input(val) {
                if (key) {
                    formData[key] = val
                }
            }
        }
    })
}

function generateUploadComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    let components = []

    if (obj.children) {
        components = obj.children.map(item => {
            let func = componentObj[item.type]
            return func? func.call(vm, h, formData, item, vm) : null
        })
    }
    return h('Upload', {
        props: obj.props,
        style: obj.style,
        slot: obj.slot,
    }, components)
}

function generateColorPickerComponent(h, formData = {}, obj, vm,designSelectData={},designFormOptions=[],options) {
    const key = obj.key? obj.key : ''

    return h('ColorPicker', {
        props: {
            value: key? formData[key] : '',
            ...obj.props
        },
        style: obj.style,
        slot: obj.slot,
        on: {
            ...translateEvents(obj.events, vm,options),
            input(val) {
                if (key) {
                    formData[key] = val
                }
            }
        }
    })
}

function generateKeyValTableComponent(h, formData = [], obj, vm,designSelectData={},designFormOptions=[]) {
  const key = obj.key? obj.key : '';
  let _comments=[];
  let button_add = h('Button', {
    props: {icon: "md-add-circle",size:'small',cursor: 'pointer'},
    style:{color:"green"},
    on:{click() {
        // if(formData[key].length==obj.childrenLength){
        //   MsgUtil.MODAL_WARNING("提示","最多添加两项")
        //   return;
        // }
        formData[key].push({label:"选项"+(formData[key].length+1),value:(formData[key].length+1)+'',border: true});
        if(designSelectData){
          designSelectData.children=formData[key];
        }
      }}
  },"添加");
  let table_columns_action = {title: '操作',key: 'action',width:105,align: 'left',render: (h, params) => {
      return h('Button', {
        style:{color:'red'},
        props: {icon: "md-remove-circle",size:'small',cursor: 'pointer'},
        on: {
          click(){
            formData[key].splice(params.index, 1);
            if(designSelectData){
              designSelectData.children=formData[key];
            }
          }
        }
      },"删除");
    }};
  let table_columns_def_val = {title: '默认值',key: 'def',width:105,align: 'left',render: (h, params) => {
      return h('Checkbox', {
        props: {
          value:formData[key][params.index].def,
          size:'small',cursor: 'pointer'},
          on: {
          input(val){
            if(designSelectData){
              formData[key][params.index].def=val;
              let type ="single";
              if(designSelectData.type==="checkboxGroup"||designSelectData.multiple){
                type = "multiple";
              }
              if(!(designSelectData.value instanceof Array)&&designSelectData.multiple){
                designSelectData.value=[];
              }
              switch (type) {
                case "multiple":
                  if(val){
                    if(designSelectData.value==undefined){
                      designSelectData.value=[params.row.value]
                    }else{
                      designSelectData.value.push(params.row.value)
                    }
                  }else {
                    let index = designSelectData.value.indexOf(params.row.value);
                    if(index>-1){
                      designSelectData.value.splice(index,1)
                    }
                  }
                  break;
                default:
                  if(val){
                    formData[key].map(row=>{
                      row.def=false;
                    });
                    designSelectData.value=params.row.value;
                    formData[key][params.index].def=val;
                    formData[key] = CommonUtil.Clone(formData[key]);
                  }else {
                    designSelectData.value=""
                  }
                  break;
              }
            }
          }
        }
      },"默认值");
    }};
  let table_columns_label = {key: 'label',render: (h, params) => {
      return h('Input', {
        style:{width:"100%"},
        props: { value: params.row.label,placeholder:"选项"},
        on: {
          input: function (value) {
            formData[key][params.index].label=value;
            if(designSelectData){
              designSelectData.children=formData[key];
            }
          }
        }
      });
    }};
  let table_columns_value = {key: 'value',render: (h, params) => {
      return h('Input', {
        style:{width:"100%"},
        props: { value: params.row.value,placeholder:"选项值"},
        on: {
          input: function (value) {
            formData[key][params.index].value=value;
            if(designSelectData){
              designSelectData.children=formData[key];
            }
          }
        }
      });
    }};
  let table_columns_switch_slot = {key:"slot",width:100,render: (h, params) => {
      return h('span',{},function () {
        if(params.row.slot==="open"){
            return "当打开时"
        }else if(params.row.slot==="close"){
            return "当关闭时"
        }
      }());
    }};
  let table_columns=[];
  switch (designSelectData.type) {
    case "switch":
      table_columns.push(table_columns_def_val,table_columns_switch_slot,table_columns_label,table_columns_value);
      break;
    default:
      _comments.push(button_add);
      table_columns.push(table_columns_def_val,table_columns_label,table_columns_value,table_columns_action);
      break;
  }
  if(formData[key]==0){
    formData[key]=designSelectData.children;
  }
  let table =h('Table', {
    style:{
      width:"100%"
    },
    props: {
      'no-data-text':"点击左上方添加按钮添加键值！",
      draggable:true,
      border:false,
      data:formData[key],
      columns:table_columns,
      ...obj.props
    },
    on:{
      'on-drag-drop':function (index1, index2) {
        formData[key].splice(index2, 1, ...formData[key].splice(index1, 1, formData[key][index2]));
        if(designSelectData){
          designSelectData.children=formData[key];
        }
      }
    },
    data: obj.data
  });
  _comments.push(table);
  return h('div',{},_comments)
}

function generateEventTableComponent(h, formData = [], obj, vm,designSelectData={},designFormOptions=[],options) {
  const key = obj.key? obj.key : '';
  return h('div',{},[
    h('span',{},[
      h('Button', {
        props: {icon: "md-add-circle",size:'small',cursor: 'pointer'},
        style:{color:"green",marginRight:"10px"},
        on:{click() {
            formData[key].push({title:"显示隐藏",type:"show_or_hide",formType:designSelectData.type,withVal:-1,showField:[],hideField:[]})
          }}
      },"显示与隐藏")
    ]),
    h('Table', {
      style:{
        width:"100%"
      },
      props: {
        'no-data-text':"点击左上方按钮添加事件！",
        draggable:true,
        border:false,
        data:formData[key],
        columns:[
          {key: 'value',render: (h, params) => {
           let showFields=[];
           let hideFields=[];
           let withVal=[];
           let hideFieldsUnEabled=false;
           if(designSelectData.type==="checkboxGroup"){
             hideFieldsUnEabled = true;
           }
           designFormOptions.map(item=>{
             /*不控制隐藏或显示自己*/
             if(item.key!==designSelectData.key){
               showFields.push({label:item.label+'（'+item.key+'）',key:item.key});
               hideFields.push({label:item.label+'（'+item.key+'）',key:item.key});
             }
           });
           designSelectData.children.map(item=>{
             withVal.push({label:item.label+'（'+item.value+'）',key:item.value})
           });
            let value = params.row;
            switch (value.type) {
              case "show_or_hide":
                let col_select_withVal = h("Col",{props:{span:4}},[h('Select', {
                  style:{width:"100%"},
                  props: { value: params.row.withVal ,transfer:true,placeholder:"请选择选项"},
                  on: {
                    input: function (value) {
                      formData[key][params.index].withVal=value;
                      extendShowOrHideEvent(formData[key],designSelectData);
                    }
                  }
                },withVal.map(item=>{
                  return h('Option',{
                    props:{value:item.key}
                  },item.label)
                }))]);
                let col_show = h("Col",{props:{span:2}},[
                  h("div",{style:{fontSize:"13px",width:"100%",textAlign:"center"}},"显示：")
                ]);
                let col_show_or_hide = h("Col",{props:{span:4}},[
                  h("div",{style:{fontSize:"13px",width:"100%",textAlign:"center"}},"显示或隐藏：")
                ]);
                let col_select_show_or_hide_field = h("Col",{props:{span:16}},[
                  h('Select', {
                    style:{width:"100%"},
                    props: { value: params.row.showField ,transfer:true,multiple:true,placeholder:"请选择字段"},
                    on: {
                      input: function (value) {
                        formData[key][params.index].showField=value;
                        extendShowOrHideEvent(formData[key],designSelectData);
                      }
                    }
                  },showFields.map(item=>{
                    return h('Option',{
                      props:{value:item.key}
                    },item.label)
                  }))
                ]);
                let col_select_showField = h("Col",{props:{span:8}},[
                  h('Select', {
                    style:{width:"100%"},
                    props: { value: params.row.showField ,transfer:true,multiple:true,placeholder:"请选择字段"},
                    on: {
                      input: function (value) {
                        formData[key][params.index].showField=value;
                        extendShowOrHideEvent(formData[key],designSelectData);
                      }
                    }
                  },showFields.map(item=>{
                    return h('Option',{
                      props:{value:item.key}
                    },item.label)
                  }))
                ]);
                let col_hide = h("Col",{props:{span:2}},[
                  h("div",{style:{fontSize:"13px",width:"100%",textAlign:"center"}},"隐藏：")
                ]);
                let col_select_hideField = h("Col",{props:{span:8}},[
                  h('Select', {
                    style:{width:"100%"},
                    props: { value: params.row.hideField ,disabled:hideFieldsUnEabled,transfer:true,multiple:true,placeholder:"请选择字段"},
                    on: {
                      input: function (value) {
                        formData[key][params.index].hideField=value;
                        extendShowOrHideEvent(formData[key],designSelectData);
                      }
                    }
                  },hideFields.map(item=>{
                    return h('Option',{
                      props:{value:item.key}
                    },item.label)
                  }))
                ]);
                let cols=[];
                cols.push(col_select_withVal);
                if(designSelectData.type === "checkboxGroup"){
                  cols.push(
                    col_show_or_hide,
                    col_select_show_or_hide_field
                  );
                }else {
                  cols.push(
                    col_show,
                    col_select_showField,
                    col_hide,
                    col_select_hideField
                  );
                }
                return h("Row",{},cols);
                break;
              case "cascadeField":
                break;
             }
            }},
          {title: '操作',key: 'value',width:105,align: 'left',
            render: (h, params) => {
              return h('Button', {
                style:{color:'red'},
                props: {icon: "md-remove-circle",size:'small',cursor: 'pointer'},
                on: {
                  click(){
                    formData[key].splice(params.index, 1)
                  }
                }
              },"删除");
            }}],
        ...obj.props
      },
      on:{
        'on-drag-drop':function (index1, index2) {
          formData[key].splice(index2, 1, ...formData[key].splice(index1, 1, formData[key][index2]));
          extendShowOrHideEvent(formData[key],designSelectData);
        }
      },
      data: obj.data
    })
  ])
}
let rules_msg = {
  "isNotNull":"不能为空",
  "isIntGtZero":"只能为整数并且大于0",
  "isIntGteZero":"只能为整数并且大于等于0",
  "isFloatGtZero":"只能为小数并且大于0",
  "isFloatGteZero":"判断浮点数num是否大于或等于0",
  "isEmail":"请输入正确的Email地址！",
  "isNumber":"请输入整数或小数",
  "isDigits":"请输入0到9的数字",
  "isMoney":"请输入Money,如10.07！",
  "isMobile":"请输入手机号码",
  "isTel":"请输入(手机/电话)号码",
  "isQq":"请输入qq号码",
  "isEnglish":"请输入英文字母",
  "isInteger":"请输入整数",
  "isDouble":"请输入小数",
  "isZipCode":"请输入邮政编码",
  "isUrl":"请输入Url",
  "isRightfulString":"请输入合法字符(a-zA-Z0-9-_)",
  "isIdCardNo":"请输入身份证号码",
  "isChinese":"请输入汉字",
  "isChineseChar":"请输入中文字符",
  "stringCheck":"请输入包含中文、英文、数字、下划线等字符"};;
function generateRulesTableComponent(h, formData = [], obj, vm,designSelectData={},designFormOptions=[],options) {
  const key = obj.key? obj.key : '';
  return h('div',{},[
    h('span',{},[
      h('Button', {
        props: {icon: "md-add-circle",size:'small',cursor: 'pointer'},
        style:{color:"green",marginRight:"10px"},
        on:{click() {
            formData[key].push({validator:"",msg:""})
          }}
      },"添加")
    ]),
    h('Table', {
      style:{
        width:"100%"
      },
      props: {
        'no-data-text':"点击左上方按钮添加表单校验！",
        draggable:true,
        border:false,
        data:formData[key],
        columns:[
        {key:'validator',
          render: (h, params) => {
            return h("Row",{props:{gutter:10}},[
              h("Col",{props:{span:12}},[
                h("Select",{
                  style:{width:"100%"},
                  props: { value: params.row.validator ,transfer:true,placeholder:"选择校验方法"},
                  on: {
                    input: function (value) {
                      formData[key][params.index].validator=value;
                      formData[key][params.index].msg=rules_msg[value];
                      designSelectData.validators = formData[key];
                      refreshDesignSelectData(designFormOptions,designSelectData);
                    }
                  }
                },function () {
                  let options=[];
                  for(let key in rules_msg){
                    options.push(h('Option',{
                      props:{value:key}
                    },rules_msg[key]))
                  }
                  return options;
                }())
              ]),
              h("Col",{props:{span:12}},[
                h("Input",{
                  props: { value: params.row.msg ,transfer:true,placeholder:"校验失败提示信息!"},
                  on: {
                    input: function (value) {
                      formData[key][params.index].msg=value;
                      designSelectData.validators = formData[key];
                      refreshDesignSelectData(designFormOptions,designSelectData);
                    }
                  }
                })
              ])
            ]);
          }},
          {title: '操作',key: 'value',width:105,align: 'left',
            render: (h, params) => {
              return h('Button', {
                style:{color:'red'},
                props: {icon: "md-remove-circle",size:'small',cursor: 'pointer'},
                on: {
                  click(){
                    formData[key].splice(params.index, 1);
                    designSelectData.validators = formData[key];
                    refreshDesignSelectData(designFormOptions,designSelectData);
                  }
                }
              },"删除");
            }}],
          ...obj.props
      },
      on:{
        'on-drag-drop':function (index1, index2) {
          formData[key].splice(index2, 1, ...formData[key].splice(index1, 1, formData[key][index2]));
          extendShowOrHideEvent(formData[key],designSelectData);
          refreshDesignSelectData(designFormOptions,designSelectData);
        }
      },
      data: obj.data
    })
  ])
}
function generateApiTableComponent(h, formData = [], obj, vm,designSelectData={},designFormOptions=[],options) {
  const key = obj.key? obj.key : '';
  let fields=[];
  designFormOptions.map(item=>{
    /*不控制隐藏或显示自己*/
    if(item.key!==designSelectData.key){
      fields.push({label:item.label+'（'+item.key+'）',key:item.key});
    }
  });
  return h('div',{},[
    h('Row',{},[
      h('Col',{props:{span:7}},[h("Select",{
        style:{width:"100%"},
        props: {value:formData[key]["api"], transfer:true,placeholder:"选择API"},
        on: {
          input: function (val) {
            formData[key]["api"]=val;
            let value = JSON.parse(val);
            formData[key]["params"]=value.params;
            formData[key]["response"]=value.response;
            apiResource(formData[key],designSelectData,designFormOptions);
          }
        }
      },function () {
        if(!obj.refresh){
          ReqUtil.doApiPost("/apiOpenService/getAllApi")
            .setSuccessFunction(function (result) {
              obj.children = result.data.map(item => {
                return {label:item.name,value:JSON.stringify(item)}
              });
              obj.refresh = true;
            }).isRawBody().execute();
        }else{
          obj.children = obj.children||[];
          return obj.children.map(item=>{
            return h('Option',{
              props:item
            });
          })
        }
      }())]),
      h('Col',{props:{span:2,title:"根据api返回数据结构设置label"},style:{textAlign:'center'}},"label:"),
      h('Col',{props:{span:6}},[
        h('Select',{
          props:{
            value:formData[key]["label"]
          },
          on: {
            input: function (value) {
              formData[key]["label"]=value;
              apiResource(formData[key],designSelectData,designFormOptions);
            }
          }
        },function () {
          let options=[];
          for(let k in formData[key].response){
            options.push(h('Option',{
              props:{value:k}
            },formData[key].response[k]))
          }
          return options;
        }())
      ]),
      h('Col',{props:{span:2,title:"根据api返回数据结构设置value"},style:{textAlign:'center'}},"value:"),
      h('Col',{props:{span:6}},[
        h('Select',{
          props:{
            value:formData[key]["value"]
          },
          on: {
            input: function (value) {
              formData[key]["value"]=value;
              apiResource(formData[key],designSelectData,designFormOptions);
            }
          }
        },function () {
          let options=[];
          for(let k in formData[key].response){
            options.push(h('Option',{
              props:{value:k}
            },formData[key].response[k]))
          }
          return options;
        }())
      ]),
    ]),
    h('Table', {
      style:{
        width:"100%"
      },
      props: {
        'no-data-text':"选择上方的api！",
        draggable:true,
        border:false,
        data:formData[key]["params"],
        columns:[
          {key:"desc",width:150},
          {key:"name",width:150,render:(h, params) => {
            return h('span',"参数："+params.row.name)
          }},
          {key:"type",width:140,render:(h, params) => {
              return h('span',{},"类型："+params.row.type)
          }},
          {key:"value",render: (h, params) => {
           return  h('Row',{},[
             h('Col',{props:{span:2}}, [h('span',{},"值：")]),
             h('Col',{props:{span:22}}, [
               h('Select',{
                 props:{value:formData[key]['paramsVal'][params.row.name],placeholder:"请选择参数字段",transfer:true},
                 on:{
                   input: function (value) {
                     formData[key]['paramsVal'][params.row.name]=value;
                     apiResource(formData[key],designSelectData,designFormOptions);
                   }
                 }
               },
               fields.map(item=>{
                 return h('Option',{
                   props:{value:item.key}
                 },item.label)
               }))]),
            ])
          }}
        ],
        ...obj.props
      },
      data: obj.data
    })
  ])
}
function generateColComponent(h, obj, component) {
    return h('Col', {
        props: {
            span: obj.span||24,
            push: obj.push,
            pull: obj.pull,
            offset: obj.offset,
            order: obj.order,
            'class-name': obj['class-name'] || obj['className'],
            xs: obj.xs,
            sm: obj.sm,
            md: obj.md,
            lg: obj.lg,
        },
    }, [component])
}

function generateRowComponent(h,obj,component) {
  return h('Row', {
    props: {
      gutter: obj.gutter,
      type: obj.type,
      align: obj.align,
      justify: obj.justify,
      'class-name': obj['class-name'] || obj['className']
    },
  },[generateColComponent(h, obj, component)])
}

function generateFormItemComponent(h, obj, component) {
    let validators = null;
    try{
      if(obj.validators){
        validators ={
          required: false,
          trigger: 'blur',
          validator(a, v, c) {
            obj.validators.map(item=>{
              try{
                if(!eval("(rules."+item.validator+"(v))")){
                  c(new Error(item.msg));
                }
              }catch (e) {}
            });
            c();
          }
        };
        obj.validators.map(item=>{
          if(item.validator==="isNotNull"){
            validators.required=true;
            return;
          }
        });
      }
    }catch (e) { }
    return h('FormItem', {
        class: obj.className,
        props: {
            label: obj.label+" : ",
            rules: obj.rules||validators,
            prop: obj.key? obj.key : '',
            'label-width':obj['label-width'] || obj['labelWidth'],
            'label-for': obj['label-for'] || obj['labelFor'],
            error: obj.error,
            'show-message': obj['show-message'] || obj['showMessage'],
        }
    }, [component])
}
function refreshDesignSelectData(designFormOptions,designSelectData) {
  let label = designFormOptions[designSelectData.index].label+"";
  designFormOptions[designSelectData.index].label="通过label改变来刷新";//TODO 临时解决
  designFormOptions[designSelectData.index].label=label;
}
function apiResource(apiData,designSelectData,designFormOptions) {
  let api = JSON.parse(apiData.api);
  let path = api.path;
  designSelectData.refresh=false;
  designSelectData.dataModal.label = apiData.label;
  designSelectData.dataModal.value = apiData.value;
  designSelectData.url = path;
  designSelectData.urlParam = apiData.paramsVal;
  let apiParamsMap={};
  for(let i=0,len=designFormOptions.length;i<len;i++){
    let item = designFormOptions[i];
    if(item.url&&item.urlParam){
      for(let key in item.urlParam){
        apiParamsMap[item.urlParam[key]]=apiParamsMap[item.urlParam[key]]?apiParamsMap[item.urlParam[key]]:[];
        if(apiParamsMap[item.urlParam[key]].indexOf(item.key)==-1){
          apiParamsMap[item.urlParam[key]].push(item.key);
        }
      }
    }
  }
  //当api参数变化时触发 api请求
  for(let i=0,len=designFormOptions.length;i<len;i++){
    let item = designFormOptions[i];
    if(apiParamsMap[item.key]){
      designFormOptions[i].events['on-change']=designFormOptions[i].events['on-change']?designFormOptions[i].events['on-change']:{}
      designFormOptions[i].events['on-change']["cascade"]=apiParamsMap[item.key]
    }
  }
}
function extendShowOrHideEvent(events,designSelectData) {
  let eventsOpt =[];
  events.map(item=>{
    eventsOpt.push({
      formType:item.formType,
      withVal: item.withVal,
      showField: item.showField,
      hideField: item.hideField
    })
  });
  designSelectData.events['on-change']=designSelectData.events['on-change']?designSelectData.events['on-change']:{};
  designSelectData.events['on-change']["showOrHide"]=eventsOpt;
};

function translateEvents(events = {}, vm,options) {
    const result = {};
    for (let event in events) {
        if(typeof(events[event])==='function'){
          result[event] = events[event].bind(vm)
        }else{
          let changFunStr="";
          if(events[event].showOrHide){
            events[event].showOrHide.map(item=> {
              switch (item.formType) {
                case "checkboxGroup":
                  changFunStr+="if(val.indexOf('"+item.withVal+"')>-1){";
                  item.showField.map(field=>{
                    changFunStr+="options."+field+".show=true;";
                  });
                  item.hideField.map(field=>{
                    changFunStr+="options."+field+".show=false;";
                  });
                  changFunStr+="}";
                  changFunStr+="if(val.indexOf('"+item.withVal+"')==-1){";
                  item.showField.map(field=>{
                    changFunStr+="options."+field+".show=false;";
                  });
                  item.hideField.map(field=>{
                    changFunStr+="options."+field+".show=true;";
                  });
                  changFunStr+="}";
                  break;
                default:
                  changFunStr+="if(val=='"+item.withVal+"'){";
                  item.showField.map(field=>{
                    changFunStr+="options."+field+".show=true;";
                  });
                  item.hideField.map(field=>{
                    changFunStr+="options."+field+".show=false;";
                  });
                  changFunStr+="}";
                  break;
              }
            });
            changFunStr = "function showOrHide(val){"+changFunStr+"}(v)";
          }
          let cascadeFunStr ="";
          //级联操作
          if(events[event].cascade){
            events[event].cascade.map(item=>{
              cascadeFunStr+="options."+item+".refresh=false;"
            });
            cascadeFunStr = "function cascade(val){"+cascadeFunStr+"}(v)";
          }
          result[event] = (function(v){
            changFunStr=(changFunStr===""?"function(){}":changFunStr);
            eval("("+changFunStr+")")
            cascadeFunStr=(cascadeFunStr===""?"function(){}":cascadeFunStr);
            eval("("+cascadeFunStr+")")
          }).bind(vm)
        }
    }
    return result
}

export default componentObj
