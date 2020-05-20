import $ from "jquery"
import {Modal, Notice} from "view-design";
const MODAL_ERROR = function (msg) {
  Modal.error({
    title: "异常",
    width: 600,
    render: (h) => {
      return h({
        template: '<json-viewer ' +
          '        :value="message" ' +
          '        :expand-depth=5 ' +
          '        copyable ></json-viewer>',
        data() {
          return {
            message:msg
          }
        }
      })
    }
  })
};
const MODAL_INFO = function (msg) {
  Modal.info({
    title: "信息",
    width: 1200,
    render: (h) => {
      return h({
        template: '<json-viewer ' +
          '        :value="message" ' +
          '        :expand-depth=5 ' +
          '        copyable ></json-viewer>',
        data() {
          return {
            message:msg
          }
        }
      })
    }
  })
};
const MODAL_CONFIRM = function (title,msg,otherObj) {
  Modal.confirm($.extend({
    name:"MODAL_CONFIRM",
    title: title,
    content: msg
  },otherObj))
};
const MODAL_WARNING = function (title,msg,otherObj) {
  Modal.warning($.extend({
    name:"MODAL_WARNING",
    title: title,
    content: msg
  },otherObj))
};
const NOTICE_INFO = function (title,msg,otherObj) {
  Notice.info($.extend({
    name:"NOTICE_INFO",
    title:title,
    desc:msg
  },otherObj));
};
export default {
  MODAL_ERROR,
  MODAL_INFO,
  MODAL_CONFIRM,
  MODAL_WARNING,
  NOTICE_INFO
}
