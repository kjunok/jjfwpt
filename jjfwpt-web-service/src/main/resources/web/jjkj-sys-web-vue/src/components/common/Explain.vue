<template>
  <Card shadow style="border: none;border-bottom: white 1px solid;margin-left: 0px;">
    <Icon type="ios-bulb"/>
    说明
    <span style="color: #ffabfa">{{content}}</span>
  </Card>
</template>

<script>
    export default {
      props:['content'],
        name: "Explain"
    }
</script>

<style scoped>

</style>
