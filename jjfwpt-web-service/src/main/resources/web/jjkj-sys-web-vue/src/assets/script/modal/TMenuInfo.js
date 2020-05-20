import CommonUtil from "../utils/CommonUtil";
import MsgUtil from "../utils/MsgUtil";
import ReqUtil from "../utils/ReqUtil";

export default {
  standard:{},
  gridColumns:function (_this) {
    return [
      {
        title: '名称',
        key: 'name',
        fixed:'left',
        tree: true,
        width:300,
        render: (h, params) => {
          return h('span',{
            style:{
              marginLeft:"10px"
            }
          },[
            function (d) {
              if((d.row.icon+"").indexOf('/')>-1){
                return h('img', {
                  attrs: {
                    src:CommonUtil.GetFilePath(d.row.icon)
                  },
                  style: {
                    marginBottom:"-5px",
                    marginRight:"17px",
                    width: '20px',
                    height:'20px'
                  }
                })
              }else{
                return h('Icon', {
                  props: {
                    type:d.row.icon,
                    size:22
                  },
                  style: {
                    marginRight: '15px',
                    columns: CommonUtil.GetColor(d.row.type)
                  }
                })
              }
            }(params),
            h('span',params.row.name)
          ]);
        }
      },
      {
        title: '操作',
        slot: 'action',
        width: 170,
        fixed: 'left',
        align: 'left',
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
                marginRight: '8px',
                color:"#a790a6"
              },
              on: {
                click: () => {
                  _this.addOrUpdateShow = true;
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
                  _this.addOrUpdateShow = true;
                  _this.handle = {type:"update",data:data};
                }
              }
            });
            let addBtn = h('Icon', {
              props:{
                type: 'md-add-circle',
                size:22,
              },
              style: {
                color:"#436d86",
                marginRight: '8px'
              },
              on: {
                click: () => {
                  _this.addOrUpdateShow = true;
                  _this.handle = {type:"add",data:data};
                }
              }
            });
            let delBtn =  h('Icon', {
              props: {
                type: 'ios-trash',
                size:22
              },
              style: {
                color:"red",
                marginRight: '8px'
              },
              on: {
                click: () => {
                  MsgUtil.MODAL_CONFIRM("提醒","是否删除",
                    {onOk:function () {
                        ReqUtil.doPost("/del").setData({})
                          .setSuccessFunction(function (result) {
                            if(result.success){
                              MsgUtil.NOTICE_INFO("删除成功!","删除成功21121212121")
                            }
                          })
                          .setData({id:data.id})
                          .execute();
                      },
                      onCancel:function () {
                        MsgUtil.NOTICE_INFO("删除成功!","删除成功21121212121")
                      }
                    });
                }
              }
            });
            if(data.type==0){
              render.push(addBtn);
            }else {
              render.push(viewBtn,editBtn);
              if(data.type==1||data.type==2){
                render.push(addBtn);
              }
              if(data.children.length==0){
                render.push(delBtn);
              }
            }
            return render;
          }(h,data));
        }
      },
      {
        title: '描述',
        key: 'description'
      },
    ]
  },
  form:{
    saveOrUpdate:{
      columns:{
        id: '',
        name: '',
        pid: '',
        viewPath: '',
        type: '1',
        enabled: '0',
        icon: 'md-help',
        iconFile:null,
        orderNum: 1,
        code:"",
        groupName: '',
        description: '',
        desc: ''
      },
      validate: {
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'},
        ],
        pid: [
          {required: true, message: '请选择父菜单', trigger: 'change'}
        ],
        type:
          [
            {required: true, message: '请选择类型', trigger: 'change'}
          ]
      }
    }
  }
}
