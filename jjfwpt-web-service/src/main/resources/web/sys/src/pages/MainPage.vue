<template>
  <body>
     <Header></Header>
     <LeftMenu></LeftMenu>
     <ThemeSettings></ThemeSettings>
  </body>
</template>
<style>

</style>

<script>
  import Header from "../components/Header"
  import LeftMenu from "../components/LeftMenu";
  import ThemeSettings from "../components/ThemeSettings";
  export default {
    components: {ThemeSettings, LeftMenu, Header},
    name: 'MainPage',
    data() {
      const item = {
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      };
      return {
        isCollapse: true,
        tableData: Array(20).fill(item)
      }
    },
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  };
</script>
