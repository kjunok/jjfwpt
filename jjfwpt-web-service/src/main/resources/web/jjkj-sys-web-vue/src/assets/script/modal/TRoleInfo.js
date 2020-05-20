import MsgUtil from "../utils/MsgUtil";
import ReqUtil from "../utils/ReqUtil";

export default {
  STANDARD:{

  },
  gridColumns:function (_this) {
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
        width: 160,
        fixed: 'left'
      },
      {
        title: '操作',
        width: 210,
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
                color:"#a790a6",
                marginRight: '8px'
              },
              on: {
                click: () => {
                  _this.roleForm = true;
                  _this.handle = {type:"view",data:data};
                }
              }
            });
            let addRoleUser = h('Icon', {
              props: {
                type: 'ios-people',
                size:22
              },
              style: {
                color:"#135f12",
                marginRight: '8px'
              },
              on: {
                click: () => {
                  _this.roleUser = true;
                  _this.handle = {data:data};
                }
              }
            });
            let addRoleMenu = h('Icon', {
              props: {
                type: 'md-reorder',
                size:22
              },
              style: {
                color:"#135f12",
                marginRight: '8px'
              },
              on: {
                click: () => {
                  _this.roleMenu = true;
                  _this.handle = {data:data};
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
                  _this.roleForm = true;
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
            render.push(viewBtn,editBtn,addRoleUser,addRoleMenu,delBtn);
            return render;
          }(h,data));
        }
      },
      {
        title: '代码',
        key: 'code',
        //width: 200,
        //fixed: 'left'
      },
      {
        title: '图标',
        key: 'icon',
        //width: 200,
        //fixed: 'left'
      },
      {
        title: '创建人',
        key: 'createUserIdText',
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
        key: 'updateUserIdText',
        //width: 200,
        //fixed: 'left'
      },
      {
        title: '修改时间',
        key: 'updateTime',
        //width: 200,
        //fixed: 'left'
      }
    ];
  },
  searchColumns:function(){
    return [
      {
        label: '名称',
        value: 'name'
      },
      {
        label: '代码',
        value: 'code'
      }
    ]
  },
  form:{
    saveOrUpdateUser:{
      columns:{
        id: '',
        name: '',
        code: '',
        createUserId: '',
        createTime: '',
        updateUserId: '',
        updateTime: '',
        icon: ''
      },
      validate: {
        name: [
          {required: true, message: '角色名称不能为空', trigger: 'blur'},
        ],
        code: [
          {required: true, message: '角色代码不能为空', trigger: 'blur'}
        ]
      }
    }
  }

}
