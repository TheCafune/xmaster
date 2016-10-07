<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/resourcemanage/datasetmanage.js"></script>
<script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
<style type="text/css">
  #show {
    height: 350px;
    margin: 0 20px 20px 20px;
  }
</style>

<header class="panel-heading">
  <div class="doc-buttons">
    <c:forEach items="${res}" var="key">
      ${key.description}
    </c:forEach>
  </div>
</header>

<div class="table-responsive">
  <div id="paging" class="pagclass"></div>
</div>

<input id="load" type="button" value="Load"/>

<div class="row">
  <div id="show">
    <input id="test" value="hahh ">
  </div>
</div>
<script type="text/javascript">
  //为id为load的按钮绑定事件处理函数
  $("#load").click(function()
  {
    //调用本地资源
    alert("123");
    //F:/UnityTest/zhongji-8.12anlihuifang/生成测试项目/2.html
    $("#show").load("../../../html/a.html");
    var user_name=$("#test").attr("value");
    alert(user_name);
    alert("1234");
  });
</script>
