import $ from "jquery";
import LogUtil from "./LogUtil";
import router from "../../../router";
import MsgUtil from "./MsgUtil";
import Config from "../config/Config";
  let ajaxReq = function (reqOpt) {
    let defReqOpt = {
      url:reqOpt.url,
      type: 'post',
      async:true,
      dataType: 'json',
      contentType:"application/json",
      data:"" ,
      success: reqOpt.success || function () {},
      error: reqOpt.error || defFunction.errorFunction,
      complete: reqOpt.complete || defFunction.completeFunction,
      beforeSend: reqOpt.beforeSend || defFunction.beforeSendFunction
    };

    if(!reqOpt.url){
      LogUtil.ERROR("请设置请求地址")
    }
    $.extend(defReqOpt,reqOpt)
    try{
      $.ajax(defReqOpt);
    }catch (e) {
      LogUtil.ERROR(e)
    }

  };
  let getErrorMsg = function(e){
    let errorMsg={};
    for(let key in e){
      if(!(e[key] instanceof  Function)){
        errorMsg[key] = e[key];
      }
    }
    delete errorMsg.responseText;
    return errorMsg;
  };
  let defFunction = {
    /*event - 包含 event 对象
    xhr - 包含 XMLHttpRequest 对象
    options - 包含 AJAX 请求中使用的选项
    exc - 包含 JavaScript exception*/
    errorFunction:function (event,xhr,options,exc) {
      LogUtil.ERROR({event,xhr,options,exc});
      MsgUtil.MODAL_ERROR(getErrorMsg(event))
    },
    beforeSendFunction:function () {

    },
    /*event - 包含 event 对象
    xhr - 包含 XMLHttpRequest 对象
    options - 包含 AJAX 请求中使用的选项*/
    completeFunction:function (event,xhr,options) {
      switch (xhr) {
        case "error":
          switch (event.status) {
            case 404:
              MsgUtil.MODAL_ERROR(getErrorMsg(event));
              //router.push('/404');
              break;
            case 401:
              LogUtil.ERROR("登录过期请登录！");
              sessionStorage.clear();
              router.push('/login');
              break;
            case 504:
              LogUtil.ERROR("服务器连接超时！");
              MsgUtil.MODAL_ERROR(getErrorMsg(event));
              break;
          }
          break;
      }
      LogUtil.INFO({event,xhr,options});
    }
  };
  const Req = function () {
  let obj = new Object();
    obj.reqOpt={};
    obj.setUrl = function (url) {
      obj.reqOpt.url = url;
    };
    obj.setData = function (data) {
      obj.reqOpt.data = data;
      return obj;
    };
    obj.setType = function (type) {
      obj.reqOpt.type = type;
      return obj;
    };
    obj.setAsync = function (async) {
      obj.reqOpt.async = async;
      return obj;
    };
    obj.setDataType = function (dataType) {
      obj.reqOpt.dataType = dataType;
      return obj;
    };
    obj.setJsonp = function (jsonp) {
      obj.reqOpt.jsonp = jsonp;
      return obj;
    };
    obj.setScriptCharset = function (scriptCharset) {
      obj.reqOpt.scriptCharset = scriptCharset;
      return obj;
    };
    obj.setContentType = function (contentType) {
      obj.reqOpt.contentType = contentType;
      return obj;
    };
    obj.setSuccessFunction = function (success) {
      obj.reqOpt.success = success;
      return obj;
    };
    obj.dataProcess = function(data){return data};
    obj.setErrorFunction = function (error) {
      obj.reqOpt.error = error;
      return obj;
    };
    obj.setCompleteFunction = function (complete) {
      obj.reqOpt.complete = complete;
      return obj;
    };
    obj.setBeforeSendFunction = function (beforeSend) {
      obj.reqOpt.beforeSend = beforeSend;
      return obj;
    };
    obj.go = function () {
      if(!obj.reqOpt.type){
        throw new Error("使用go方法需要设置请求类型，请使用setType([get|post|del....]) 方法设置")
      }
      ajaxReq(obj.reqOpt)
    };
    obj.isRawBody = function(){
        obj.dataProcess=function (data) {
            return JSON.stringify(data)
        };
        return this;
    };
    obj.isFormBody = function(){
      obj.dataProcess=function (data) {
        let formData = new FormData();
        obj.reqOpt.processData=false;
        obj.reqOpt.contentType=false;
        for(let key in data){
          formData.append(key,data[key]);
        }
        return formData;
      };
      return this;
    };
    obj.isFileBody = function(){
      obj.reqOpt.processData=false;// 使数据不做处理
      obj.reqOpt.contentType=false;
      return this;
    };
    obj.removeEmptyField = function(){
      let newData ={};
      for(let key in obj.reqOpt.data){
        if(obj.reqOpt.data[key]){
          newData[key]=obj.reqOpt.data[key]
        }
      }
      obj.reqOpt.data=newData;
      return this;
    };
    obj.POST = function () {
      obj.setType("POST")
    };
    obj.GET = function () {
      obj.setType("GET")
    };
    //执行请求
    obj.execute = function (callback) {
      try{
        obj.setBeforeSendFunction(function (request) {
          let access_token = sessionStorage.getItem("access_token");
          if(access_token){
            request.setRequestHeader("Authorization", ("Bearer "+access_token));
          }
        });
        //处理请求参数类型
        obj.setData(obj.dataProcess(obj.reqOpt.data));
        ajaxReq(obj.reqOpt);
        if(callback){
          callback();
        }
        LogUtil.DEBUG("["+obj.reqOpt.type+"]::"+""+obj.reqOpt.url)
      }catch (e) {
        LogUtil.ERROR(e)
      }
    };
    return obj;
  };
  const doApiPost = function (url) {
    url = Config.prefix+url;
    return doPost(url);
  };
  const doPost = function (url) {
    let req = Req();
    req.setUrl(url);
    req.POST();
    return req;
  };
  const doGet = function (url) {
    let reqObj = Req();
    reqObj.setUrl(url);
    reqObj.GET();
    return reqObj;
  };
export default {doGet,doPost,doApiPost}
