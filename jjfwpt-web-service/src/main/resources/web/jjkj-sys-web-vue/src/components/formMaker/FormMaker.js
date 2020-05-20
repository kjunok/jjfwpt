import componentObj from './formMakerUtils'
import FormUtil from "../../assets/script/utils/FormUtil";

export default {
    props: {
        options:null,
        formData: {
          type: Object,
          required: true
        },
        designFormOptions:{
          type: Array,
          required: false
        },
        designSelectData:{
          type: Object,
          required: false
        },
        //是否为单个数据
        singleData:{
          type: Boolean,
          default:false,
          required: true
        }
    },
    render(h) {
        const options = this.options;
        const designFormData = this.designFormOptions;
        const designSelectData = this.designSelectData;
        const singleData = this.singleData===undefined?false:this.singleData;
        const components =[];
        let formData = this.formData;
        if(singleData){
          formData = FormUtil.EmptyFormData([options])
        }
        const createComponent = function (item) {
          if(item.show!==undefined&&!item.show&&!singleData){
            return null;
          }
          let func = componentObj[item.type];
          let subComponent = func? func.call(this, h, formData, item, this,designSelectData,designFormData,options) : null;
          let component = componentObj.formItem(h, item, subComponent);
          return componentObj.col(h, item, component);
        };
        if(singleData){
          components.push(createComponent(options))
        }else{
          for(let i in options){
            let item =  options[i];
            components.push(createComponent(item))
          }
        }
        if (options.submit) {
            processSubmitOrReset(components, h, formData, options.submit, this, 'submit')
        }

        if (options.reset) {
            processSubmitOrReset(components, h, formData, options.reset, this, 'reset')
        }

      if (!options.formProps) {
        return h('div',{},components)
      }else{
        return h('Form', {
            ref: 'form',
            props: {
                model: formData,
                ...options.formProps
            },
            class: 'vue-generate-form'
        }, [
            h('Row', {
                props: options.rowProps
            }, components)
        ])
      }
    }
}

function processSubmitOrReset(components, h, formData, obj, vm, type) {
    let subComponent = componentObj[type](h, formData, obj, vm);
    let component = componentObj.formItem(h, obj, subComponent);
    components.push(componentObj.col(h, obj, component))
}
