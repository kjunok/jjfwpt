import Config from "./config/Config";
const path = function(path){
  return Config.prefix+path;
};
export default {
  auth:{
    loginByPwd : {
      path:"/auth/oauth/loginByPwd",
      des:"登录"
    },
    logOut:{
      path: "/auth/oauth/logOut",
      des:"注销"
    }
  },
  userService:{
    searchUserList : {
      path:path("/userService/searchUserList"),
      des:"查询用户列表"
    },
    saveOrUpdateUser:{
      path:path("/userService/saveOrUpdateUser"),
      des:"添加或修改用户"
    },
    searchSingleUserByParam:{
      path:path("/userService/searchSingleUserByParam"),
      des:"查询单个用户"
    },
    searchRoleUserPageByParam:{
      path:path("/userService/searchRoleUserPageByParam"),
      des:"查询角色用户"
    },
    searchNotRoleUserPageByParam:{
      path:path("/userService/searchNotRoleUserPageByParam"),
      des:"查询非角色用户"
    }
  },
  menuService:{
    routes : {
      path:path("/menuService/routes"),
      des:"路由"
    },
    searchMenuTreeSortList : {
      path:path("/menuService/searchMenuTreeSortList"),
      des:"树形菜单列表"
    },
    searchMenuTreeNavList : {
      path:path("/menuService/searchMenuTreeNavList"),
      des:"左侧导航菜单"
    }
  },
  roleService:{
    searchRolePagesByParam:{
      path:path("/roleService/searchRolePagesByParam"),
      des:"分页查询角色列表"
    },
    searchRoleById:{
      path:path("/roleService/searchRoleById"),
      des:"根据角色id查询角色基本信息"
    },
    getRoleMenusByRoleId:{
      path:path("/roleService/getRoleMenusByRoleId"),
      des:"根据角色id查询角色菜单"
    },
    saveOrUpdateRoleMenu:{
      path:path("/roleService/saveOrUpdateRoleMenu"),
      des:"添加或修改角色菜单"
    },
    saveOrUpdateRoleInfo:{
      path:path("/roleService/saveOrUpdateRoleInfo"),
      des:"添加或修改角色基本信息"
    },
    delRoleUserByRoleIdAndUserIds:{
      path:path("/roleService/delRoleUserByRoleIdAndUserIds"),
      des:"删除角色用户"
    },
    addRoleUser:{
      path:path("/roleService/addRoleUser"),
      des:"添加角色用户"
    }
  },
  bserviceService:{
    searchBserviceInfoPagesByParam:{
      path:path("/bserviceService/searchBserviceInfoPagesByParam"),
      des:"分页服务列表"
    }
  },
  formService:{
    searchFormPagesByParam:{
      path:path("/formService/searchFormPagesByParam"),
      des:"分页表单列表"
    },
    searchFormById:{
      path:path("/formService/searchFormById"),
      des:"根据id查询表单"
    },
    saveOrUpdateForm:{
      path:path("/formService/saveOrUpdateForm"),
      des:"添加或修改表单"
    },
    deleteFormById:{
      path:path("/formService/deleteFormById"),
      des:"根据id删除表单"
    }
  }
}
