import Config from "../config/Config";
import CommonUtil from "./CommonUtil";

export default {
  INFO:function (msg) {
    if(!Config.logType.info){
      return;
    }
    console.log("[ INFO]["+CommonUtil.DateFormat(new Date(),"yyyy-MM-dd hh:mm:ss")+"]","::::",msg);
  },
  ERROR:function (msg) {
    if(!Config.logType.error){
      return;
    }
    console.error("[ERROR]["+CommonUtil.DateFormat(new Date(),"yyyy-MM-dd hh:mm:ss")+"]","::::",msg);
  },
  DEBUG:function (msg) {
    if(!Config.logType.debug){
      return;
    }
    console.info("[DEBUG]["+CommonUtil.DateFormat(new Date(),"yyyy-MM-dd hh:mm:ss")+"]","::::",msg);
  },
  WARN:function (msg) {
    if(!Config.logType.warn){
      return;
    }
    console.warn("[ WARN]["+CommonUtil.DateFormat(new Date(),"yyyy-MM-dd hh:mm:ss")+"]","::::",msg);
  }
}
