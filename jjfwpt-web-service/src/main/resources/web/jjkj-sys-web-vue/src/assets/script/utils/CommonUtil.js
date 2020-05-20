import Vue from "vue";

const ClientHeight = function (unnecessary) {
  return document.documentElement.clientHeight - (unnecessary||0);
};
const GetColor = function (i) {
  let colorList=[
    "#0000CC",
    "#3366FF",
    "#333300",
    "#336666"

  ];
  return colorList[(i||0)];
};
const ToPage = function (viewPath) {
  sessionStorage.setItem("history",viewPath);
  this.$router.push({path: viewPath||'/404'})
};
let randommCodes =
  ['0','1','2','3','4','5','6','7','8','9',
   'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
   'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
let getRandom= function (start, end, fixed=0) {
  let differ = end - start;
  let random = Math.random();
  return (start + differ * random).toFixed(fixed)
};
/*随机码*/
const RandomCode = function (length) {
  let code ="";
  code+=randommCodes[getRandom(10,61)]
  for(let i=0;i<length-1;i++){
      code+=randommCodes[getRandom(0,61)];
  }
  return code.substring(0,length);
};
/*注册窗体高度改变而需要处理的组件*/
const RegHeightChangeFun = function (key,fun) {
  Vue.prototype.heightChangeFun[key]={
      enabled:true,
      fun:fun
    };
};
/*注销窗体高度改变而需要处理的组件*/
const UnRegHeightChangeFun = function (key) {
  delete  Vue.prototype.heightChangeFun[key];
};
/*启用窗体高度改变而需要处理的组件*/
const EnabeHeightChangeFun = function (key) {
  Vue.prototype.heightChangeFun[key].enabled=true;
};
/*禁用窗体高度改变而需要处理的组件*/
const UnEnabeHeightChangeFun = function (key) {
  Vue.prototype.heightChangeFun[key].enabled=false;
};
const GetFilePath = function(path){
  return "/file/"+path;
};

let isType = (obj, type) => {
  if (typeof obj !== 'object') return false;
  // 判断数据类型的经典方法：
  const typeString = Object.prototype.toString.call(obj);
  let flag;
  switch (type) {
    case 'Array':
      flag = typeString === '[object Array]';
      break;
    case 'Date':
      flag = typeString === '[object Date]';
      break;
    case 'RegExp':
      flag = typeString === '[object RegExp]';
      break;
    default:
      flag = false;
  }
  return flag;
};
let getRegExp = re => {
  let flags = '';
  if (re.global) flags += 'g';
  if (re.ignoreCase) flags += 'i';
  if (re.multiline) flags += 'm';
  return flags;
};
/**
 * deep clone
 * @param  {[type]} parent object 需要进行克隆的对象
 * @return {[type]}        深克隆后的对象
 */
const Clone = function(parent){
  // 维护两个储存循环引用的数组
  const parents = [];
  const children = [];

  const _clone = parent => {
    if (parent === null) return null;
    if (typeof parent !== 'object') return parent;

    let child, proto;

    if (isType(parent, 'Array')) {
      // 对数组做特殊处理
      child = [];
    } else if (isType(parent, 'RegExp')) {
      // 对正则对象做特殊处理
      child = new RegExp(parent.source, getRegExp(parent));
      if (parent.lastIndex) child.lastIndex = parent.lastIndex;
    } else if (isType(parent, 'Date')) {
      // 对Date对象做特殊处理
      child = new Date(parent.getTime());
    } else {
      // 处理对象原型
      proto = Object.getPrototypeOf(parent);
      // 利用Object.create切断原型链
      child = Object.create(proto);
    }

    // 处理循环引用
    const index = parents.indexOf(parent);

    if (index != -1) {
      // 如果父数组存在本对象,说明之前已经被引用过,直接返回此对象
      return children[index];
    }
    parents.push(parent);
    children.push(child);

    for (let i in parent) {
      // 递归
      child[i] = _clone(parent[i]);
    }

    return child;
  };
  return _clone(parent);
};

function DateFormat(thisDate, fmt) {
  let o = {
    "M+": thisDate.getMonth() + 1,
    "d+": thisDate.getDate(),
    "h+": thisDate.getHours(),
    "m+": thisDate.getMinutes(),
    "s+": thisDate.getSeconds(),
    "q+": Math.floor((thisDate.getMonth() + 3) / 3),
    "S": thisDate.getMilliseconds()
  };
  if (/(y+)/.test(fmt))
    fmt = fmt.replace(RegExp.$1, (thisDate.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (let k in o)
    if (new RegExp("(" + k + ")").test(fmt))
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}



export default {
  UnRegHeightChangeFun,
  RegHeightChangeFun,
  EnabeHeightChangeFun,
  UnEnabeHeightChangeFun,
  ClientHeight,
  GetColor,
  ToPage,
  GetFilePath,
  RandomCode,
  Clone,
  DateFormat
}
