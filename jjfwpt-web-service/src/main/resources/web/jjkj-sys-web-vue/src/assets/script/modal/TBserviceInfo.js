import MsgUtil from "../utils/MsgUtil";
import ReqUtil from "../utils/ReqUtil";

export default {
  STANDARD:{

  },
  gridColumns:function (_this) {
    return [
      {
        title: '操作',
        width: 130,
        fixed: 'left',
        align: 'center',
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
                  _this.userForm = true;
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
                  _this.userForm = true;
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
            render.push(viewBtn,editBtn,delBtn);
            return render;
          }(h,data));
        }
      },
      {
        title: '服务名称',
        key: 'serviceName',
        sortable: true,
        //width: 200,
        //fixed: 'left'
      },
      {
        title: '所属区域',
        key: 'area',
        sortable:function () {
          return this.bserviceInfoGrid.sortAble
        },
        //width: 200,
        //fixed: 'left'
      },
      // {
      //   title: '表单字段',
      //   key: 'formItems',
      //   //width: 200,
      //   //fixed: 'left'
      // },
      {
        title: '状态',
        key: 'state',
        sortable: true,
        slot: 'state',
        //width: 200,
        //fixed: 'left'
      },
      {
        title: '服务描述',
        key: 'serviceDesc',
        //width: 200,
        //fixed: 'left'
      },
      {
        title: '创建人',
        sortable: true,
        key: 'createUserIdText',
        //width: 200,
        //fixed: 'left'
      },
      {
        title: '创建时间',
        key: 'createTime',
        sortable: true,
        //width: 200,
        //fixed: 'left'
      },
      {
        title: '修改人',
        key: 'updateUserIdText',
        sortable: true,
        //width: 200,
        //fixed: 'left'
      },
      {
        title: '修改时间',
        sortable: true,
        key: 'updateTime',
        //width: 200,
        //fixed: 'left'
      }
    ];
  },
  searchColumns:function(){
    return [
      {
        label: '服务名称',
        value: 'serviceName'
      },
      {
        label: '描述',
        value: 'serviceDesc'
      }
    ]
  },
  form:{
    saveOrUpdateUser:{
      columns:{
        id:'',
        serviceName:'',
        area:'',
        formItems:'',
        state:'',
        serviceDesc:'',
        createUserId:'',
        createTime:'',
        updateUserId:'',
        updateTime:''
      },
      validate: {
        serviceName: [
          {required: true, message: '服务名称不能为空', trigger: 'blur'},
        ],
        serviceDesc: [
          {required: true, message: '描述不能为空', trigger: 'blur'}
        ]
      }
    }
  }

}
