var pageii = null;
var grid = null;
$(function() {

    grid = lyGrid({
        pagId : 'paging',
        l_column : [ {
            colkey : "id",
            name : "id",
        }, {
            colkey : "accountName",
            name : "用户",
        }, {
            colkey : "standard_num",
            name : "合格数",
            isSort:true,
        }, {
            colkey : "unstandard_num",
            name : "不合格数",
            isSort:true,
        }, {
            colkey : "evaluate_date",
            name : "评估时间",
			isSort:true,
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
        }, {
            name : "查看详情",
            renderData : function( rowindex ,data, rowdata, colkeyn) {
                return "测试渲染函数";
            	//return "<a herf=" + rootPath + '/risk/toAddQuotaPage.shtml'+ ">查看详情</a>";
            }
        } ],
        jsonUrl : rootPath + '/risk/findResultByPage.shtml',
        checkbox : true,
        serNumber : true
    });
    $("#search").click("click", function() {// 绑定查询按扭
        var searchParams = $("#searchForm").serializeJson();// 初始化传参数
        grid.setOptions({
            data : searchParams
        });
    });

    
    $("#viewDetail").click("click", function() {
    	var cbox = grid.getSelectedCheckbox();
    	if (cbox.length > 1 || cbox == "") {
            layer.msg("只能选中一个");
            return;
        }
        pageii = layer.open({
            title : "详情",
            type : 2,
            area : [ "80%", "90%" ],
            content : rootPath + '/risk/toDetailPage.shtml?id=' + cbox
        });
    });
    
    $("#delFun").click("click", function() {
    	var cbox = grid.getSelectedCheckbox();
        if (cbox == "") {
            layer.msg("请选择删除项！！");
            return;
        }
        layer.confirm('是否删除？', function(index) {
            var url = rootPath + '/risk/doDeleteEvaluate.shtml';
            var s = CommnUtil.ajax(url, {
                ids : cbox.join(",")
            }, "json");
            if (s == "success") {
                layer.msg('删除成功');
                grid.loadData();
            } else {
                layer.msg('删除失败');
            }
        });
    });
});
