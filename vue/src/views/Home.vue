<template>
  <div>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width:500px;height: 400px"></div>
      </el-col>

      <el-col :span="12">
        <div id="pie" style="width:500px;height: 400px"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "Home",
  data(){
    return{

    }
  },
  mounted() {  //页面元素渲染之后再触发
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var option = {
      title: {
        text: '各季度会员数量统计',
        subtext: '趋势图',
        left: 'center'
      },
      xAxis: {
        type: 'category',
        data: ["第一季度","第二季度","第三季度","第四季度"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        {
          data: [],
          type: 'bar'
        }
      ]
    };

    var chartDom1 = document.getElementById('pie');
    var myChart1 = echarts.init(chartDom1);
    var pieOption = {
      title: {
        text: '各季度会员数量统计',
        subtext: '比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          type: 'pie',
          radius: '60%',
          label:{
            normal:{
              show:true,
              position:'inner',
              textStyle:{
                fontsize: 16,
                fontWeight: 300,
                color: "#fff"
              },
              formatter: '{d}%'
            }
          },
          data: [],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };

    this.request.get("/echarts/members").then(res => {
      //填充数据
      option.series[0].data = res.data
      option.series[1].data = res.data
      //数据准备完毕之后再set
      myChart.setOption(option)

      pieOption.series[0].data = [
        {name: '第一季度', value: res.data[0]},
        {name: '第二季度', value: res.data[1]},
        {name: '第三季度', value: res.data[2]},
        {name: '第四季度', value: res.data[3]}
      ]
      myChart1.setOption(pieOption)
    })

  }
}
</script>

<style scoped>

</style>