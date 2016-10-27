<%--
  Created by IntelliJ IDEA.
  User: NING
  Date: 2016/10/4
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@include file="/common/common.jspf" %>
  <script type="text/javascript" src="${ctx}/js/app/resourcemanage/datasetedit.js"></script>

  <style type="text/css">
    .col-sm-3 {
      width: 15%;
      float: left;
    }

    .col-sm-9 {
      width: 85%;
      float: left;
    }
  </style>
</head>
<body>
<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
<form id="form" name="form" class="form-horizontal" method="post">
  <section class="panel panel-default">
    <div class="panel-body">
      <div class="form-group">
      <c:forEach items="${allRisk}" var="r">
		<div class="form-group">
        <label class="col-sm-3 control-label">${r.name}</label>

        <div class="col-sm-9">
          <input type="text" class="form-control"
                 placeholder="请输入${r.name}的值，限额为${r.critical_discharge}" value="${r.value}"
                 name="${r.name}" id="${r.name}">
        </div>
      </div>

      <div class="line line-dashed line-lg pull-in"></div>
      </c:forEach>
    </div>
    </div>
    <footer class="panel-footer text-right bg-light lter">
		<div class="doc-buttons">
	        <c:forEach items="${res}" var="key">
	            ${key.description}
	        </c:forEach>
	    </div>   
    </footer>
  </section>
</form>

<script type="text/javascript">
  onloadurl();
  $('.form_date').datetimepicker({
    language: 'zh-CN',
    weekStart: 1,
    todayBtn: 1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2,
    forceParse: 0
  });
  
  $("#submit").click("click", function() {
	  submit();
  });
    
  function submit(){
		ly.ajax({
			async : false, //请勿改成异步，下面有些程序依赖此请数据
			type : "POST",
			data : {
				"risks" : JSON.stringify($('#form').serializeArray())
			},
			url : rootPath + '/risk/DoRiskEvaluate.shtml',
			dataType : 'json',
			success : function(json) {
				if (json == "success") {
					/* 	layer.confirm('分配成功！是否关闭窗口？',{icon: 3,offset: '200px'}, function(index) {
				        	parent.layer.close(parent.pageii);
				        	return false;
						 }); */
					layer.alert("分配成功！可查看评估结果",{icon: 1,offset: '200px'});
				} else {
					layer.alert(json,{icon: 2,offset: '200px'});
				}
			}
		});
	}
</script>
</body>
</html>
