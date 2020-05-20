import $ from 'jquery'

const httpReq = function (url, data, type, dataType, header, successfn, errorfn, completefn) {
  type = (type == null || type == "" || typeof (type) == "undefined") ? "post" : type;
  dataType = (dataType == null || dataType == "" || typeof (dataType) == "undefined") ? "json" : dataType;
  data = (data == null || data == "" || typeof (data) == "undefined") ? {"date": new Date().getTime()} : data;

  $.ajax({
    type: type,
    data: data,
    url: url,
    dataType: dataType,
    contentType: "application/json;charset=utf-8",
    beforeSend: function (request) {
      for (const key in header) {
        request.setRequestHeader(key, header[key]);
      }
    },
    success: function (d) {
      successfn(d)
    },
    error: function (e) {
      errorfn(e)
    },
    complete: function (e) {
      completefn(e)
    }
  });
}
const localDict = {
  common:{
    state: {
      "0": "启用",
      "1": "禁用"
    }
  },
  menuinfo: {
    state: {
      "0": "启用",
      "1": "禁用"
    },
    type: {
      "1": "功能模块",
      "2": "模块菜单",
      "3": "页面权限",
    }
  },
  userinfo: {

  }
};
const req = function (urlV) {
  const ajaxObj = new Object();
  ajaxObj.url = urlV;
  ajaxObj.setData = function (value) {
    ajaxObj.dataV = value;
    return ajaxObj;
  }
  ajaxObj.setJsonDataStr = function (value) {
    ajaxObj.dataV = JSON.stringify(value);
    return ajaxObj;
  }
  ajaxObj.setType = function (value) {
    ajaxObj.typeV = value;
    return ajaxObj;
  }
  ajaxObj.setHeader = function (value) {
    ajaxObj.headerV = $.extend(ajaxObj.headerV || {}, value);
    return ajaxObj;
  }
  ajaxObj.setDataType = function (value) {
    ajaxObj.dataTypeV = value;
    return ajaxObj;
  }
  ajaxObj.setSuccessfn = function (value) {
    ajaxObj.successfnV = value;
    return ajaxObj;
  }
  ajaxObj.setComplete = function (value) {
    ajaxObj.completeV = value;
    return ajaxObj;
  }
  ajaxObj.setErrorfn = function (value) {
    ajaxObj.errorfnV = value;
    return ajaxObj;
  }
  ajaxObj.go = function (that) {
    const access_token = sessionStorage.getItem("access_token");
    if(access_token){
      ajaxObj.setHeader({Authorization: ("Bearer "+access_token)})
    }
    httpReq(
      ajaxObj.url,
      ajaxObj.dataV,
      ajaxObj.typeV,
      ajaxObj.dataTypeV,
      ajaxObj.headerV,
      ajaxObj.successfnV || function () {
      },
      function (d) {
        try{
          if(d.responseJSON.error){
            errorMsg(that, JSON.stringify(d.responseJSON))
          }
          if (d.responseJSON.status === 401) {
            that.$Modal.warning({
              title: "登录已过期",
              content: "请重新登录",
              width: 400,
              onOk: function () {
                sessionStorage.setItem("history", that.$route.path);
                that.$router.push({path: "/login"})
              }
            })
          }else {
            errorMsg(that, d.responseJSON)
          }
          (ajaxObj.errorfnV || function () {})(d)
        }catch (e) {}
      },
      function (d) {
        try{
          // if (d.responseJSON.statusCode === "201") {
          //   that.$Modal.warning({
          //     title: "登录已过期",
          //     content: "请重新登录",
          //     width: 300,
          //     onOk: function () {
          //       sessionStorage.setItem("history", that.$route.path);
          //       that.$router.push({path: "/login"})
          //     }
          //   })
          // }
          (ajaxObj.completeV || function () {})(d)
        }catch (e) {}
      }
    )
  }
  return ajaxObj;
}

const errorMsg = function (that, a) {
  that.$Modal.error({
    title: "异常",
    width: 400,
    render: (h) => {
      return h({
        template: '<json-viewer ' +
          '        :value="message" ' +
          '        :expand-depth=5 ' +
          '        copyable ></json-viewer>',
        data() {
          return {
            message: a
          }
        }
      })
    }
  })
}
const getUrlParam = function () {
  let url=window.location.href
  let param={};
  let paramStr=url.substring(url.indexOf("#")+1,url.length).split("&");
  for(let key in paramStr){
    let vkstr=paramStr[key]
    let vk=vkstr.split("=")
    param[vk[0]]=vk[1]
  }
  return param;
}
const tlogin = function(e) {
      if ("google" == e) {
        window.location.href = "https://accounts.google.com/o/oauth2/auth?redirect_uri=http://www.processon.com/google.jsp&response_type=token&client_id=524693152001.apps.googleusercontent.com&scope=https://www.googleapis.com/auth/userinfo.profile+https://www.googleapis.com/auth/userinfo.email";
      } else if ("qq" == e) {
        window.location.href = "https://graph.qq.com/oauth2.0/authorize?client_id=101580723&response_type=token&scope=all&redirect_uri=http://iwant.natapp1.cc/api/security/user/tlogin";
      } else if ("sina" == e) {
        window.location.href = "https://api.weibo.com/oauth2/authorize?client_id=355778041&response_type=code&redirect_uri=http://www.processon.com/login/sina";
      } else if ("mingdao" == e) {
        window.location.href = "https://api.mingdao.com/oauth2/authorize?app_key=5967E9E0B4ADA1B9C23B1893ABAED0F&response_type=code&redirect_uri=http://www.processon.com/login/mingdao";
      }
};

export default {
  req: req,
  errorMsg: errorMsg,
  getUrlParam:getUrlParam,
  extend: $.extend,
  tlogin: tlogin,
  localDict: localDict
}

