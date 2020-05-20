<template>
  <Carousel autoplay v-model="value2" style="height: 500px;width: 90%;background: beige;margin:5% auto" loop>
    <CarouselItem>
      <div class="demo-carousel">
        <img src="../assets/img/skin-lights.jpg" style="width: 100%;height: 500px">
      </div>
    </CarouselItem>
    <CarouselItem >
      <div class="demo-carousel">
          <img src="../assets/img/skin-kiwi.jpg" style="width: 100%;height: 500px">
      </div>
    </CarouselItem>
    <CarouselItem>
      <div class="demo-carousel" >
        <img src="../assets/img/skin-sunny.jpg" style="width: 100%;height: 500px">
      </div>
    </CarouselItem>
  </Carousel>
</template>
<script>
  export default {
    data () {
      return {
        value2: 0
      }
    },
    mounted: function () {

    },
    methods:{
      test:function () {

      }
    }
  }
</script>
