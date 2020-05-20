import MsgUtil from "../utils/MsgUtil";
import ReqUtil from "../utils/ReqUtil";

export default {
  STANDARD:{

  },
  gridColumns:function (_this,type) {
    let processOption ={
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
    };
    switch (type) {
      case "roleUser":
        processOption ={
          title: '操作',
          width: 100,
          fixed: 'left',
          align: 'center',
          render: (h, params) => {
            let data = params.row;
            return h('div', function (h,data) {
              let render= [];
              let editBtn = h('Icon', {
                props: {
                  type: 'md-remove-circle',
                  size:22
                },
                style: {
                  color:"red",
                  marginRight: '8px'
                },
                on: {
                  click: () => {
                    _this.api_delRoleUserByRoleIdAndUserIds(data.id);
                  }
                }
              });
              render.push(editBtn);
              return render;
            }(h,data));
          }
        };
        break;
      case "notRoleUser":
        processOption ={
          title: '操作',
          width: 100,
          fixed: 'left',
          align: 'center',
          render: (h, params) => {
            let data = params.row;
            return h('div', function (h,data) {
              let render= [];
              let editBtn = h('Icon', {
                props: {
                  type: 'md-add-circle',
                  size:22
                },
                style: {
                  color:"blue",
                  marginRight: '8px'
                },
                on: {
                  click: () => {
                    _this.api_addRoleUser(data.id);
                  }
                }
              });
              render.push(editBtn);
              return render;
            }(h,data));
          }
        };
        break;
    }
    return [
      processOption,
      {
        title: '账户',
        key: 'account',
        width: 150,
        //fixed: 'left'
      },
      {
        title: '昵称',
        key: 'avatar',
        width: 150,
        //fixed: 'left'
      },
      {
        title: '姓名',
        key: 'userName',
        width: 150,
        //fixed: 'left'
      },
      {
        title: '生日',
        key: 'birthDay',
        width: 150,
        //fixed: 'left'
      },
      {
        title: '性别',
        key: 'sex',
        width: 70,
        render: (h, params) => {
         return  h("span",params.row.sex==1?'男':'女')
        }
      },
      {
        title: '邮箱',
        key: 'email',
        width: 200,
        //fixed: 'left'
      },
      {
        title: '手机号',
        key: 'phone',
        width: 150,
        //fixed: 'left'
      },
      {
        title: '状态',
        key: 'enable',
        width: 100,
        render: (h, params) => {
          return  function () {
            let enabled = h("span",{
              style:{
                padding:"5px",
                background:'green',
                color:"white",
                borderRadius:'10px'
              }
            },'启用');
            let unEnabled = h("span",{
              style:{
                padding:"5px",
                background:'red',
                color:"white",
                borderRadius:'10px'
              }
            },'禁用');
            if(params.row.enable){
              return enabled;
            }else {
              return unEnabled;
            }
          }(h,params)
        }
      },
      {
        title: '创建时间',
        key: 'createTime',
        width: 200,
        //fixed: 'left'
      },
      {
        title: '修改时间',
        key: 'updateTime',
        width: 200,
        //fixed: 'left'
      }
    ];
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
      },
      {
        label: '姓名',
        value: 'userName'
      }
    ]
  },
  form:{
    saveOrUpdateUser:{
      columns:{
        id:'',
        account:'',
        password:'',
        userType:"1",
        userName:'',
        namePinyin:'',
        sex:1,
        avatar:'',
        phone:'',
        email:'',
        idCard:'',
        weiXin:'',
        weiBo:'',
        qq:'',
        birthDay:'',
        address:'',
        staffNo:'',
        enable:true,
        remark:''
      },
      validate: {
        account: [
          {required: true, message: '账户不能为空', trigger: 'blur'},
        ],
        avatar: [
          {required: true, message: '昵称不能为空', trigger: 'blur'}
        ],
        userName:
          [
            {required: true, message: '姓名不能为空', trigger: 'blur'}
          ]
      }
    }
  }

}
