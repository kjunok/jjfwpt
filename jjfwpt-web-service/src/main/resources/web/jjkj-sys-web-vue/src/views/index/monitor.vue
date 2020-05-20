<template>
  <div style="background: rgba(0, 0, 0, 0.36)">
    <Card style="width:100%;background: rgba(0, 0, 0, 0.36);border: none;border-bottom: white 1px solid">
      <Icon type="ios-bulb"/>
      说明
      <span style="color: #ffabfa">监控</span>
    </Card>
    <div  id="allmap" ref="allmap" style="width: 100%;" :style="{height:clientHeight+150+'px'}"></div>
  </div>
</template>

<script>
  export default {
    name: "monitor",
    props:['clientHeight'],
    data() {
      return {
      }
    },
    methods:{
      map(){
        let map =new BMap.Map(this.$refs.allmap); // 创建Map实例
        map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);// 初始化地图,设置中心点坐标和地图级别
        map.addControl(new BMap.MapTypeControl({//添加地图类型控件
          mapTypes:[
            BMAP_NORMAL_MAP,
            BMAP_HYBRID_MAP
          ]}));
        map.setMapStyle({style:'dark'});
        map.setCurrentCity("北京");// 设置地图显示的城市 此项是必须设置的
        map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
      },
      getLocation(){
        let geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function(r){
          if(this.getStatus() == BMAP_STATUS_SUCCESS){
            // var mk = new BMap.Marker(r.point);
            // map.addOverlay(mk);
            // map.panTo(r.point);
           console.log(r.address.city)
          }
          else {
            alert('failed'+this.getStatus());
          }
        },{enableHighAccuracy: true})
      }
    },
    mounted() {
      this.map()
      this.getLocation()
    }
  }
</script>

<style scoped>

</style>
