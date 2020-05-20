import MsgUtil from "../utils/MsgUtil";
import ReqUtil from "../utils/ReqUtil";
import API from "../API";

export default {
  STANDARD:{

  },
  gridColumns:function (_this,type) {
    return [
      {
        type: 'index',
        width: 60,
        align: 'center',
        fixed: 'left'
      },
      {
        title: '名称',
        key: 'name',
        width: 200,
        fixed: 'left'
      },
      {
        title: '操作',
        slot: 'action',
        width: 200,
        fixed: 'left',
        render: (h, params) => {
          let data = params.row;
          return h('div', function (h,data) {
            let render= [];
            let viewBtn =h('Icon', {
              props: {
                type: 'md-search',
                size:22
              },
              style: {
                color:"#a790a6",
                marginRight: '8px'
              },
              on: {
                click: () => {
                  _this.formForm = true;
                  _this.handle = {type:"view",data:data};
                }
              }
            });
            let editBtn = h('Icon', {
              props: {
                type: 'ios-create',
                size:22
              },
              style: {
                color:"#135f12",
                marginRight: '8px'
              },
              on: {
                click: () => {
                  _this.formForm = true;
                  _this.handle = {type:"edit",data:data};
                }
              }
            });
            let formDesign = h('Icon', {
              props: {
                type: 'ios-list-box',
                size:22
              },
              style: {
                color:"#135f12",
                marginRight: '8px'
              },
              on: {
                click: () => {
                  _this.FormDesign = true;
                  _this.handle = {type:"edit",data:data};
                }
              }
            });
            let delBtn =  h('Icon', {
              props: {
                type: 'ios-trash',
                size:22,
              },
              style: {
                color:"red",
                marginRight: '8px'
              },
              on: {
                click: () => {
                  MsgUtil.MODAL_CONFIRM("提醒","是否删除",
                    {onOk:function () {
                          _this.api_deleteFormById(data)
                      },
                      onCancel:function () {}
                    });
                }
              }
            });
            render.push(viewBtn,editBtn,formDesign,delBtn);
            return render;
          }(h,data));
        }
      },
      {
        title: '标识',
        key: 'code',
        width: 200,
        //fixed: 'left'
      },
      {
        title: '描述',
        key: 'description',
        //width: 200,
        //fixed: 'left'
      }]
  },
  searchColumns:function(){
    return [
      {
        label: '账户',
        value: 'account'
      },
      {
        label: '昵称',
        value: 'avatar'
      }
    ]
  },
  form:{
    saveOrUpdate:{
      columns:{
        id:'',
        name:'',
        code:'',
        description:'',
        options:'',
        version:'',
        enable:''
      },
      validate: {

      }
    }
  }

}
