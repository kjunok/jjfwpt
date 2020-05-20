<template>
  <Drawer
    title="增加字典"
    v-model="show"
    width="550"
    @on-close="close">
    <div >
      <Table :columns="columns6" :data="data5"></Table>
      <div style="margin: 10px;">
        <div style="float: right;">
          <Page style="height: 40px" size="small" show-total show-elevator
                :total="dictItemGrid.pageData.total"
                :current="dictItemGrid.pageData.start"
                :page-size="dictItemGrid.pageData.size"
                @on-change="changePageNum"
                @on-page-size-change="changePageSize"/>
        </div>
      </div>
    </div>
    <Card style="margin-top: 50px;border: 1px solid rgba(0,0,255,0.17)">
      <p slot="title" style="color:white">
        <Icon type="ios-film-outline"></Icon>
        Classic film
      </p>
      <Form :model="formItem" :label-width="80" >
        <FormItem label="Input">
          <Input v-model="formItem.input" placeholder="Enter something..."></Input>
        </FormItem>
        <FormItem label="Switch">
          <i-switch v-model="formItem.switch" size="large">
            <span slot="open">On</span>
            <span slot="close">Off</span>
          </i-switch>
        </FormItem>
        <FormItem label="Slider">
          <Slider v-model="formItem.slider" range></Slider>
        </FormItem>
        <FormItem label="Text">
          <Input v-model="formItem.textarea" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="Enter something..."></Input>
        </FormItem>
        <FormItem>
          <Button type="primary">Submit</Button>
          <Button style="margin-left: 8px">Cancel</Button>
        </FormItem>
      </Form>
    </Card>
  </Drawer>
</template>

<script>
    export default {
      name: "childTable",
      props:['clientHeight',"show"],
      data(){
        return{
          dictItemGrid:{
            pageData:{
              total:0,
              start:1,
              size:10
            }
          },
          formItem: {
            input: '',
            select: '',
            radio: 'male',
            checkbox: [],
            switch: true,
            date: '',
            time: '',
            slider: [20, 50],
            textarea: ''
          },
          columns6: [
            {
              title: 'Date',
              key: 'date'
            },
            {
              title: 'Name',
              key: 'name'
            },
            {
              title: 'Age',
              key: 'age',
              filters: [
                {
                  label: 'Greater than 25',
                  value: 1
                },
                {
                  label: 'Less than 25',
                  value: 2
                }
              ],
              filterMultiple: false,
              filterMethod (value, row) {
                if (value === 1) {
                  return row.age > 25;
                } else if (value === 2) {
                  return row.age < 25;
                }
              }
            },
            {
              title: 'Address',
              key: 'address',
              filters: [
                {
                  label: 'New York',
                  value: 'New York'
                },
                {
                  label: 'London',
                  value: 'London'
                },
                {
                  label: 'Sydney',
                  value: 'Sydney'
                }
              ],
              filterMethod (value, row) {
                return row.address.indexOf(value) > -1;
              }
            }
          ],
          data5: [
            {
              name: 'Joe Black',
              age: 30,
              address: 'Sydney No. 1 Lake Park',
              date: '2016-10-02'
            },
            {
              name: 'Jon Snow',
              age: 26,
              address: 'Ottawa No. 2 Lake Park',
              date: '2016-10-04'
            },
            {
              name: 'Jon Snow',
              age: 26,
              address: 'Ottawa No. 2 Lake Park',
              date: '2016-10-04'
            },
            {
              name: 'Jon Snow',
              age: 26,
              address: 'Ottawa No. 2 Lake Park',
              date: '2016-10-04'
            }
          ]
        }
      },
      methods:{
        close(){
          this.$emit("childTableListen", function (parent) {
            parent.dictInfoGrid.childTable.show=false
          })
        },
        selectIcon(icon) {
          this.$emit("childTableListen", function (parent) {
           // parent.menuForm.data.icon = icon
            //parent.menuForm.iconSelect=false
          })
        }
      }
    }
</script>

<style scoped>

</style>
