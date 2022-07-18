<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 60px">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div><i class="el-icon-user-solid" /> 用户总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-coin" /> 销售总量</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            ￥ 1000000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-money" /> 收益总额</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            ￥ 300000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-s-shop" /> 门店总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            20
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <div id="main" style="width:500px;height: 450px"></div>
      </el-col>

      <el-col :span="12">
        <div id="pie" style="width:500px;height: 450px"></div>
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
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
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
          name: "折线图",
          data: [],
          type: 'line'
        },
        {
          name: "柱状图",
          data: [],
          barWidth: '35%',
          type: 'bar',
          itemStyle: {
            barBorderRadius: 3,
            borderWidth: 1,
            borderType: 'solid',
            borderColor: '#73c0de',
            shadowColor: '#5470c6',
            shadowBlur: 3,
            color: '#73c0de'
          }
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