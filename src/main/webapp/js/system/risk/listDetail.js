var pageii = null;
var grid = null;
$(function() {
	var standard_num = $("#standard_num").val();
	var unstandard_num = $("#unstandard_num").val();
	var groupId = $("#groupId").val();
    grid = lyGrid({
        pagId : 'paging',
        l_column : [ {
            colkey : "id",
            name : "id",
        }, {
            colkey : "groupId",
            name : "分组",
        }, {
            colkey : "name",
            name : "指标名",
        }, {
            colkey : "value",
            name : "录入值",
        }, {
            colkey : "result",
            name : "评估结果",
            renderData : function( rowindex ,data, rowdata, colkeyn) {
            	if(data == 0){
            		return "合格";
            	}
            	else{
            		return "不合格";
            	}
            }
        }, {
            name : "查看详情",
            renderData : function( rowindex ,data, rowdata, colkeyn) {
                return "测试渲染函数";
            }
        }],
        jsonUrl : rootPath + '/risk/getDetail.shtml?groupId='+ groupId,
        checkbox : true,
        serNumber : true
    });
    $("#search").click("click", function() {// 绑定查询按扭
        var searchParams = $("#searchForm").serializeJson();// 初始化传参数
        grid.setOptions({
            data : searchParams
        });
    });
    
    var myChart = echarts.init(document.getElementById('chart')); 
    option = {
	    title : {
	        text: '风险评估结果',
	        subtext: '评估合格率',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient : 'vertical',
	        x : 'left',
	        data:['不合格数','合格数']
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            magicType : {
	                show: true, 
	                type: ['pie', 'funnel'],
	                option: {
	                    funnel: {
	                        x: '25%',
	                        width: '50%',
	                        funnelAlign: 'left',
	                        max: 1548
	                    }
	                }
	            },
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    series : [
	        {
	            name:'访问来源',
	            type:'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
	                {value:unstandard_num, name:'不合格数'},
	                {value:standard_num, name:'合格数'}
	            ]
	        }
	    ],
	    color:['red', 'green','yellow','blueviolet']//属性color，并指定几种颜色，此颜色将根据饼图的区域个数循环
	};
    myChart.setOption(option);
});
