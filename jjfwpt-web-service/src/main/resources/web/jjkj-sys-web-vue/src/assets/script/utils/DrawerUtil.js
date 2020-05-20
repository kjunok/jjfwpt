import CommonUtil from "./CommonUtil";

export default {
  util:function (name) {
    return {
       close() {
         this.$emit(name+"Listen", function (parent) {
           parent[name]=false;
         })
       },
       destroyed(){
        CommonUtil.UnRegHeightChangeFun(name);
      }
    }
  }
}
