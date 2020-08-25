var zgqdCharts="";
var mzfydCharts="";
var jzwphcdCharts="";
var tzyxlCharts="";

//震感强度统计图
function addZgqdCharts(stime,etime){
	var id=$("#cureventid").val();
	var ttime="";
	if(stime && etime)
	{
		ttime="&stime="+stime+"&etime="+etime;
	}
	$.get("/count/getTremble?id="+id+"&zj="+curzj+"&dist="+$("#fwlist").val()+"&tjtype="+$("#leix").val()+ttime,function(data){
		//console.log(data)
		if(data.state=="fail")
		{
			data.data={};
			data.data.dj=0;
			data.data.mc=0;
			data.data.jj=0;
			data.data.sn=0;
			data.data.sw=0;
			data.data.zl=0;
			data.data.other=0;
			data.data.zrs=1;
		}
		if(data.data.zrs==0)
		{
			data.data.zrs=1;
		}
		option = {
		    title: {
		        text: '震感强度',
		        x: '60%',
		        y: 'bottom',
		        textStyle: {
		        	color:"#fff",
		            fontWeight: 'normal',
		            fontSize: 14
		        }
		    },
		    tooltip: {
		        trigger: 'axis',
		        backgroundColor:'rgba(255,255,255,0.8)',
		        extraCssText: 'box-shadow: 0 0 8px rgba(0, 0, 0, 0.3);',
		        textStyle:{
		            color:'#6a717b',
		        },
						formatter:'{b}：{c}%'
		    }, 
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '30px',
		        top:"20px",
		        containLabel: true
		    },
		    yAxis: [{
		        type: 'category',
		        data: ['灯具轻微晃动','门窗轻微作响','家具晃动','室内人有明显震感','室内室外人均有明显震感','站立不稳','其他'],
		        inverse: true,
		        axisTick: {
		            alignWithLabel: true,
		        },
		         axisLabel: {
		            margin: 10,
		            textStyle: {
		                fontSize: 12,
		                color:'#ffffff'
		                }
		        },
		         axisLine: {
		            lineStyle: {
		                color: '#2548ac'
		            }
		         },
		    }],
		    xAxis: [{
		        type: 'value',
		        max:100,
		        splitNumber:5,
		        axisLabel: {
		            margin: 10,
		            textStyle: {
		                fontSize: 12,
		                color:'#ffffff'
	                },
		            formatter:'{value}%'
		        },
		         axisLine: {
		            lineStyle: {
		                color: '#192469'
		            }
		         },
		         splitLine: {
		            lineStyle: {
		                color: '#17367c'
		            }
		        }
		    }],
		    series: [{
		        name: '震感强度',
		        type: 'bar',
		        barWidth:10,
		        barMinHeight:10,
		        data: [(data.data.dj/data.data.zrs*100).toFixed(0), (data.data.mc/data.data.zrs*100).toFixed(0), (data.data.jj/data.data.zrs*100).toFixed(0), (data.data.sn/data.data.zrs*100).toFixed(0), (data.data.sw/data.data.zrs*100).toFixed(0), (data.data.zl/data.data.zrs*100).toFixed(0), (data.data.other/data.data.zrs*100).toFixed(0)],
		        label: {
		            normal: {
		                show: true,
		                position: 'insideRight',
		                textStyle: {
		                    color: 'white' //color of value
		                },
		                formatter:'{c}%',
		                position: [0, 0]
		            }
		        },
		        itemStyle: {
		            
		            normal: {
		                color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
		                    offset: 0,
		                    color: '#0590eb' // 0% 处的颜色
		                }, {
		                    offset: 1,
		                    color: '#08e3f1' // 100% 处的颜色
		                }], false),
		                barBorderRadius: [0, 15,15, 0],
		                shadowColor: 'rgba(0,0,0,0.1)',
		                shadowBlur: 3,
		                shadowOffsetY: 3
		            }
		        }
		    }]
		};
		zgqdCharts = echarts.init(document.getElementById('zgqd'));
		zgqdCharts.setOption(option,true);
	})
}
//民众反应度
function addMzfydCharts(stime,etime){
	var id=$("#cureventid").val();
	var ttime="";
	if(stime && etime)
	{
		ttime="&stime="+stime+"&etime="+etime;
	}
	$.get("/count/getResponsiveness?id="+id+"&zj="+curzj+"&dist="+$("#fwlist").val()+"&tjtype="+$("#leix").val()+ttime,function(data){
		//console.log(data)
		var data1 = [{
		    value: data.data.wyx,
		    name: '极个别在户外避震'
		}, {
		    value: data.data.ss,
		    name: '少数在户外避震'
		}, {
		    value: data.data.ds,
		    name: '多数在户外避震'
		},{
		    value: data.data.jz,
		    name: '均在户外避震'
		}, {
		    value: data.data.other,
		    name: '其他'
		}];
		var option = {
		    title: {
		        text: '民众反应度',
		        x: '60%',
		        y: 'bottom',
		        textStyle: {
		        	color:"#fff",
		            fontWeight: 'normal',
		            fontSize: 14
		        }
		    },
		    tooltip: {
		        show: true,
		        trigger: 'item',
		        formatter: "{b}: {c} ({d}%)"
		    },
		    legend: {
		    	orient: 'vertical',
		    	itemWidth: 20,
		        itemHeight: 6,
		        itemGap: 10,
		        padding: [0, 38],
		        //orient: 'horizontal',
		        x:"left",
		        y: 'center',
		        data: ['极个别在户外避震', '少数在户外避震', '多数在户外避震','均在户外避震','其他'],
		        textStyle: {
		            color:"#ffffff"
		        }
		    },
		    series: [{
		        type: 'pie',
		        selectedMode: 'single',
		        radius: ['25%', '70%'],
		        center: ['70%', '45%'],
				roseType: 'area',
				stillShowZeroSum:false,
		        color: ['#AB82FF', '#9ACD32', '#FFB90F', '#FF8C69', '#7aa3ff'],
		        label: {
		            normal: {
		            	show:false,
		                position: 'inner',
		                formatter: '{d}%',
		
		                textStyle: {
		                    color: '#1e1f23',
		                    fontWeight: 'bold',
		                    fontSize: 12
		                }
		            }
		        },
		        labelLine: {
		            normal: {
		                show: false
		            }
		        },
		        data: data1
		    }]
		};
			mzfydCharts = echarts.init(document.getElementById('mzfyd'));
			mzfydCharts.setOption(option,true);
	})
	
	
}

//建筑物破坏程度
function addJzwphcdCharts(stime,etime){
	var ttime="";
	if(stime && etime)
	{
		ttime="&stime="+stime+"&etime="+etime;
	}
	var piebg = {
	    name: '相关背景',
	    type: 'pie',
	    labelLine: {
	        normal: {
	            show: false
	        }
	    },
	    data: [{
	        value: 0
	    }],
	    avoidLabelOverlap: false,
	    animation: false
	};
	var id=$("#cureventid").val();
	$.get("/count/getBuilding?id="+id+"&zj="+curzj+"&dist="+$("#fwlist").val()+"&tjtype="+$("#leix").val()+ttime,function(data){
		//console.log(data)
		var option = {
			title: {
		        text: '破坏程度',
		        x: '60%',
		        y: 'bottom',
		        textStyle: {
		        	color:"#fff",
		            fontWeight: 'normal',
		            fontSize: 14
		        }
		    },
		    legend: {
		        orient: 'vertical',
		        x:'left',
		        y:'center',
		        itemWidth: 20,
		        itemHeight: 6,
		        itemGap: 10,
		        padding: [0, 38],
		        textStyle: {
		          color: '#ffffff',
		          fontSize: 12,
		        },
		        data: [
		          {name: '轻微',icon: 'rec'},
		          {name: '严重',icon: 'rec'},
		          {name: '垮塌',icon: 'rec'},
		          {name: '整体',icon: 'rec'},
		          {name: '局部',icon: 'rec'},
		          {name: '溜瓦',icon: 'rec'},
		          {name: '裂缝',icon: 'rec'},
		        ]
		      },
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    series: [
		        {
		            name: '倒塌',
		            type: 'pie',
		            radius: ['20%', '30%'],
		            center:['70%','50%'],
		            color:['#0eb35b','#42f0f6','#017ce0'],
		            labelLine:{normal:{show:false}},
					label:{normal:{show:false}},
						itemStyle: {
						   normal: {
	//					     borderWidth: 5,
	//					     borderColor: '#0d1112',
						   },
						  emphasis: {
						      borderWidth: 0,
						      shadowBlur: 10,
						      shadowOffsetX: 0,
						      shadowColor: 'rgba(0, 0, 0, 0.5)'
						 }
					},
		            data: [
		                {value: data.data.zt,name: '整体'},
		                {value: data.data.jb,name: '局部'},
		            ]
		        },
		        {
		            name: '屋顶破坏',
		            type: 'pie',
		            radius: ['65%', '75%'],
		            center:['70%','50%'],
		            color:['#fa4b46', '#ffc502', '#ae9fff'],
		            labelLine:{normal:{show:false}},
					label:{normal:{show:false}},
						itemStyle: {
						   normal: {
	//					     borderWidth: 5,
	//					     borderColor: '#0d1112',
						   },
						  emphasis: {
						      borderWidth: 0,
						      shadowBlur: 10,
						      shadowOffsetX: 0,
						      shadowColor: 'rgba(0, 0, 0, 0.5)'
						 }
					},
		            data: [
		                {value: data.data.lw,name: '溜瓦'},
		                {value: data.data.lf,name: '裂缝'},
		                {value: data.data.kt,name: '垮塌'},
		            ]
		        },
		        {
		            name: '墙体裂缝',
		            type: 'pie',
		            radius: ['40%', '55%'],
		            center:['70%','50%'],
		            color:['#3b1d88','#017ce0','#fc2424'],
		            labelLine:{normal:{show:false}},
					label:{normal:{show:false}},
						itemStyle: {
						   normal: {
	//					     borderWidth: 5,
	//					     borderColor: '#0d1112',
						   },
						  emphasis: {
						      borderWidth: 0,
						      shadowBlur: 10,
						      shadowOffsetX: 0,
						      shadowColor: 'rgba(0, 0, 0, 0.5)'
						 }
					},
		            data: [
		                {value: data.data.qw,name: '轻微'},
		                {value: data.data.yz,name: '严重'},
		            ]
		        }
		    ]
		};
		//jzwphcdCharts = echarts.init(document.getElementById('jzwphcd'));
		//jzwphcdCharts.setOption(option,true);
		$("#dtds").text(data.data.ds ? data.data.ds: 0);
	})
}
//通知有效率
function addTzyxlCharts(){
	var id=$("#cureventid").val();
	$.get("/count/getValid?id="+id,function(data){
		console.log(data)
		//Js();
		//$("#jsq").html(Js());
//		setInterval(function(){
//			Js();
//		},1000)
		if(data.data)
		{
			if(!data.data.fkl)
			{
				data.data.fkl=0;
			}
			if(!data.all)
			{
				data.all=0;
			}
			if(!data.data.tzl)
			{
				data.data.tzl=0;
			}
			if(!data.data.yxl)
			{
				data.data.yxl=0;
			}
		}
		else
		{
			data.data={};
			if(!data.data.fkl)
			{
				data.data.fkl=0;
			}
			if(!data.all)
			{
				data.all=0;
			}
			if(!data.data.tzl)
			{
				data.data.tzl=0;
			}
			if(!data.data.yxl)
			{
				data.data.yxl=0;
			}
		}
		$("#tzrs").text(data.all+"人");
		$("#bzrs").text(data.data.fkl+"人");
			// 颜色
		var lightBlue = {
			    type: 'linear',
			    x: 0,
			    y: 0,
			    x2: 0,
			    y2: 1,
			    colorStops: [{
			        offset: 0,
			        color: 'rgba(41, 121, 255, 1)' // 0% 处的颜色
			    }, {
			        offset: 1,
			        color: 'rgba(0, 192, 255, 1)' // 100% 处的颜色
			    }],
			    globalCoord: false // 缺省为 false
			}
			var piePane = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAKElEQVQ4jWP8//8/AwXgPxMluhkYGBhGDRg1YNQAKhnAwsDAQFF+BgBtSwUd6uvSywAAAABJRU5ErkJggg=='
			var piePatternImg = new Image();
			piePatternImg.src = piePane;
			// 指定图表的配置项和数据
			var option = {
			    tooltip: {
			        show: false
			    },
			    grid: {
			        top: '25%',
			        left: '5%',
			        right: '5%',
			        bottom: '25%',
			    },
			    xAxis: {
			        data: ['报灾\n通知率', '通知\n有效率', '通知\n反馈率'],
			        offset: 0,
			        axisTick: {
			            show: false
			        },
			        axisLine: {
			            show: false
			        },
			        axisLabel: {
			            color: '#fff',
			            fontSize: 14
			        }
			    },
			    yAxis: {
			        min: 0,
			        max: data.all,
			        splitLine: {
			            show: false
			        },
			        axisTick: {
			            show: false
			        },
			        axisLine: {
			            show: false
			        },
			        axisLabel: {
			            show: false
			        }
			    },
			    series: [{
			        type: 'bar',
			        barMinHeight:10,
			        label: {
			            show: true,
			            position: 'top',
			            padding: 0,
			            color: '#ffffff',
			            fontSize: 14,
			            formatter: function(v){
			            	//console.log(v)
			            	return (v.value/(data.all?data.all :1)*100).toFixed(0)+"%";
			            }
			        },
			        itemStyle: {
			            color: lightBlue
			        },
			        barWidth: '40%',
			        data: [data.data.tzl, data.data.yxl, data.data.fkl],
			        z: 10
			    }, {
			        type: 'bar',
			        barGap: '-100%',
			        barMinHeight:50,
			        itemStyle: {
			            color: {
			                image: piePatternImg,
			                repeat: 'repeat'
			            },
			            opacity: 0.05
			        },
			        barWidth: '40%',
			
			        data: [data.all, data.all, data.all],
			        z: 5
			    }, {
			        type: 'bar',
			        barGap: '-100%',
			        barMinHeight:50,
			        itemStyle: {
			            color: '#536dfe',
			            opacity: 0.2
			        },
			        barWidth: '40%',
			
			        data: [data.all, data.all, data.all],
			        z: 5
			    }],
			};
			tzyxlCharts = echarts.init(document.getElementById('yxl'));
			tzyxlCharts.setOption(option,true);
	})

}

//更新死亡人数
function addSzqk(stime,etime){
	var id=$("#cureventid").val();
	var ttime="";
	if(stime && etime)
	{
		ttime="&stime="+stime+"&etime="+etime;
	}
	$.get("/count/getDisaster?id="+id+"&zj="+curzj+"&dist="+$("#fwlist").val()+"&tjtype="+$("#leix").val()+ttime,function(data){
		console.log(data)
		$("#zsrs").text(data.data.zsnum);
		$("#swrs").text(data.data.deathnum);
		$("#qsrs").text(data.data.qsnum);
	})
}

//列表中加载最新地震事件
function addNewDz(){
	$.get("",function(data,s){
		
	})
}

function Js(mss){
	var hours = parseInt((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
	var minutes = parseInt((mss % (1000 * 60 * 60)) / (1000 * 60));
	var seconds = (mss % (1000 * 60)) / 1000;
	return hours+":"+minutes+":"+seconds;
}